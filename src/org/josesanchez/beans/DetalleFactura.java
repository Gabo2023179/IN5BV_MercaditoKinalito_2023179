package org.josesanchez.beans;

public class DetalleFactura {

    private int codigoDetalleFactura;
    private double precioUnitario;
    private String cantidad;
    private int numeroFactura;
    private int productoId;

    public DetalleFactura() {
    }

    public DetalleFactura(int codigoDetalleFactura, double precioUnitario, String cantidad, int numeroFactura, int productoId) {
        this.codigoDetalleFactura = codigoDetalleFactura;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.numeroFactura = numeroFactura;
        this.productoId = productoId;
    }

    public int getCodigoDetalleFactura() {
        return codigoDetalleFactura;
    }

    public void setCodigoDetalleFactura(int codigoDetalleFactura) {
        this.codigoDetalleFactura = codigoDetalleFactura;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public int getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(int numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }
    
    @Override
    public String toString(){
        return "| " + getNumeroFactura() + " - " + getProductoId();
    }

}



