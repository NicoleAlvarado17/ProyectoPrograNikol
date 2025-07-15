
package fidecompro.service;

import fidecompro.Usuario;
import fidecompro.exception.EntidadDuplicadaException;
import fidecompro.persistence.UsuarioDAO;
import java.util.List;

public class UsuarioService {
    private static UsuarioService instancia = new UsuarioService();
    private UsuarioDAO dao = new UsuarioDAO();
    private UsuarioService() {}
    public static UsuarioService getInstance() { return instancia; }

    public void crearUsuario(String username, String password, String rol)
            throws EntidadDuplicadaException {
        Usuario u = new Usuario(username, password, rol);
        dao.crear(u);
    }

    public List<Usuario> listarUsuarios() {
        return dao.cargar();
    }

    public void eliminarUsuario(String username) {
        List<Usuario> lista = dao.cargar();
        lista.removeIf(u -> u.getUsuario().equals(username));
        dao.guardar(lista);
    }

    public void actualizarUsuario(Usuario actualizado) {
        List<Usuario> lista = dao.cargar();
        lista.replaceAll(u -> u.getUsuario().equals(actualizado.getUsuario()) ? actualizado : u);
        dao.guardar(lista);
    }
}