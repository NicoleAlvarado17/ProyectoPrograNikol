
package fidecompro.service;

import fidecompro.exception.EntidadDuplicadaException;
import fidecompro.Factura;
import fidecompro.persistence.FacturaDAO;
import java.util.List;

public class FacturaService {
    private FacturaDAO dao = new FacturaDAO();

    public void generarFactura(Factura f) throws EntidadDuplicadaException {
        List<Factura> lista = dao.loadAll();
        if (lista.stream().anyMatch(x -> x.getId() == f.getId())) {
            throw new EntidadDuplicadaException("Factura ya existe: " + f.getId());
        }
        lista.add(f);
        dao.saveAll(lista);
    }

    public List<Factura> listarPorCliente(int clienteId) {
        return dao.loadAll().stream()
            .filter(f -> f.getCliente().getId() == clienteId)
            .toList();
    }
    
    public int nextFacturaId(){
        return dao.loadAll().stream().mapToInt(Factura::getId).max().orElse(0)+1;
    }
    
    
    
    
    
}


