
package org.josesanchez.beans;

public class DetalleCompra {
   private int codigoDetalleCompra;
   private double costoUnitario;
   private int cantidad;
   private int productosCodigoProducto;
   private int comprasNumeroDocumento;

    public DetalleCompra() {
    }

    public DetalleCompra(int codigoDetalleCompra, double costoUnitario, int cantidad, int productosCodigoProducto, int comprasNumeroDocumento) {
        this.codigoDetalleCompra = codigoDetalleCompra;
        this.costoUnitario = costoUnitario;
        this.cantidad = cantidad;
        this.productosCodigoProducto = productosCodigoProducto;
        this.comprasNumeroDocumento = comprasNumeroDocumento;
    }

    public int getCodigoDetalleCompra() {
        return codigoDetalleCompra;
    }

    public void setCodigoDetalleCompra(int codigoDetalleCompra) {
        this.codigoDetalleCompra = codigoDetalleCompra;
    }

    public double getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(double costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getProductosCodigoProducto() {
        return productosCodigoProducto;
    }

    public void setProductosCodigoProducto(int productosCodigoProducto) {
        this.productosCodigoProducto = productosCodigoProducto;
    }

    public int getComprasNumeroDocumento() {
        return comprasNumeroDocumento;
    }

    public void setComprasNumeroDocumento(int comprasNumeroDocumento) {
        this.comprasNumeroDocumento = comprasNumeroDocumento;
    }

}
