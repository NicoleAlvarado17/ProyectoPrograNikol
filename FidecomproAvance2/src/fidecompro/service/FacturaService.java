package fidecompro.service;

import fidecompro.Factura;
import fidecompro.exception.EntidadDuplicadaException;
import fidecompro.persistence.FacturaDAO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FacturaService implements ServicioCRUD<Factura> {

    private static final FacturaService instancia = new FacturaService();
    private final FacturaDAO dao = new FacturaDAO();

    public FacturaService() {
    }

    public static FacturaService getInstance() {
        return instancia;
    }

    public void agregar(Factura f) throws EntidadDuplicadaException {
        List<Factura> lista = dao.cargar();
        if (lista.stream().anyMatch(x -> x.getId() == f.getId())) {
            throw new EntidadDuplicadaException("Factura ya existe: " + f.getId());
        }
        lista.add(f);
        dao.guardar(lista);
    }

    public List<Factura> listar() {
        return dao.cargar();
    }

    public void actualizar(Factura f) {
        List<Factura> lista = dao.cargar();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == f.getId()) {
                lista.set(i, f);
                break;
            }
        }
        dao.guardar(lista);
    }

    public void eliminar(Factura f) {
        List<Factura> lista = dao.cargar();
        lista.removeIf(x -> x.getId() == f.getId());
        dao.guardar(lista);
    }
    
    public void generarFactura(Factura f)throws EntidadDuplicadaException{
    agregar(f);}

    /**
     * HU‑005 helper: devuelve el siguiente ID para una nueva factura.
     */
    public int nextFacturaId() {
        return dao.cargar()
                .stream()
                .mapToInt(Factura::getId)
                .max()
                .orElse(0)
                + 1;
    }
}
