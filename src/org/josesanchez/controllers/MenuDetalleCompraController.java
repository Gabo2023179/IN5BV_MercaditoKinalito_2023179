package org.josesanchez.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
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
import org.josesanchez.beans.Compras;
import org.josesanchez.beans.DetalleCompra;
import org.josesanchez.beans.Productos;
import org.josesanchez.dbs.Conexion;
import org.josesanchez.system.Main;

public class MenuDetalleCompraController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    };
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<DetalleCompra> listaDCompra;
    private ObservableList<Productos> listaProductos;
    private ObservableList<Compras> listaCompras;

    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtCodigoDC;
    @FXML
    private TextField txtCostoU;
    @FXML
    private TextField txtCantidad;
    @FXML
    private ComboBox cmbProCodPro;
    @FXML
    private ComboBox cmbComNumDoc;
    @FXML
    private TableView tblDetalleProducto;
    @FXML
    private TableColumn colCodigoDc;
    @FXML
    private TableColumn colCostoU;
    @FXML
    private TableColumn colCantidad;
    @FXML
    private TableColumn colProCodPro;
    @FXML
    private TableColumn colComNumDoc;
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
        cmbProCodPro.setItems(getProductos());
        cmbComNumDoc.setItems(getCompras());
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void cargaDatos() {
        tblDetalleProducto.setItems(getDetalleCompra());
        colCodigoDc.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("codigoDetalleCompra"));
        colCostoU.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Double>("costoUnitario"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("cantidad"));
        colProCodPro.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("productoId"));
        colComNumDoc.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("compraId"));
    }

    public void seleccionarElementos() {
        txtCodigoDC.setText(String.valueOf(((DetalleCompra) tblDetalleProducto.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra()));
        txtCostoU.setText(String.valueOf(((DetalleCompra) tblDetalleProducto.getSelectionModel().getSelectedItem()).getCostoUnitario()));
        txtCantidad.setText(String.valueOf(((DetalleCompra) tblDetalleProducto.getSelectionModel().getSelectedItem()).getCantidad()));
        cmbProCodPro.getSelectionModel().select(buscarProductos(((DetalleCompra) tblDetalleProducto.getSelectionModel().getSelectedItem()).getProductoId()));
        cmbComNumDoc.getSelectionModel().select(buscarCompras(((DetalleCompra) tblDetalleProducto.getSelectionModel().getSelectedItem()).getCompraId()));
    }

    public Productos buscarProductos(int productoId) {
        Productos resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProductos(?)}");
            procedimiento.setInt(1, productoId);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Productos(registro.getInt("productoId"),
                        registro.getString("descripcionProducto"),
                        registro.getDouble("precioUnitario"),
                        registro.getDouble("precioDocena"),
                        registro.getDouble("precioMayor"),
                        registro.getString("imagenProducto"),
                        registro.getInt("existencia"),
                        registro.getInt("codigoProveedor"),
                        registro.getInt("codigoTipoDeProducto")
                );

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public Compras buscarCompras(int compraId) {
        Compras resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarCompras(?)}");
            procedimiento.setInt(1, compraId);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Compras(registro.getInt("compraId"),
                        registro.getDate("fechaCompra").toLocalDate(),
                        registro.getString("descripcion"),
                        registro.getDouble("totalCompra")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public ObservableList<Compras> getCompras() {
        ArrayList<Compras> lista = new ArrayList<Compras>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCompras()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Compras(
                        resultado.getInt("compraId"),
                        resultado.getDate("fechaCompra").toLocalDate(),
                        resultado.getString("descripcion"),
                        resultado.getDouble("totalCompra")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaCompras = FXCollections.observableArrayList(lista);
    }

    public ObservableList<DetalleCompra> getDetalleCompra() {
        ArrayList<DetalleCompra> lista = new ArrayList<DetalleCompra>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDetalleCompra()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new DetalleCompra(
                        resultado.getInt("codigoDetalleCompra"),
                        resultado.getDouble("costoUnitario"),
                        resultado.getInt("cantidad"),
                        resultado.getInt("productoId"),
                        resultado.getInt("compraId")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaDCompra = FXCollections.observableArrayList(lista);
    }

    public ObservableList<Productos> getProductos() {
        ArrayList<Productos> lista = new ArrayList<Productos>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProductos ()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Productos(resultado.getInt("productoId"),
                        resultado.getString("descripcionProducto"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getDouble("precioDocena"),
                        resultado.getDouble("precioMayor"),
                        resultado.getString("imagenProducto"),
                        resultado.getInt("existencia"),
                        resultado.getInt("codigoTipoDeProducto"),
                        resultado.getInt("codigoProveedor")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProductos = FXCollections.observableArrayList(lista);
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
                imgAgregar.setImage(new Image("/org/josesanchez/Images/AgregarDetalleCompra.png"));
                imgEliminar.setImage(new Image("/org/josesanchez/Images/eliminarGeneral.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }

    public void guardar() {
        DetalleCompra registro = new DetalleCompra();
        registro.setProductoId(((Productos) cmbProCodPro.getSelectionModel().getSelectedItem())
                .getProductoId());
        registro.setCompraId(((Compras) cmbComNumDoc.getSelectionModel().getSelectedItem())
                .getCompraId());
        registro.setCostoUnitario(Double.parseDouble(txtCostoU.getText()));
        registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarDetalleCompra(?, ?, ?, ?)}");
            procedimiento.setDouble(1, registro.getCostoUnitario());
            procedimiento.setInt(2, registro.getCantidad());
            procedimiento.setInt(3, registro.getProductoId());
            procedimiento.setInt(4, registro.getCompraId());
            procedimiento.execute();
            ResultSet generatedKeys = procedimiento.getGeneratedKeys();
            if (generatedKeys.next()) {
                registro.setCodigoDetalleCompra(generatedKeys.getInt(1));
            }
            listaDCompra.add(registro);
            cargaDatos();
        } catch (SQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "No es posible duplicar la compra");
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
                imgAgregar.setImage(new Image("/org/josesanchez/Images/AgregarDetalleCompra.png"));
                imgEliminar.setImage(new Image("/org/josesanchez/Images/eliminarGeneral.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            default:
                if (tblDetalleProducto.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Detalle Compra", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarDetalleCompra (?)}");
                            procedimiento.setInt(1, ((DetalleCompra) tblDetalleProducto.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra());
                            procedimiento.execute();
                            limpiarControles();
                            listaDCompra.remove(tblDetalleProducto.getSelectionModel().getSelectedItem());
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
                if (tblDetalleProducto.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/josesanchez/Images/editartipodeproducto.png"));
                    imgReporte.setImage(new Image("/org/josesanchez/Images/cancelar.png"));
                    activarControles();
                    txtCodigoDC.setEditable(false);
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
                imgEditar.setImage(new Image("/org/josesanchez/Images/EditarGeneral.png"));
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarDetalleCompra (?, ?, ?, ?, ?)}");
            DetalleCompra registro = (DetalleCompra) tblDetalleProducto.getSelectionModel().getSelectedItem();
            double costoUnitario = Double.parseDouble(txtCostoU.getText());
            registro.setCostoUnitario(costoUnitario);
            int cantidad = Integer.parseInt(txtCantidad.getText());
            registro.setCantidad(cantidad);
            procedimiento.setInt(1, registro.getCodigoDetalleCompra());
            procedimiento.setDouble(2, registro.getCostoUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setInt(4, registro.getProductoId());
            procedimiento.setInt(5, registro.getCompraId());
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
                imgEditar.setImage(new Image("/org/josesanchez/Images/EditarGeneral.png"));
                imgReporte.setImage(new Image("/org/josesanchez/Images/Accounting_icon-icons.com_74682.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void desactivarControles() {
        txtCodigoDC.setEditable(false);
        txtCostoU.setEditable(false);
        txtCantidad.setEditable(false);
        cmbProCodPro.setDisable(true);
        cmbComNumDoc.setDisable(true);

    }

    public void activarControles() {
        txtCostoU.setEditable(true);
        txtCantidad.setEditable(true);
        cmbProCodPro.setDisable(false);
        cmbComNumDoc.setDisable(false);

    }

    public void limpiarControles() {
        txtCodigoDC.clear();
        txtCostoU.clear();
        txtCantidad.clear();
        cmbProCodPro.getSelectionModel().getSelectedItem();
        cmbComNumDoc.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void regresar(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
