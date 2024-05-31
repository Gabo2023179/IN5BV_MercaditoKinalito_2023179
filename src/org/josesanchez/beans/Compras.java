/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josesanchez.beans;

import java.sql.Date;
import java.time.LocalDate;

public class Compras {
    private int compraId;
    private LocalDate fechaCompra;
    private String descripcion;
    private double totalCompra;
    

    public Compras() {
    }

    public Compras(int compraId, LocalDate fechaCompra, String descripcion, double totalCompra) {
        this.compraId = compraId;
        this.fechaCompra = fechaCompra;
        this.descripcion = descripcion;
        this.totalCompra = totalCompra;
    }

    

    public int getCompraId() {
        return compraId;
    }

    public void setCompraId(int compraId) {
        this.compraId = compraId;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(double totalCompra) {
        this.totalCompra = totalCompra;
    }

    @Override
    public String toString(){
        return "| " + getCompraId();
    }
    
}
