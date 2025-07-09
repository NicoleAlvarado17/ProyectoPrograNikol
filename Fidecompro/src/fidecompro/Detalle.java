
package fidecompro;

import java.io.Serializable;

public class Detalle implements Serializable{
   private static final long serialVersionUID = 1L;
   
    private Producto producto;
    private int cantidad;

    public Detalle(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }
   

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public double getSubtotal(){
        return producto.getPrecio()*cantidad;
    }
    
}
