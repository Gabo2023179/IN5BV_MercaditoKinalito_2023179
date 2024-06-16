/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josesanchez.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import org.josesanchez.beans.TipoDeProductos;
import org.josesanchez.dbs.Conexion;
import org.josesanchez.reports.GenerarReportes;
import org.josesanchez.system.Main;

/**
 *
 * @author informatica
 */
public class MenuTipoDeProductosController implements Initializable {

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }

    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    private ObservableList<TipoDeProductos> listaTdp;

    private Main escenarioPrincipal;

    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtTdp;
    @FXML
    private TextField txtdescripcion;
    @FXML
    private TableView tblTdp;
    @FXML
    private TableColumn colTdp;
    @FXML
    private TableColumn colDescripcion;
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
        tblTdp.setItems(getTipoDeProductos());
        colTdp.setCellValueFactory(new PropertyValueFactory<TipoDeProductos, Integer>("codigoTipoDeProducto"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<TipoDeProductos, String>("descripcion"));

    }

    public void seleccionarElemento() {
        txtTdp.setText(String.valueOf(((TipoDeProductos) tblTdp.getSelectionModel().getSelectedItem()).getCodigoTipoDeProducto()));
        txtdescripcion.setText((((TipoDeProductos) tblTdp.getSelectionModel().getSelectedItem()).getDescripcion()));
    }

    public ObservableList<TipoDeProductos> getTipoDeProductos() {
        ArrayList<TipoDeProductos> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoDeProducto ()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new TipoDeProductos(resultado.getInt("codigoTipoDeProducto"),
                        resultado.getString("descripcion")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaTdp = FXCollections.observableArrayList(lista);
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
                imgAgregar.setImage(new Image("/org/josesanchez/Images/AgregarTipoDeProducto.png"));
                imgEliminar.setImage(new Image("/org/josesanchez/Images/elimianrtipodeproducto.png"));
                imgReporte.setOpacity(1);
                imgEditar.setOpacity(1);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void guardar() {
        TipoDeProductos registro = new TipoDeProductos();
        registro.setDescripcion(txtdescripcion.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTipoDeProducto (?)}");
            procedimiento.setString(1, registro.getDescripcion());
            procedimiento.execute();
            ResultSet generatedKeys = procedimiento.getGeneratedKeys();
            if (generatedKeys.next()) {
                registro.setCodigoTipoDeProducto(generatedKeys.getInt(1));
            }
            listaTdp.add(registro);
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
                imgAgregar.setImage(new Image("/org/josesanchez/Images/AgregarTipoDeProducto.png"));
                imgEliminar.setImage(new Image("/org/josesanchez/Images/elimianrtipodeproducto.png"));
                imgReporte.setOpacity(1);
                imgEditar.setOpacity(1);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblTdp.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar TipoDeProducto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTipoDeProducto (?)}");
                            procedimiento.setInt(1, ((TipoDeProductos) tblTdp.getSelectionModel().getSelectedItem()).getCodigoTipoDeProducto());
                            procedimiento.execute();
                            limpiarControles();
                            listaTdp.remove(tblTdp.getSelectionModel().getSelectedItem());
                        } catch (SQLIntegrityConstraintViolationException e) {
                            JOptionPane.showMessageDialog(null, "No puedes eliminar este registro, esta referenciado en otra clase");
                        } catch (Exception e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Se produjo un error: " + e.getMessage());
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
                if (tblTdp.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/josesanchez/Images/editartipodeproducto.png"));
                    imgReporte.setImage(new Image("/org/josesanchez/Images/cancelar.png"));
                    imgAgregar.setOpacity(0.5);
                    imgEliminar.setOpacity(0.5);
                    activarControles();
                    txtTdp.setEditable(false);
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
                imgEditar.setImage(new Image("/org/josesanchez/Images/editartipodeproducto.png"));
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarTipoDeProducto (?, ?)}");
            TipoDeProductos registro = (TipoDeProductos) tblTdp.getSelectionModel().getSelectedItem();
            registro.setDescripcion(txtdescripcion.getText());
            procedimiento.setInt(1, registro.getCodigoTipoDeProducto());
            procedimiento.setString(2, registro.getDescripcion());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reporte() {
        switch (tipoDeOperaciones) {
             case NINGUNO:
                imprimirReporte();
                break;
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
    
    public void imprimirReporte(){
        Map parametros = new HashMap();
        parametros.put("descripcion", null);
        GenerarReportes.mostrarReportes("reportTipoDeProductos.jasper", "Reporte de los tipos de Productos", parametros);
    }

    public void desactivarControles() {
        txtTdp.setEditable(false);
        txtdescripcion.setEditable(false);
    }

    public void activarControles() {
        txtdescripcion.setEditable(true);

    }

    public void limpiarControles() {
        txtTdp.clear();
        txtdescripcion.clear();
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
