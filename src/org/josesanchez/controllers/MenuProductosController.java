/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josesanchez.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import org.josesanchez.dbs.Conexion;
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
    private ObservableList<Producto> listaProducto;
    private ObservableList<Proveedor> listaProveedor;
    private ObservableList<TipoDeProducto> listarTdp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    public ObservableList<Producto> getProducto(){
        ArrayList<Producto> lista = new ArrayList<Producto>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{sp_ListarProductos ()}");
            ResultSet resultado = procedimeinto.executeQuery();
            while(resultado.next()){
            lista.add(new Producto (resultado.getString("productoId"),
                        resultado.getString("descripcionProducto"),
                        resultado.getInt("")
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return 
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

}
