/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josesanchez.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.josesanchez.beans.Cargos;
import org.josesanchez.beans.Empleados;
import org.josesanchez.dbs.Conexion;
import org.josesanchez.system.Main;

/**
 *
 * @author informatica
 */
public class MenuEmpleadosController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    };
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Empleados> listaEmpleados;
    private ObservableList<Cargos> listaCargos;

    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtCodEmpleado;
    @FXML
    private TextField txtNomEmpleado;
    @FXML
    private TextField txtApeEmpleado;
    @FXML
    private TextField txtSueldo;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtTurno;
    @FXML
    private ComboBox cmbCargoId;
    @FXML
    private TableView tblEmpleados;
    @FXML
    private TableColumn colCodEmp;
    @FXML
    private TableColumn colNomEmpleado;
    @FXML
    private TableColumn colApeEmpleado;
    @FXML
    private TableColumn colSueldo;
    @FXML
    private TableColumn colDireccion;
    @FXML
    private TableColumn colTurno;
    @FXML
    private TableColumn colCargoId;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReporte;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgReporte;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargaDatos();
        cmbCargoId.setItems(getCargos());
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void cargaDatos() {
        tblEmpleados.setItems(getEmpleados());
        colCodEmp.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("codigoEmpleado"));
        colNomEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombresEmpleado"));
        colApeEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("apellidosEmpleado"));
        colSueldo.setCellValueFactory(new PropertyValueFactory<Empleados, Double>("sueldo"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Empleados, String>("direccion"));
        colTurno.setCellValueFactory(new PropertyValueFactory<Empleados, String>("turno"));
        colCargoId.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("cargoId"));
    }

    public void seleccionarElementos() {
        txtCodEmpleado.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
        txtNomEmpleado.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getNombresEmpleado());
        txtApeEmpleado.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getApellidosEmpleado()));
        txtSueldo.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getSueldo()));
        txtDireccion.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getDireccion()));
        txtTurno.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getTurno()));
        cmbCargoId.getSelectionModel().select(buscarCargos(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCargoId()));

    }

    public Cargos buscarCargos(int cargoId) {
        Cargos resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarCargos(?)}");
            procedimiento.setInt(1, cargoId);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Cargos(registro.getInt("cargoId"),
                        registro.getString("nombreCargo"),
                        registro.getString("DescripcionCargo")
                );

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public ObservableList<Empleados> getEmpleados() {
        ArrayList<Empleados> lista = new ArrayList<Empleados>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpleados ()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Empleados(resultado.getInt("codigoEmpleado"),
                        resultado.getString("nombresEmpleado"),
                        resultado.getString("apellidosEmpleado"),
                        resultado.getDouble("sueldo"),
                        resultado.getString("direccion"),
                        resultado.getString("turno"),
                        resultado.getInt("cargoId")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaEmpleados = FXCollections.observableArrayList(lista);
    }

    public ObservableList<Cargos> getCargos() {
        ArrayList<Cargos> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCargos ()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Cargos(resultado.getInt("cargoId"),
                        resultado.getString("nombreCargo"),
                        resultado.getString("DescripcionCargo")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaCargos = FXCollections.observableArrayList(lista);
    }

    public void agregar() {
        switch (tipoDeOperacion) {
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("/org/josesanchez/Images/guardar.png"));
                imgEliminar.setImage(new Image("/org/josesanchez/Images/cancelar.png"));
                imgReporte.setOpacity(0.5);
                imgEditar.setOpacity(0.5);
                tipoDeOperacion = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/josesanchez/Images/AgregarTipoDeProducto.png"));
                imgEliminar.setImage(new Image("/org/josesanchez/Images/elimianrtipodeproducto.png"));
                imgReporte.setOpacity(1);
                imgEditar.setOpacity(1);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }

    public void guardar() {
        Empleados registro = new Empleados();
        registro.setCargoId(((Cargos) cmbCargoId.getSelectionModel().getSelectedItem())
                .getCargoId());
        registro.setNombresEmpleado(txtNomEmpleado.getText());
        registro.setApellidosEmpleado(txtApeEmpleado.getText());
        registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
        registro.setDireccion(txtDireccion.getText());
        registro.setTurno(txtTurno.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmpleados(?, ?, ?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getNombresEmpleado());
            procedimiento.setString(2, registro.getApellidosEmpleado());
            procedimiento.setDouble(3, registro.getSueldo());
            procedimiento.setString(4, registro.getDireccion());
            procedimiento.setString(5, registro.getTurno());
            procedimiento.setInt(6, registro.getCargoId());
            procedimiento.execute();
            ResultSet generatedKeys = procedimiento.getGeneratedKeys();
            if (generatedKeys.next()) {
                registro.setCodigoEmpleado(generatedKeys.getInt(1));
            }
            listaEmpleados.add(registro);
            cargaDatos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar() {
        switch (tipoDeOperacion) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/josesanchez/Images/AgregarTipoDeProducto.png"));
                imgEliminar.setImage(new Image("/org/josesanchez/Images/elimianrtipodeproducto.png"));
                imgReporte.setOpacity(1);
                imgEditar.setOpacity(1);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            default:
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmpleado (?)}");
                            procedimiento.setInt(1, ((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                            procedimiento.execute();
                            limpiarControles();
                            listaEmpleados.remove(tblEmpleados.getSelectionModel().getSelectedItem());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento!");
                }
        }
    }

    public void editar() {
        switch (tipoDeOperacion) {

            case NINGUNO:
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/josesanchez/Images/editartipodeproducto.png"));
                    imgReporte.setImage(new Image("/org/josesanchez/Images/cancelar.png"));
                    imgAgregar.setOpacity(0.5);
                    imgEliminar.setOpacity(0.5);
                    activarControles();
                    txtCodEmpleado.setEditable(false);
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar algun elemento!");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/josesanchez/Images/EditarCargo 2.png"));
                imgReporte.setImage(new Image("/org/josesanchez/Images/Accounting_icon-icons.com_74682.png"));
                imgAgregar.setOpacity(1);
                imgEliminar.setOpacity(1);
                desactivarControles();
                limpiarControles();
                tipoDeOperacion = operaciones.NINGUNO;
                cargaDatos();
                break;
        }

    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarEmpleado (?, ?, ?, ?, ?, ?, ?)}");
            Empleados registro = (Empleados) tblEmpleados.getSelectionModel().getSelectedItem();
            registro.setCodigoEmpleado(Integer.parseInt(txtCodEmpleado.getText()));
            registro.setNombresEmpleado(txtNomEmpleado.getText());
            registro.setApellidosEmpleado(txtApeEmpleado.getText());
            registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
            registro.setDireccion(txtDireccion.getText());
            registro.setTurno(txtTurno.getText());
            registro.setCargoId(((Cargos) cmbCargoId.getValue()).getCargoId());
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setString(2, registro.getNombresEmpleado());
            procedimiento.setString(3, registro.getApellidosEmpleado());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(5, registro.getDireccion());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getCargoId());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reporte() {
        switch (tipoDeOperacion) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/josesanchez/Images/EditarCargo 2.png"));
                imgReporte.setImage(new Image("/org/josesanchez/Images/Accounting_icon-icons.com_74682.png"));
                imgAgregar.setOpacity(1);
                imgEliminar.setOpacity(1);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void desactivarControles() {
        txtCodEmpleado.setEditable(false);
        txtNomEmpleado.setEditable(false);
        txtApeEmpleado.setEditable(false);
        txtSueldo.setEditable(false);
        txtDireccion.setEditable(false);
        txtTurno.setEditable(false);
        cmbCargoId.setDisable(true);
    }

    public void activarControles() {
        txtNomEmpleado.setEditable(true);
        txtApeEmpleado.setEditable(true);
        txtSueldo.setEditable(true);
        txtDireccion.setEditable(true);
        txtTurno.setEditable(true);
        cmbCargoId.setDisable(false);
    }

    public void limpiarControles() {
        txtCodEmpleado.clear();
        txtNomEmpleado.clear();
        txtApeEmpleado.clear();
        txtSueldo.clear();
        txtDireccion.clear();
        txtTurno.clear();
        cmbCargoId.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void regresar(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

}
