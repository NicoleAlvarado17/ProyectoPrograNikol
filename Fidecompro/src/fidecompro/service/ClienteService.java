package fidecompro.service;

import fidecompro.exception.EntidadDuplicadaException;
import fidecompro.Cliente;
import fidecompro.persistence.ClienteDAO;
import java.util.List;

public class ClienteService implements ServicioCRUD<Cliente> {

    private static final ClienteService instancia = new ClienteService();
    private final ClienteDAO dao = new ClienteDAO();

    public static ClienteService getInstance() {
        return instancia;
    }

    public ClienteService() {
    }

    /**
     * Crea un nuevo cliente; lanza si ya existe uno con el mismo ID o usuario
     */
    @Override
    public void agregar(Cliente c) throws EntidadDuplicadaException {
        List<Cliente> lista = dao.cargar();
        boolean dup = lista.stream()
                .anyMatch(x -> x.getId() == c.getId()
                || x.getUsuario().equalsIgnoreCase(c.getUsuario()));
        if (dup) {
            throw new EntidadDuplicadaException(
                    "Cliente duplicado (ID o usuario): " + c.getUsuario()
            );
        }
        lista.add(c);
        dao.guardar(lista);
    }

    /**
     * Devuelve todos los clientes
     */
    @Override
    public List<Cliente> listar() {
        return dao.cargar();
    }

    /**
     * Actualiza un cliente existente
     */
    public void actualizar(Cliente c) {
        List<Cliente> lista = dao.cargar();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == c.getId()) {
                lista.set(i, c);
                break;
            }
        }
        dao.guardar(lista);
    }

    /**
     * Elimina un cliente por ID
     */
    public void eliminar(Cliente c) {
        List<Cliente> lista = dao.cargar();
        lista.removeIf(x -> x.getId() == c.getId());
        dao.guardar(lista);
    }

}
