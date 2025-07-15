package fidecompro.ui;

import fidecompro.Cliente;
import fidecompro.exception.EntidadDuplicadaException;
import fidecompro.service.ClienteService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ClienteFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    // 1) Usamos el singleton en lugar de `new`
    private ClienteService service = ClienteService.getInstance();

    // Colocamos la columna "Teléfono"
    private DefaultTableModel model
            = new DefaultTableModel(new Object[]{"ID", "Usuario", "Nombre", "Teléfono"}, 0);

    public ClienteFrame() {
        super("Gestión de Clientes");
        setLayout(new BorderLayout(5, 5));

        // Tabla y scroll
        JTable table = new JTable(model);
        refreshTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Botón Nuevo Cliente
        JButton btnAdd = new JButton("Nuevo Cliente");
        add(btnAdd, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> {
            // Pedimos datos
            String idS = JOptionPane.showInputDialog(this, "ID:");
            String u = JOptionPane.showInputDialog(this, "Usuario:");
            String c = JOptionPane.showInputDialog(this, "Contraseña:");
            String n = JOptionPane.showInputDialog(this, "Nombre:");
            String tel = JOptionPane.showInputDialog(this, "Teléfono (8 dígitos):");

            // Validación teléfono (HU‑003)
            if (tel == null || !tel.matches("\\d{8}")) {
                JOptionPane.showMessageDialog(this,
                        "El teléfono debe tener exactamente 8 dígitos numéricos.",
                        "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                // Parseo ID y constructor de 5 parámetros
                int id = Integer.parseInt(idS.trim());
                service.agregar(new Cliente(id, u, c, n, tel,"."));
                refreshTable();
            } catch (NumberFormatException nf) {
                JOptionPane.showMessageDialog(this,
                        "El ID debe ser un número válido.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (EntidadDuplicadaException ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Error de duplicado", JOptionPane.ERROR_MESSAGE);
            }
        });

        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * Refresca los datos en la tabla
     */
    private void refreshTable() {
        model.setRowCount(0);
        service.listar().forEach(c
                -> model.addRow(new Object[]{
            c.getId(),
            c.getUsuario(),
            c.getNombre(),
            c.getTelefono()
        })
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClienteFrame().setVisible(true));
    }
}
