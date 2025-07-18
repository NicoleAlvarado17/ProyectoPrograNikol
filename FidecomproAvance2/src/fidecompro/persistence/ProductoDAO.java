package fidecompro.persistence;

import fidecompro.Producto;

public class ProductoDAO extends GenericDAO<Producto> {

    public ProductoDAO() {
        super("productos.dat");
    }
}
