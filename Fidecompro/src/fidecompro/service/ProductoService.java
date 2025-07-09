
package fidecompro.service;

import fidecompro.exception.EntidadDuplicadaException;
import fidecompro.Producto;
import fidecompro.persistence.ProductoDAO;
import java.util.List;

public class ProductoService implements ServicioCRUD<Producto> {
    private ProductoDAO dao = new ProductoDAO();

    @Override
    public void agregar(Producto p) throws EntidadDuplicadaException {
        List<Producto> lista = dao.loadAll();
        if (lista.stream().anyMatch(x -> x.getId() == p.getId())) {
            throw new EntidadDuplicadaException("Producto ya existe: " + p.getId());
        }
        lista.add(p);
        dao.saveAll(lista);
    }

    @Override
    public List<Producto> listar() {
        return dao.loadAll();
    }
}