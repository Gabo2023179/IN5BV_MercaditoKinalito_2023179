/*
Documentacion

Nombre Completo: Jose Gabriel Contreras Sancehez
Carnet: 2023179
Grado Y Seccion: IN5BV
Fecha de Creacion: 25/04/24
Fecha de Modificacion: 10/05/24
 */
package org.josesanchez.system;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.josesanchez.controllers.MenuCargoEmpleadosController;
import org.josesanchez.controllers.MenuCargosController;
import org.josesanchez.controllers.MenuClientesController;
import org.josesanchez.controllers.MenuComprasController;
import org.josesanchez.controllers.MenuDetalleCompraController;
import org.josesanchez.controllers.MenuDetalleFacturaController;
import org.josesanchez.controllers.MenuEmailProveedorController;
import org.josesanchez.controllers.MenuEmpleadosController;
import org.josesanchez.controllers.MenuFacturaController;
import org.josesanchez.controllers.MenuPrincipalController;
import org.josesanchez.controllers.MenuProductosController;
import org.josesanchez.controllers.MenuProgramadorController;
import org.josesanchez.controllers.MenuProveedoresController;
import org.josesanchez.controllers.MenuTelefonoProveedorController;
import org.josesanchez.controllers.MenuTipoDeProductosController;

/**
 *
 * @author informatica
 */
public class Main extends Application {

    private Stage escenarioPrincipal;
    private Scene escena;
    private final String URLVIEW = "/org/josesanchez/views/";

    @Override
    public void start(Stage escenarioPrincipal) throws IOException {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Mercadito Kinalito");
        menuPrincipalView();
//Parent root = FXMLLoader.load(getClass().getResource(URLVIEW + "MenuPrincipalView.fxml"));
        //Scene escena = new Scene(root);
        //escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public Initializable cambiarEscena(String fxmlName, int width, int heigth) throws IOException {
        Initializable resultado;
        FXMLLoader loader = new FXMLLoader();

        InputStream file = Main.class.getResourceAsStream(URLVIEW + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(URLVIEW + fxmlName));

        escena = new Scene((AnchorPane) loader.load(file), width, heigth);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable) loader.getController();

        return resultado;
    }

    public void menuPrincipalView() {
        try {
            MenuPrincipalController menuPrincipal = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 1340, 732);
            menuPrincipal.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void menuClientesView() {
        try {
            MenuClientesController menuCliente = (MenuClientesController) cambiarEscena("MenuClientesView.fxml", 1302, 732);
            menuCliente.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void menuProgramadorView() {
        try {
            MenuProgramadorController menuProgramador = (MenuProgramadorController) cambiarEscena("MenuProgramadorView.fxml", 1290, 725
            );
            menuProgramador.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void menuProveedoresView() {
        try {
            MenuProveedoresController menuProveedor = (MenuProveedoresController) cambiarEscena("MenuProveedoresView.fxml", 1290, 720);
            menuProveedor.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void menuCargosView() {
        try {
            MenuCargosController menuCargos = (MenuCargosController) cambiarEscena("MenuCargosView.fxml", 1212, 681);
            menuCargos.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void menuTipoDeProductosView() {
        try {
            MenuTipoDeProductosController menuTipoDeProductos = (MenuTipoDeProductosController) cambiarEscena("MenuTipoDeProductosView.fxml", 1212, 681);
            menuTipoDeProductos.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void menuComprasView() {
        try {
            MenuComprasController menuCompras = (MenuComprasController) cambiarEscena("MenuComprasView.fxml", 1154, 651);
            menuCompras.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void menuProductosView() {
        try {
            MenuProductosController menuProductos = (MenuProductosController) cambiarEscena("MenuProductosView.fxml", 1298, 734);
            menuProductos.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void menuCargoEmpleadosView() {
        try {
            MenuCargoEmpleadosController menuCargoEmpleados = (MenuCargoEmpleadosController) cambiarEscena("MenuCargoEmpleadosView.fxml", 1254, 704);
            menuCargoEmpleados.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void menuDetalleCompraView() {
        try {
            MenuDetalleCompraController menuDetalleCompra = (MenuDetalleCompraController) cambiarEscena("MenuDetalleCompraView.fxml", 1075, 605);
            menuDetalleCompra.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void menuDetalleFacturaView() {
        try {
            MenuDetalleFacturaController menuDetalleFactura = (MenuDetalleFacturaController) cambiarEscena("MenuDetalleFacturaView.fxml", 1027, 572);
            menuDetalleFactura.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void menuEmailProveedorView() {
        try {
            MenuEmailProveedorController menuEmailProveedor = (MenuEmailProveedorController) cambiarEscena("MenuEmailProveedorView.fxml", 1094, 611);
            menuEmailProveedor.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void menuEmpleadosView() {
        try {
            MenuEmpleadosController menuEmpleados = (MenuEmpleadosController) cambiarEscena("MenuEmpleadosView.fxml", 1208, 684);
            menuEmpleados.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void menuFacturaView() {
        try {
            MenuFacturaController menuFactura = (MenuFacturaController) cambiarEscena("MenuFacturaView.fxml", 1234, 695);
            menuFactura.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void menuTelefonoProveedorView() {
        try {
            MenuTelefonoProveedorController menuTelefonoProveedor = (MenuTelefonoProveedorController) cambiarEscena("MenuTelefonoProveedorView.fxml", 1165, 652);
            menuTelefonoProveedor.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
