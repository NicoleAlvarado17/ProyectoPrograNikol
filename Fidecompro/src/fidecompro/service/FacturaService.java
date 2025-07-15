package fidecompro.service;

import fidecompro.Factura;
import fidecompro.exception.EntidadDuplicadaException;
import fidecompro.persistence.FacturaDAO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FacturaService {

    private FacturaDAO dao = new FacturaDAO();

    // Método existente para generar y guardar la factura
    public void generarFactura(Factura f) throws EntidadDuplicadaException {
        List<Factura> lista = dao.loadAll();
        if (lista.stream().anyMatch(x -> x.getId() == f.getId())) {
            throw new EntidadDuplicadaException("Factura ya existe: " + f.getId());
        }
        lista.add(f);
        dao.saveAll(lista);
    
}
