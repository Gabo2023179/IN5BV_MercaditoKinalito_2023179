package org.josesanchez.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
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
import org.josesanchez.beans.Proveedores;
import org.josesanchez.dbs.Conexion;

import org.josesanchez.system.Main;

public class MenuProveedoresController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private ObservableList<Proveedores> listaProveedores;
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private Main escenarioPrincipal;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtDireccionP;
    @FXML
    private TextField txtRazonSocial;
    @FXML
    private TextField txtContactoPrincipal;
    @FXML
    private TextField txtPaginaWeb;
    @FXML
    private TextField txtApellidoP;
    @FXML
    private TextField txtNombreP;
    @FXML
    private TextField txtNitP;
    @FXML
    private TextField txtCodigoP;
    @FXML
    private TableView tblProveedor;
    @FXML
    private TableColumn colCodigoP;
    @FXML
    private TableColumn colNombreP;
    @FXML
    private TableColumn colNitP;
    @FXML
    private TableColumn colApellidoP;
    @FXML
    private TableColumn colDireccionP;
    @FXML
    private TableColumn colRazonSocial;
    @FXML
    private TableColumn colContactoprincipal;
    @FXML
    private TableColumn colPaginaWeb;
    @FXML
    private Button btnAgregarP;
    @FXML
    private Button btnEliminarP;
    @FXML
    private Button btnEditarP;
    @FXML
    private Button btnReporteP;
    @FXML
    private ImageView imgAgregarP;
    @FXML
    private ImageView imgEliminarP;
    @FXML
    private ImageView imgEditarP;
    @FXML
    private ImageView imgReporteP;

    public void cargarDatos() {
        tblProveedor.setItems(getProveedores());
        colCodigoP.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("codigoProveedor"));
        colNitP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("NITproveedor"));
        colNombreP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nombreProveedor"));
        colApellidoP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("apellidoProveedor"));
        colDireccionP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("direccionProveedor"));
        colRazonSocial.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("razonSocial"));
        colContactoprincipal.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("contactoPrincipal"));
        colPaginaWeb.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("paginaWeb"));
    }

    public void seleccionarElemento() {
        txtCodigoP.setText(String.valueOf(((Proveedores) tblProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        txtNitP.setText((((Proveedores) tblProveedor.getSelectionModel().getSelectedItem()).getNITproveedor()));
        txtNombreP.setText(((Proveedores) tblProveedor.getSelectionModel().getSelectedItem()).getNombreProveedor());
        txtApellidoP.setText(((Proveedores) tblProveedor.getSelectionModel().getSelectedItem()).getApellidoProveedor());
        txtDireccionP.setText(((Proveedores) tblProveedor.getSelectionModel().getSelectedItem()).getDireccionProveedor());
        txtRazonSocial.setText(((Proveedores) tblProveedor.getSelectionModel().getSelectedItem()).getRazonSocial());
        txtContactoPrincipal.setText(((Proveedores) tblProveedor.getSelectionModel().getSelectedItem()).getContactoPrincipal());
        txtPaginaWeb.setText(((Proveedores) tblProveedor.getSelectionModel().getSelectedItem()).getPaginaWeb());
    }

    public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> lista = new ArrayList<Proveedores>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProveedores ()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Proveedores(resultado.getInt("codigoProveedor"),
                        resultado.getString("NITproveedor"),
                        resultado.getString("nombreProveedor"),
                        resultado.getString("apellidoProveedor"),
                        resultado.getString("direccionProveedor"),
                        resultado.getString("razonSocial"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaWeb")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProveedores = FXCollections.observableArrayList(lista);
    }

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                limpiarControles();
                btnAgregarP.setText("Guardar");
                btnEliminarP.setText("Cancelar");
                btnEditarP.setDisable(true);
                btnReporteP.setDisable(true);
                imgAgregarP.setImage(new Image("/org/josesanchez/Images/guardar.png"));
                imgEliminarP.setImage(new Image("/org/josesanchez/Images/cancelar.png"));
                imgReporteP.setOpacity(0.5);
                imgEditarP.setOpacity(0.5);
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarP.setText("Agregar");
                btnEliminarP.setText("Eliminar");
                btnEditarP.setDisable(false);
                btnReporteP.setDisable(false);
                imgAgregarP.setImage(new Image("/org/josesanchez/Images/business_application_addmale_useradd_insert_add_user_client_2312.png"));
                imgEliminarP.setImage(new Image("/org/josesanchez/Images/delete_delete_deleteusers_delete_male_user_maleclient_2348.png"));
                imgReporteP.setOpacity(0.5);
                imgEditarP.setOpacity(0.5);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void guardar() {
        Proveedores registro = new Proveedores();
        registro.setNITproveedor(txtNitP.getText());
        registro.setNombreProveedor((txtNombreP.getText()));
        registro.setApellidoProveedor(txtApellidoP.getText());
        registro.setDireccionProveedor(txtDireccionP.getText());
        registro.setRazonSocial(txtRazonSocial.getText());
        registro.setContactoPrincipal(txtContactoPrincipal.getText());
        registro.setPaginaWeb(txtPaginaWeb.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProveedores (?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getNITproveedor());
            procedimiento.setString(2, registro.getNombreProveedor());
            procedimiento.setString(3, registro.getApellidoProveedor());
            procedimiento.setString(4, registro.getDireccionProveedor());
            procedimiento.setString(5, registro.getRazonSocial());
            procedimiento.setString(6, registro.getContactoPrincipal());
            procedimiento.setString(7, registro.getPaginaWeb());
            procedimiento.execute();
            ResultSet generatedKeys = procedimiento.getGeneratedKeys();
            if (generatedKeys.next()) {
                registro.setCodigoProveedor(generatedKeys.getInt(1));
            }
            listaProveedores.add(registro);
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
                btnAgregarP.setText("Agregar");
                btnEliminarP.setText("Eliminar");
                btnEditarP.setDisable(false);
                btnReporteP.setDisable(false);
                imgAgregarP.setImage(new Image("/org/josesanchez/Images/business_application_addmale_useradd_insert_add_user_client_2312.png"));
                imgEliminarP.setImage(new Image("/org/josesanchez/Images/delete_delete_deleteusers_delete_male_user_maleclient_2348.png"));
                imgReporteP.setOpacity(1);
                imgEditarP.setOpacity(1);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblProveedor.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Proveedores", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProveedor (?)}");
                            procedimiento.setInt(1, ((Proveedores) tblProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                            procedimiento.execute();
                            limpiarControles();
                            listaProveedores.remove(tblProveedor.getSelectionModel().getSelectedItem());
                        } catch (SQLIntegrityConstraintViolationException e) {
                            JOptionPane.showMessageDialog(null, "No puedes eliminar este registro, esta referenciado en otra clase");
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
                if (tblProveedor.getSelectionModel().getSelectedItem() != null) {
                    seleccionarElemento();
                    btnEditarP.setText("Actualizar");
                    btnReporteP.setText("Cancelar");
                    btnAgregarP.setDisable(true);
                    btnEliminarP.setDisable(true);
                    imgEditarP.setImage(new Image("/org/josesanchez/Images/male-user-edit_25348.png"));
                    imgReporteP.setImage(new Image("/org/josesanchez/Images/cancelar.png"));
                    imgAgregarP.setOpacity(0.5);
                    imgEliminarP.setOpacity(0.5);
                    activarControles();
                    txtCodigoP.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar algun elemento!");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditarP.setText("Editar");
                btnReporteP.setText("Reporte");
                btnAgregarP.setDisable(false);
                btnEliminarP.setDisable(false);
                imgEditarP.setImage(new Image("/org/josesanchez/Images/business_application_addmale_useradd_insert_add_user_client_2312.png"));
                imgReporteP.setImage(new Image("/org/josesanchez/Images/users_12820.png"));
                imgAgregarP.setOpacity(1);
                imgEliminarP.setOpacity(1);
                desactivarControles();   
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }

    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarProveedor (?, ?, ?, ?, ?, ?, ?, ?)}");
            Proveedores registro = (Proveedores) tblProveedor.getSelectionModel().getSelectedItem();
            registro.setCodigoProveedor(Integer.parseInt(txtCodigoP.getText()));
            registro.setNITproveedor(txtNitP.getText());
            registro.setNombreProveedor(txtNombreP.getText());
            registro.setApellidoProveedor(txtApellidoP.getText());
            registro.setDireccionProveedor(txtDireccionP.getText());
            registro.setRazonSocial(txtRazonSocial.getText());
            registro.setContactoPrincipal(txtContactoPrincipal.getText());
            registro.setPaginaWeb(txtPaginaWeb.getText());
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITproveedor());
            procedimiento.setString(3, registro.getNombreProveedor());
            procedimiento.setString(4, registro.getApellidoProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
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
                btnEditarP.setText("Editar");
                btnReporteP.setText("Reporte");
                btnAgregarP.setDisable(false);
                btnEliminarP.setDisable(false);
                imgEditarP.setImage(new Image("/org/josesanchez/Images/business_application_addmale_useradd_insert_add_user_client_2312.png"));
                imgReporteP.setImage(new Image("/org/josesanchez/Images/users_12820.png"));
                imgAgregarP.setOpacity(1);
                imgEliminarP.setOpacity(1);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void desactivarControles() {
        txtCodigoP.setEditable(false);
        txtNitP.setEditable(false);
        txtNombreP.setEditable(false);
        txtApellidoP.setEditable(false);
        txtDireccionP.setEditable(false);
        txtRazonSocial.setEditable(false);
        txtContactoPrincipal.setEditable(false);
        txtPaginaWeb.setEditable(false);

    }

    public void activarControles() {
        txtNitP.setEditable(true);
        txtNombreP.setEditable(true);
        txtApellidoP.setEditable(true);
        txtDireccionP.setEditable(true);
        txtRazonSocial.setEditable(true);
        txtContactoPrincipal.setEditable(true);
        txtPaginaWeb.setEditable(true);

    }

    public void limpiarControles() {
        txtCodigoP.clear();
        txtNitP.clear();
        txtNombreP.clear();
        txtApellidoP.clear();
        txtDireccionP.clear();
        txtRazonSocial.clear();
        txtContactoPrincipal.clear();
        txtPaginaWeb.clear();
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
