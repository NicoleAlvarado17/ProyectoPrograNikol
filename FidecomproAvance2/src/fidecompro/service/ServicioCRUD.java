package fidecompro.service;

import java.util.List;

public interface ServicioCRUD<T> {

    void agregar(T obj) throws Exception;

    List<T> listar();

}
