
package fidecompro.service;

import fidecompro.exception.EntidadDuplicadaException;
import fidecompro.Producto;
import fidecompro.persistence.ProductoDAO;
import java.util.List;

public class ProductoService implements ServicioCRUD<Producto> {
    private static final ProductoService instancia = new ProductoService();
    public static ProductoService getInstance() { return instancia; }
    private final ProductoDAO dao = new ProductoDAO();
    private ProductoService() { }

    @Override
    public void agregar(Producto p) throws EntidadDuplicadaException {
        List<Producto> lista = dao.cargar();
        if (lista.stream().anyMatch(x -> x.getId() == p.getId())) {
            throw new EntidadDuplicadaException("Producto ya existe: " + p.getId());
        }
        lista.add(p);
        dao.guardar(lista);
    }

    @Override
    public List<Producto> listar() {
        return dao.cargar();
    }

    public void actualizar(Producto p) {
        List<Producto> lista = dao.cargar();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == p.getId()) {
                lista.set(i, p);
                break;
            }
        }
        dao.guardar(lista);
    }

    public void eliminar(Producto p) {
        List<Producto> lista = dao.cargar();
        lista.removeIf(x -> x.getId() == p.getId());
        dao.guardar(lista);
    }
}