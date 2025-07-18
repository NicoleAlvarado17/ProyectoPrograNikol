
package fidecompro.ui;

import fidecompro.Usuario;
import fidecompro.exception.EntidadDuplicadaException;
import fidecompro.service.UsuarioService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UsuarioFrame extends JFrame {
    private DefaultTableModel modelo = new DefaultTableModel(
        new Object[]{"Username", "Rol"}, 0);
    private JTable tabla = new JTable(modelo);
    private JButton btnNuevo   = new JButton("Nuevo");
    private JButton btnEditar  = new JButton("Editar");
    private JButton btnEliminar= new JButton("Eliminar");
    private UsuarioService service = UsuarioService.getInstance();

    public UsuarioFrame() {
        super("Gestión de Usuarios");
        setLayout(new BorderLayout());
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel pnl = new JPanel();
        pnl.add(btnNuevo); pnl.add(btnEditar); pnl.add(btnEliminar);
        add(pnl, BorderLayout.SOUTH);

        btnNuevo.addActionListener(e -> crearUsuario());
        btnEditar.addActionListener(e -> editarUsuario());
        btnEliminar.addActionListener(e -> eliminarUsuario());

        cargarTabla();
        setSize(400,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        for (Usuario u : service.listarUsuarios()) {
            modelo.addRow(new Object[]{u.getUsuario(), u.getRol()});
        }
    }

    private void crearUsuario() {
        JTextField tfUser = new JTextField();
        JPasswordField pfPass = new JPasswordField();
        JTextField tfRol  = new JTextField();
        Object[] campos = {
            "Usuario:", tfUser,
            "Contraseña:", pfPass,
            "Rol:", tfRol
        };
        int r = JOptionPane.showConfirmDialog(this, campos,
            "Nuevo Usuario", JOptionPane.OK_CANCEL_OPTION);
        if (r==JOptionPane.OK_OPTION) {
            try {
                service.crearUsuario(
                    tfUser.getText().trim(),
                    new String(pfPass.getPassword()).trim(),
                    tfRol.getText().trim()
                );
                cargarTabla();
            } catch (EntidadDuplicadaException ex) {
                JOptionPane.showMessageDialog(this,
                    ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarUsuario() {
        int idx = tabla.getSelectedRow();
        if (idx<0) {
            JOptionPane.showMessageDialog(this,"Seleccione un usuario.");
            return;
        }
        String user = (String) modelo.getValueAt(idx,0);
        Usuario u = service.listarUsuarios()
                           .stream()
                           .filter(x->x.getUsuario().equals(user))
                           .findFirst().orElse(null);
        JTextField tfPass = new JTextField(u.getContraseña());
        JTextField tfRol  = new JTextField(u.getRol());
        Object[] campos = {"Contraseña:", tfPass, "Rol:", tfRol};
        int r = JOptionPane.showConfirmDialog(this, campos,
            "Editar Usuario", JOptionPane.OK_CANCEL_OPTION);
        if (r==JOptionPane.OK_OPTION) {
            u.setContraseña(tfPass.getText().trim());
            u.setRol(tfRol.getText().trim());
            service.actualizarUsuario(u);
            cargarTabla();
        }
    }

    private void eliminarUsuario() {
        int idx = tabla.getSelectedRow();
        if (idx<0) {
            JOptionPane.showMessageDialog(this,"Seleccione un usuario.");
            return;
        }
        String user = (String) modelo.getValueAt(idx,0);
        int c = JOptionPane.showConfirmDialog(this,
            "¿Eliminar usuario "+user+"?","Confirmar",JOptionPane.YES_NO_OPTION);
        if (c==JOptionPane.YES_OPTION) {
            service.eliminarUsuario(user);
            cargarTabla();
        }
    }
}