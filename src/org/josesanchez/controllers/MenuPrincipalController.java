/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josesanchez.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.josesanchez.system.Main;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

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
    MenuItem btnMenuCargoEmpleados;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
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
        if (event.getSource() == btnMenuCargoEmpleados) {
            escenarioPrincipal.menuCargoEmpleadosView();
        }
    }

}
