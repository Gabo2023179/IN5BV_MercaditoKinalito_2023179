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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.josesanchez.beans.Proveedores;
import org.josesanchez.beans.TelefonoProveedor;
import org.josesanchez.dbs.Conexion;
import org.josesanchez.system.Main;

public class MenuTelefonoProveedorController implements Initializable{
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private ObservableList<TelefonoProveedor> listaTelPro;
    private ObservableList<Proveedores> listaProveedores;
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    private Main escenarioPrincipal;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtCodTelPro;
    @FXML
    private TextField txtNumPrin;
    @FXML
    private TextField txtNumSecun;
    @FXML
    private TextField txtObserv;
    @FXML
    private ComboBox cmbCodProv;
    @FXML
    private TableView tblTelPro;
    @FXML
    private TableColumn colCodTelPro;
    @FXML
    private TableColumn colNumPrin;
    @FXML
    private TableColumn colNumSecun;
    @FXML
    private TableColumn colObserv;
    @FXML
    private TableColumn colCodProv;
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
        cmbCodProv.setItems(getProveedores());
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void cargaDatos() {
        tblTelPro.setItems(getTelefonoProveedor());
        colCodTelPro.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, String>("codigoTelefonoProveedor"));
        colNumPrin.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, String>("numeroPrincipal"));
        colNumSecun.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, Double>("numeroSecundario"));
        colObserv.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, Double>("observaciones"));
        colCodProv.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, Double>("codigoProveedor"));
    }

    public void seleccionarElementos() {
        txtCodTelPro.setText(String.valueOf(((TelefonoProveedor) tblTelPro.getSelectionModel().getSelectedItem()).getCodigoTelefonoProveedor()));
        txtNumPrin.setText(((TelefonoProveedor) tblTelPro.getSelectionModel().getSelectedItem()).getNumeroPrincipal());
        txtNumSecun.setText(String.valueOf(((TelefonoProveedor) tblTelPro.getSelectionModel().getSelectedItem()).getNumeroSecundario()));
        txtObserv.setText(String.valueOf(((TelefonoProveedor) tblTelPro.getSelectionModel().getSelectedItem()).getObservaciones()));
        cmbCodProv.getSelectionModel().select(buscarProveedor(((TelefonoProveedor) tblTelPro.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
    }

    public Proveedores buscarProveedor(int codigoProveedor) {
        Proveedores resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProveedores(?)}");
            procedimiento.setInt(1, codigoProveedor);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Proveedores(registro.getInt("codigoProveedor"),
                        registro.getString("NITproveedor"),
                        registro.getString("nombreProveedor"),
                        registro.getString("apellidoProveedor"),
                        registro.getString("direccionProveedor"),
                        registro.getString("razonSocial"),
                        registro.getString("contactoPrincipal"),
                        registro.getString("paginaWeb")
                );

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public ObservableList<TelefonoProveedor> getTelefonoProveedor() {
        ArrayList<TelefonoProveedor> lista = new ArrayList<TelefonoProveedor>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTelefonoProveedor ()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new TelefonoProveedor(resultado.getInt("codigoTelefonoProveedor"),
                        resultado.getString("numeroPrincipal"),
                        resultado.getString("numeroSecundario"),
                        resultado.getString("observaciones"),
                        resultado.getInt("codigoProveedor")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaTelPro = FXCollections.observableArrayList(lista);
    }

    public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProveedores()}");
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
                btnAgregar.setText("guardar");
                btnEliminar.setText("cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("/org/josesanchez/Images/guardar.png"));
                imgEliminar.setImage(new Image("/org/josesanchez/Images/cancelar.png"));
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
                imgAgregar.setImage(new Image("/org/josesanchez/Images/AgregarTelefonoProveedor.png"));
                imgEliminar.setImage(new Image("/org/josesanchez/Images/EliminarTelefonoProveedor.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void guardar() {
        TelefonoProveedor registro = new TelefonoProveedor();
        registro.setCodigoProveedor(((Proveedores) cmbCodProv.getSelectionModel().getSelectedItem())
                .getCodigoProveedor());
        registro.setNumeroPrincipal(txtNumPrin.getText());
        registro.setNumeroSecundario(txtNumSecun.getText());
        registro.setObservaciones(txtObserv.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTelefonoProveedor(?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getNumeroPrincipal());
            procedimiento.setString(2, registro.getNumeroSecundario());
            procedimiento.setString(3, registro.getObservaciones());
            procedimiento.setInt(4, registro.getCodigoProveedor());
            procedimiento.execute();
            ResultSet generatedKeys = procedimiento.getGeneratedKeys();
            if(generatedKeys.next()){
                registro.setCodigoTelefonoProveedor(generatedKeys.getInt(1));
            }
            listaTelPro.add(registro);
            cargaDatos();
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
                imgAgregar.setImage(new Image("/org/josesanchez/Images/AgregarTelefonoProveedor.png"));
                imgEliminar.setImage(new Image("/org/josesanchez/Images/EliminarTelefonoProveedor.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblTelPro.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Productos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTelefonoProveedor (?)}");
                            procedimiento.setInt(1, ((TelefonoProveedor) tblTelPro.getSelectionModel().getSelectedItem()).getCodigoTelefonoProveedor());
                            procedimiento.execute();
                            limpiarControles();
                            listaTelPro.remove(tblTelPro.getSelectionModel().getSelectedItem());
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
                if (tblTelPro.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/josesanchez/Images/editartipodeproducto.png"));
                    imgReporte.setImage(new Image("/org/josesanchez/Images/cancelar.png"));
                    activarControles();
                    txtCodTelPro.setEditable(false);
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
                imgEditar.setImage(new Image("/org/josesanchez/Images/EditarTelefonoProveedor.png"));
                imgReporte.setImage(new Image("/org/josesanchez/Images/Accounting_icon-icons.com_74682.png"));
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargaDatos();
                break;
        }

    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarTelefonoProveedor (?, ?, ?, ?, ?)}");
            TelefonoProveedor registro = (TelefonoProveedor) tblTelPro.getSelectionModel().getSelectedItem();
            registro.setCodigoTelefonoProveedor(Integer.parseInt(txtCodTelPro.getText()));
            registro.setNumeroPrincipal(txtNumPrin.getText());
            registro.setNumeroSecundario(txtNumSecun.getText());
            registro.setObservaciones(txtObserv.getText());
            registro.setCodigoProveedor(((Proveedores) cmbCodProv.getValue()).getCodigoProveedor());
            procedimiento.setInt(1, registro.getCodigoTelefonoProveedor());
            procedimiento.setString(2, registro.getNumeroPrincipal());
            procedimiento.setString(3, registro.getNumeroSecundario());
            procedimiento.setString(4, registro.getObservaciones());
            procedimiento.setInt(5, registro.getCodigoProveedor());
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
                imgEditar.setImage(new Image("/org/josesanchez/Images/EditarTelefonoProveedor.png"));
                imgReporte.setImage(new Image("/org/josesanchez/Images/Accounting_icon-icons.com_74682.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void desactivarControles() {
        txtCodTelPro.setEditable(false);
        txtNumPrin.setEditable(false);
        txtNumSecun.setEditable(false);
        txtObserv.setEditable(false);
        cmbCodProv.setDisable(true);
    }

    public void activarControles() {
        txtNumPrin.setEditable(true);
        txtNumSecun.setEditable(true);
        txtObserv.setEditable(true);
        cmbCodProv.setDisable(false);
    }

    public void limpiarControles() {
        txtCodTelPro.clear();
        txtNumPrin.clear();
        txtNumSecun.clear();
        txtObserv.clear();
        cmbCodProv.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void regresar(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
    
}
