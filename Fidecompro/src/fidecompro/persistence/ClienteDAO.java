package fidecompro.persistence;

import fidecompro.Cliente;

public class ClienteDAO extends GenericDAO<Cliente> {

    public ClienteDAO() {
        super("clientes.dat");
    }

}
