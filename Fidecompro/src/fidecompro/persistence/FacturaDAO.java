package fidecompro.persistence;

import fidecompro.Factura;

public class FacturaDAO extends GenericDAO<Factura> {

    public FacturaDAO() {
        super("factura.dat");
    }

}
