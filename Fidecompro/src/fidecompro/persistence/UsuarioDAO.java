package fidecompro.persistence;

import fidecompro.Usuario;
import fidecompro.exception.EntidadDuplicadaException;
import java.util.List;

public class UsuarioDAO extends GenericDAO<Usuario> {
    private static final String ARCHIVO = "usuarios.dat";

    public UsuarioDAO() {
        super(ARCHIVO);
    }

    //Crea un nuevo usuario si no existe uno con el mismo username.
     public void crear(Usuario u) throws EntidadDuplicadaException{
        List<Usuario> lista = cargar();
        boolean dup = lista.stream()
                           .anyMatch(x -> x.getUsuario().equals(u.getUsuario()));
        if (dup) {
            throw new EntidadDuplicadaException("Usuario duplicado: " + u.getUsuario());
        }
        lista.add(u);
        guardar(lista);
    }

    //Elimina el usuario que coincida en username.
     
    public void eliminar(Usuario u) {
        List<Usuario> lista = cargar();
        lista.removeIf(x -> x.getUsuario().equals(u.getUsuario()));
        guardar(lista);
    }

    //Actualiza el usuario existente.
     
    
    public void update(Usuario u) {
        List<Usuario> lista = cargar();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getUsuario().equals(u.getUsuario())) {
                lista.set(i, u);
                break;
            }
        }
        guardar(lista);
    }

    // Estos dos métodos delegan directamente al padre y son opcionales:

    public List<Usuario> cargar() {
        return super.cargar();
    }

    public void guardar(List<Usuario> lista) {
        super.guardar(lista);
    }
}

    