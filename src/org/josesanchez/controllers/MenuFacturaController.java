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
import org.josesanchez.beans.Clientes;
import org.josesanchez.beans.Empleados;
import org.josesanchez.beans.Factura;
import org.josesanchez.beans.Productos;
import org.josesanchez.dbs.Conexion;
import org.josesanchez.system.Main;

public class MenuFacturaController implements Initializable{
    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    };
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Factura> listaFactura;
    private ObservableList<Clientes> listaClientes;
    private ObservableList<Empleados> listaEmpleados;

    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtNumFactura;
    @FXML
    private TextField txtEstado;
    @FXML
    private TextField txtTotalFac;
    @FXML
    private TextField txtFechaFac;
    @FXML
    private ComboBox cmbCodigoCliente;
    @FXML
    private ComboBox cmbCodEmpleado;
    @FXML
    private TableView tblFactura;
    @FXML
    private TableColumn colNumFactura;
    @FXML
    private TableColumn colEstado;
    @FXML
    private TableColumn colTotalFac;
    @FXML
    private TableColumn colFechaFac;
    @FXML
    private TableColumn colCodClientes;
    @FXML
    private TableColumn colCodEmpelados;
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
        cmbCodigoCliente.setItems(getClientes());
        cmbCodEmpleado.setItems(getEmpleados());
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void cargaDatos() {
        tblFactura.setItems(getFactura());
        colNumFactura.setCellValueFactory(new PropertyValueFactory<Factura, String>("numeroFactura"));
        colEstado.setCellValueFactory(new PropertyValueFactory<Factura, String>("estado"));
        colTotalFac.setCellValueFactory(new PropertyValueFactory<Factura, Double>("totalFactura"));
        colFechaFac.setCellValueFactory(new PropertyValueFactory<Factura, Double>("fechaFactura"));
        colCodClientes.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("codigoCliente"));
        colCodEmpelados.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("codigoEmpleado"));
    }

    public void seleccionarElementos() {
        txtNumFactura.setText(String.valueOf(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getNumeroFactura()));
        txtEstado.setText(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getEstado());
        txtTotalFac.setText(String.valueOf(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getTotalFactura()));
        txtFechaFac.setText(String.valueOf(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getFechaFactura()));
        cmbCodigoCliente.getSelectionModel().select(buscarClientes(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        cmbCodEmpleado.getSelectionModel().select(buscarEmpleados(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));

    }

    public Clientes buscarClientes(int codigoCliente) {
        Clientes resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarClientes(?)}");
            procedimiento.setInt(1, codigoCliente);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Clientes(registro.getInt("codigoCliente"),
                        registro.getString("NITCliente"),
                        registro.getString("nombreCliente"),
                        registro.getString("apellidoCliente"),
                        registro.getString("direccionCliente"),
                        registro.getString("telefonoCliente"),
                        registro.getString("correoCliente")
                );
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }
    public Empleados buscarEmpleados(int codigoEmpleado) {
        Empleados resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarEmpleados(?)}");
            procedimiento.setInt(1, codigoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Empleados(registro.getInt("codigoEmpleado"),
                        registro.getString("nombresEmpleado"),
                        registro.getString("apellidosEmpleado"),
                        registro.getDouble("sueldo"),
                        registro.getString("direccion"),
                        registro.getString("turno"),
                        registro.getInt("codigoCargoEmpleado")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }
    

    public ObservableList<Factura> getFactura() {
        ArrayList<Factura> lista = new ArrayList<Factura>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarFacturas ()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Factura(resultado.getInt("numeroFactura"),
                        resultado.getString("estado"),
                        resultado.getDouble("totalFactura"),
                        resultado.getString("fechaFactura"),
                        resultado.getInt("codigoCliente"),
                        resultado.getInt("codigoEmpleado")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaFactura = FXCollections.observableArrayList(lista);
    }

     public ObservableList<Clientes> getClientes() {
        ArrayList<Clientes> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarClientes ()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Clientes(resultado.getInt("codigoCliente"),
                        resultado.getString("NITCliente"),
                        resultado.getString("nombreCliente"),
                        resultado.getString("apellidoCliente"),
                        resultado.getString("direccionCliente"),
                        resultado.getString("telefonoCliente"),
                        resultado.getString("correoCliente")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaClientes = FXCollections.observableArrayList(lista);
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

    public void agregar() {
        switch (tipoDeOperacion) {
            case NINGUNO:
                activarControles();
                btnAgregar.setText("guardar");
                btnEliminar.setText("cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("/org/josesanchez/Images/guardar.png"));
                imgEliminar.setImage(new Image("/org/josesanchez/Images/cancelar.png"));
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
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }

    public void guardar() {
        Factura registro = new Factura();
        registro.setCodigoCliente(((Clientes) cmbCodigoCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
        registro.setCodigoEmpleado(((Empleados) cmbCodEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
        registro.setEstado(txtEstado.getText());
        registro.setTotalFactura(Double.parseDouble(txtTotalFac.getText()));
        registro.setFechaFactura(txtFechaFac.getText());
        try {
             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarFactura(?, ?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getEstado());
            procedimiento.setDouble(2, registro.getTotalFactura());
            procedimiento.setString(3, registro.getFechaFactura());
            procedimiento.setInt(4, registro.getCodigoCliente());
            procedimiento.setInt(5, registro.getCodigoEmpleado());
            procedimiento.execute();
            ResultSet generatedKeys = procedimiento.getGeneratedKeys();
            if(generatedKeys.next()){
                registro.setNumeroFactura(generatedKeys.getInt(1));
            }
            listaFactura.add(registro);
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
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            default:
                if (tblFactura.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Factura", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarFactura (?)}");
                            procedimiento.setInt(1, ((Factura) tblFactura.getSelectionModel().getSelectedItem()).getNumeroFactura());
                            procedimiento.execute();
                            limpiarControles();
                            listaFactura.remove(tblFactura.getSelectionModel().getSelectedItem());
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
                if (tblFactura.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/josesanchez/Images/editartipodeproducto.png"));
                    imgReporte.setImage(new Image("/org/josesanchez/Images/cancelar.png"));
                    activarControles();
                    txtNumFactura.setEditable(false);
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
                desactivarControles();
                limpiarControles();
                tipoDeOperacion = operaciones.NINGUNO;
                cargaDatos();
                break;
        }

    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarFactura (?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            Productos registro = (Productos) tblFactura.getSelectionModel().getSelectedItem();
            procedimiento.setInt(1, registro.getProductoId());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setDouble(3, registro.getPrecioUnitario());
            procedimiento.setDouble(4, registro.getPrecioDocena());
            procedimiento.setDouble(5, registro.getPrecioMayor());
            procedimiento.setString(6, registro.getImagenProducto());
            procedimiento.setInt(7, registro.getExistencia());
            procedimiento.setInt(8, registro.getCodigoProveedor());
            procedimiento.setInt(9, registro.getCodigoTipoDeProducto());
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
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void desactivarControles() {
        txtNumFactura.setEditable(false);
        txtEstado.setEditable(false);
        txtTotalFac.setEditable(false);
        txtFechaFac.setEditable(false);
        cmbCodigoCliente.setEditable(false);
        cmbCodEmpleado.setEditable(false);
    }

    public void activarControles() {
        txtEstado.setEditable(true);
        txtTotalFac.setEditable(true);
        txtFechaFac.setEditable(true);
        cmbCodigoCliente.setEditable(true);
        cmbCodEmpleado.setEditable(true);
    }

    public void limpiarControles() {
        txtNumFactura.clear();
        txtEstado.clear();
        txtTotalFac.clear();
        txtFechaFac.clear();
        cmbCodigoCliente.getSelectionModel().getSelectedItem();
        cmbCodEmpleado.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void regresar(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

}
