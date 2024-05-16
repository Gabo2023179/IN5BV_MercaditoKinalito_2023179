
package org.josesanchez.beans;


public class TipoDeProductos {
    private int codigoTipoDeProducto;
    private String descripcion;

    public TipoDeProductos() {
    }

    public TipoDeProductos(int codigoTipoDeProducto, String descripcion) {
        this.codigoTipoDeProducto = codigoTipoDeProducto;
        this.descripcion = descripcion;
    }

    public int getCodigoTipoDeProducto() {
        return codigoTipoDeProducto;
    }

    public void setCodigoTipoDeProducto(int codigoTipoDeProducto) {
        this.codigoTipoDeProducto = codigoTipoDeProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
