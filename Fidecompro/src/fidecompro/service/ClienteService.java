
package fidecompro.service;

import fidecompro.exception.EntidadDuplicadaException;
import fidecompro.Cliente;
import fidecompro.persistence.ClienteDAO;
import java.util.List;

public class ClienteService implements ServicioCRUD<Cliente> {
    private ClienteDAO dao = new ClienteDAO();

    @Override
    public void agregar(Cliente c) throws EntidadDuplicadaException {
        List<Cliente> lista = dao.loadAll();
        if (lista.stream().anyMatch(x -> x.getId() == c.getId())) {
            throw new EntidadDuplicadaException("Cliente ya existe: " + c.getId());
        }
        lista.add(c);
        dao.saveAll(lista);
    }

    @Override
    public List<Cliente> listar() {
        return dao.loadAll();
    }
}