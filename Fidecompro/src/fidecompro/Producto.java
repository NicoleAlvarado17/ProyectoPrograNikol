package fidecompro;

import java.io.Serializable;

public class Producto implements Serializable{
   private static final long serialVersionUID = 1L;
   
    private int id;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(int id, String nombre, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void actualizarStock(int cantidad) throws StockInsuficienteException {
        if (cantidad > stock) {
            throw new StockInsuficienteException(
                    "stock insuficiente para" + nombre);
        }
        stock -= cantidad;
    }

    private static class StockInsuficienteException extends Exception {

        public StockInsuficienteException() {
            super();
        }

        public StockInsuficienteException(String mensaje) {
            super(mensaje);
        }
    }

    @Override
    public String toString() {
        return id + "-" + nombre+ ", precio=" + precio + '}';
    }

    
    
    
}
