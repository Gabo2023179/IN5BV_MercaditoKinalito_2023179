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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.josesanchez.beans.Cargos;
import org.josesanchez.dbs.Conexion;
import org.josesanchez.system.Main;

/**
 *
 * @author joseg
 */
public class MenuCargosController implements Initializable {

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }

    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    private ObservableList<Cargos> listaCargos;

    private Main escenarioPrincipal;

    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtCargoId;
    @FXML
    private TextField txtNombreCargo;
    @FXML
    private TextField txtDescripcionCargo;
    @FXML
    private TableView tblCargos;
    @FXML
    private TableColumn colCargoId;
    @FXML
    private TableColumn colNombreCargo;
    @FXML
    private TableColumn colDescripcionCargo;
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
        cargarDatos();
    }

    public void cargarDatos() {
        tblCargos.setItems(getCargos());
        colCargoId.setCellValueFactory(new PropertyValueFactory<Cargos, Integer>("cargoId"));
        colNombreCargo.setCellValueFactory(new PropertyValueFactory<Cargos, String>("nombreCargo"));
        colDescripcionCargo.setCellValueFactory(new PropertyValueFactory<Cargos, String>("DescripcionCargo"));

    }

    public void seleccionarElemento() {
        txtCargoId.setText(String.valueOf(((Cargos) tblCargos.getSelectionModel().getSelectedItem()).getCargoId()));
        txtNombreCargo.setText((((Cargos) tblCargos.getSelectionModel().getSelectedItem()).getNombreCargo()));
        txtDescripcionCargo.setText(((Cargos) tblCargos.getSelectionModel().getSelectedItem()).getDescripcionCargo());
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
        switch (tipoDeOperaciones) {
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
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/josesanchez/Images/AgregarCargo.png"));
                imgEliminar.setImage(new Image("/org/josesanchez/Images/EliminarCargo.png"));
                imgReporte.setOpacity(1);
                imgEditar.setOpacity(1);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void guardar() {
        Cargos registro = new Cargos();
        registro.setNombreCargo(txtNombreCargo.getText());
        registro.setDescripcionCargo((txtDescripcionCargo.getText()));
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCargos (?, ?)}");
            procedimiento.setString(1, registro.getNombreCargo());
            procedimiento.setString(2, registro.getDescripcionCargo());
            procedimiento.execute();
            ResultSet generatedKeys = procedimiento.getGeneratedKeys();
            if (generatedKeys.next()) {
                registro.setCargoId(generatedKeys.getInt(1));
            }
            listaCargos.add(registro);
            cargarDatos();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/josesanchez/Images/AgregarCargo.png"));
                imgEliminar.setImage(new Image("/org/josesanchez/Images/EliminarCargo.png"));
                imgReporte.setOpacity(1);
                imgEditar.setOpacity(1);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblCargos.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Cargos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {

                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCargo (?)}");
                            procedimiento.setInt(1, ((Cargos) tblCargos.getSelectionModel().getSelectedItem()).getCargoId());
                            procedimiento.execute();
                            limpiarControles();
                            listaCargos.remove(tblCargos.getSelectionModel().getSelectedItem());
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
        switch (tipoDeOperaciones) {

            case NINGUNO:
                if (tblCargos.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/josesanchez/Images/EditarCargo.png"));
                    imgReporte.setImage(new Image("/org/josesanchez/Images/cancelar.png"));
                    imgAgregar.setOpacity(0.5);
                    imgEliminar.setOpacity(0.5);
                    activarControles();
                    txtCargoId.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
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
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }

    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCargo (?, ?, ?)}");
            Cargos registro = (Cargos) tblCargos.getSelectionModel().getSelectedItem();
            registro.setNombreCargo(txtNombreCargo.getText());
            registro.setDescripcionCargo(txtDescripcionCargo.getText());
            procedimiento.setInt(1, registro.getCargoId());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reporte() {
        switch (tipoDeOperaciones) {
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
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void desactivarControles() {
        txtCargoId.setEditable(false);
        txtNombreCargo.setEditable(false);
        txtDescripcionCargo.setEditable(false);
    }

    public void activarControles() {
        txtNombreCargo.setEditable(true);
        txtDescripcionCargo.setEditable(true);

    }

    public void limpiarControles() {
        txtCargoId.clear();
        txtNombreCargo.clear();
        txtDescripcionCargo.clear();
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void regresar(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

}
