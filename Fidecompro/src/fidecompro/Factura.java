
package fidecompro;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Factura implements Serializable {
   private static final long serialVersionUID = 1L;
    private int id;
    private Cliente cliente;
    private LocalDate fecha;
    private List<Detalle> detalles = new ArrayList<>();

    public Factura(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.fecha = LocalDate.now();}
    
    public int getId() {
        return id;}
    public void setId(int id) {
        this.id = id;}
    public Cliente getCliente() {
        return cliente;}
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;}
    public LocalDate getFecha() {
        return fecha;}
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;}

    public List<Detalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<Detalle> detalles) {
        this.detalles = detalles;
    }
    
    public void agregarDetalle (Detalle d){
        detalles.add(d);
    }
     public double calcularTotal(){
         return detalles.stream().mapToDouble(Detalle::getSubtotal).sum();
         
     }   
}
