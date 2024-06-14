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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.josesanchez.beans.Productos;
import org.josesanchez.beans.Proveedores;
import org.josesanchez.beans.TipoDeProductos;
import org.josesanchez.dbs.Conexion;
import org.josesanchez.reports.GenerarReportes;
import org.josesanchez.system.Main;

/**
 *
 * @author informatica
 */
public class MenuProductosController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    };
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Productos> listaProductos;
    private ObservableList<Proveedores> listaProveedores;
    private ObservableList<TipoDeProductos> listaTdp;

    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtCodigoProd;
    @FXML
    private TextField txtDescPro;
    @FXML
    private TextField txtPrecioU;
    @FXML
    private TextField txtPrecioD;
    @FXML
    private TextField txtPrecioM;
    @FXML
    private TextField txtImagenPro;
    @FXML
    private TextField txtExistencia;
    @FXML
    private ComboBox cmbCodigoTipoP;
    @FXML
    private ComboBox cmbCodProv;
    @FXML
    private TableView tblProductos;
    @FXML
    private TableColumn colCodProd;
    @FXML
    private TableColumn colDesProd;
    @FXML
    private TableColumn colPrecioU;
    @FXML
    private TableColumn colPrecioD;
    @FXML
    private TableColumn colPrecioM;
    @FXML
    private TableColumn colImagenPro;
    @FXML
    private TableColumn colExistencia;
    @FXML
    private TableColumn colCodTipoProd;
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
        if (true) {
            cmbCodigoTipoP.setItems(getTipoDeProductos());
            cmbCodProv.setItems(getProveedores());
        }
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void cargaDatos() {
        tblProductos.setItems(getProductos());
        colCodProd.setCellValueFactory(new PropertyValueFactory<Productos, String>("productoId"));
        colDesProd.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcionProducto"));
        colPrecioU.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioUnitario"));
        colPrecioD.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioDocena"));
        colPrecioM.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioMayor"));
        colImagenPro.setCellValueFactory(new PropertyValueFactory<Productos, String>("imagenProducto"));
        colExistencia.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("existencia"));
        colCodProv.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("codigoProveedor"));
        colCodTipoProd.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("codigoTipoDeProducto"));
    }

    public void seleccionarElementos() {
        txtCodigoProd.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getProductoId()));
        txtDescPro.setText(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getDescripcionProducto());
        txtPrecioU.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
        txtPrecioD.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getPrecioDocena()));
        txtPrecioM.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getPrecioMayor()));
        txtImagenPro.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getImagenProducto()));
        txtExistencia.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getExistencia()));
        cmbCodProv.getSelectionModel().select(buscarProveedor(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        cmbCodigoTipoP.getSelectionModel().select(buscarTipoP(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getCodigoTipoDeProducto()));

    }

    public TipoDeProductos buscarTipoP(int codigoTipoDeProducto) {
        TipoDeProductos resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarTipoDeProducto(?)}");
            procedimiento.setInt(1, codigoTipoDeProducto);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new TipoDeProductos(registro.getInt("codigoTipoDeProducto"),
                        registro.getString("descripcion")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
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
                        resultado.getInt("codigoProveedor"),
                        resultado.getInt("codigoTipoDeProducto")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProductos = FXCollections.observableArrayList(lista);
    }

    public ObservableList<TipoDeProductos> getTipoDeProductos() {
        ArrayList<TipoDeProductos> lista = new ArrayList<TipoDeProductos>();
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
        Productos registro = new Productos();
        registro.setCodigoProveedor(((Proveedores) cmbCodProv.getSelectionModel().getSelectedItem())
                .getCodigoProveedor());
        registro.setCodigoTipoDeProducto(((TipoDeProductos) cmbCodigoTipoP.getSelectionModel().getSelectedItem()).getCodigoTipoDeProducto());
        registro.setDescripcionProducto(txtDescPro.getText());
        registro.setImagenProducto(txtImagenPro.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProductos(?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getDescripcionProducto());
            procedimiento.setString(2, registro.getImagenProducto());
            procedimiento.setInt(3, registro.getCodigoProveedor());
            procedimiento.setInt(4, registro.getCodigoTipoDeProducto());
            procedimiento.execute();
            ResultSet generatedKeys = procedimiento.getGeneratedKeys();
            if (generatedKeys.next()) {
                registro.setProductoId(generatedKeys.getInt(1));
            }
            listaProductos.add(registro);
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
                if (tblProductos.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Productos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProducto (?)}");
                            procedimiento.setInt(1, ((Productos) tblProductos.getSelectionModel().getSelectedItem()).getProductoId());
                            procedimiento.execute();
                            limpiarControles();
                            listaTdp.remove(tblProductos.getSelectionModel().getSelectedItem());
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
        switch (tipoDeOperacion) {

            case NINGUNO:
                if (tblProductos.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/josesanchez/Images/editartipodeproducto.png"));
                    imgReporte.setImage(new Image("/org/josesanchez/Images/cancelar.png"));
                    imgAgregar.setOpacity(0.5);
                    imgEliminar.setOpacity(0.5);
                    activarControles();
                    txtCodigoProd.setEditable(false);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarProductos (?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            Productos registro = (Productos) tblProductos.getSelectionModel().getSelectedItem();
            registro.setProductoId(Integer.parseInt(txtCodigoProd.getText()));
            registro.setDescripcionProducto(txtDescPro.getText());
            registro.setPrecioMayor(Double.parseDouble(txtPrecioU.getText()));
            registro.setPrecioMayor(Double.parseDouble(txtPrecioD.getText()));
            registro.setPrecioMayor(Double.parseDouble(txtPrecioM.getText()));
            registro.setImagenProducto(txtExistencia.getText());
            registro.setCodigoProveedor(((Proveedores) cmbCodProv.getValue()).getCodigoProveedor());
            registro.setCodigoTipoDeProducto(((TipoDeProductos) cmbCodigoTipoP.getValue()).getCodigoTipoDeProducto());
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
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
    
     public void imprimirReporte(){
        Map parametros = new HashMap();
        parametros.put("descripcionProducto", null);
        GenerarReportes.mostrarReportes("reportProductos.jasper", "Reporte de los Productos", parametros);
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void desactivarControles() {
        txtCodigoProd.setEditable(false);
        txtDescPro.setEditable(false);
        txtImagenPro.setEditable(false);
        txtPrecioU.setEditable(false);
        txtPrecioD.setEditable(false);
        txtPrecioM.setEditable(false);
        txtExistencia.setEditable(false);
        cmbCodProv.setDisable(true);
        cmbCodigoTipoP.setDisable(true);
    }

    public void activarControles() {
        txtDescPro.setEditable(true);
        txtImagenPro.setEditable(true);
        cmbCodProv.setDisable(false);
        cmbCodigoTipoP.setDisable(false);
    }

    public void limpiarControles() {
        txtCodigoProd.clear();
        txtDescPro.clear();
        txtPrecioU.clear();
        txtPrecioD.clear();
        txtPrecioM.clear();
        txtExistencia.clear();
        cmbCodProv.getSelectionModel().getSelectedItem();
        cmbCodigoTipoP.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void regresar(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

}
