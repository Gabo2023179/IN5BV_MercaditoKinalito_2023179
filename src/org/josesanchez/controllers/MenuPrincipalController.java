/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josesanchez.controllers;

import java.net.URL;
import javafx.util.Duration;
import java.util.ResourceBundle;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.josesanchez.system.Main;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.shape.Circle;


public class MenuPrincipalController implements Initializable {

    private Main escenarioPrincipal;

    @FXML
    MenuItem btnMenuClientes;
    @FXML
    MenuItem btnMenuProgramador;
    @FXML
    MenuItem btnMenuProveedores;
    @FXML
    MenuItem btnMenuCargos;
    @FXML
    MenuItem btnMenuTdp;
    @FXML
    MenuItem btnMenuCompras;
    @FXML
    MenuItem btnMenuProductos;
    @FXML
    MenuItem btnMenuDetalleCompra;
    @FXML
    MenuItem btnMenuDetalleFactura;
    @FXML
    MenuItem btnMenuEmailProveedor;
    @FXML
    MenuItem btnMenuEmpleados;
    @FXML
    MenuItem btnMenuFactura;
    @FXML
    MenuItem btnMenuTelefonoProveedor;
    @FXML
    private Circle c1;
    @FXML
    private Circle c2;
    @FXML
    private Button play;

   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
            play();
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    public void play(ActionEvent event){
        setRotate(c1,true,360,10);
        setRotate(c2,true,360,18);
    }
    
     @FXML
    private void play() {
        setRotate(c1,true,360,10);
        setRotate(c2,true,360,18);
    }
    
    

    private void setRotate(Circle c, boolean reverse, int angle, int duration) {
        RotateTransition rt = new RotateTransition(Duration.seconds(duration),c);

        rt.setAutoReverse(reverse);
        rt.setByAngle(angle);
        rt.setDelay(Duration.seconds(0));
        rt.setRate(3);
        rt.setCycleCount(18);
        rt.play();
    }


    @FXML
    public void clicClientes(ActionEvent event) {
        if (event.getSource() == btnMenuClientes) {
            escenarioPrincipal.menuClientesView();
        }
        if (event.getSource() == btnMenuProgramador) {
            escenarioPrincipal.menuProgramadorView();
        }
        if (event.getSource() == btnMenuProveedores) {
            escenarioPrincipal.menuProveedoresView();
        }
        if (event.getSource() == btnMenuCargos) {
            escenarioPrincipal.menuCargosView();
        }
        if (event.getSource() == btnMenuTdp) {
            escenarioPrincipal.menuTipoDeProductosView();
        }
        if (event.getSource() == btnMenuCompras) {
            escenarioPrincipal.menuComprasView();
        }
        if (event.getSource() == btnMenuProductos) {
            escenarioPrincipal.menuProductosView();
        }
        if (event.getSource() == btnMenuDetalleCompra) {
            escenarioPrincipal.menuDetalleCompraView();
        }
        if (event.getSource() == btnMenuDetalleFactura) {
            escenarioPrincipal.menuDetalleFacturaView();
        }
        if (event.getSource() == btnMenuEmailProveedor) {
            escenarioPrincipal.menuEmailProveedorView();
        }
        if (event.getSource() == btnMenuEmpleados) {
            escenarioPrincipal.menuEmpleadosView();
        }
        if (event.getSource() == btnMenuFactura) {
            escenarioPrincipal.menuFacturaView();
        }
        if (event.getSource() == btnMenuTelefonoProveedor) {
            escenarioPrincipal.menuTelefonoProveedorView();
        }
    }

}
