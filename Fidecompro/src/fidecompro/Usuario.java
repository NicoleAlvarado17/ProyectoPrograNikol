package fidecompro;

import java.io.Serializable;

public class Usuario implements Serializable {
   private static final long serialVersionUID = 1L;
    private String usuario;
    private String contraseña;

    public Usuario(String usuario, String contraseña) {
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    public boolean validar (String u, String c) {
        return usuario.equalsIgnoreCase(u) && contraseña.equals(c);
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

}
