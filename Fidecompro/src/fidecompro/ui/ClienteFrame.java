package fidecompro.ui;

import fidecompro.Cliente;
import fidecompro.exception.EntidadDuplicadaException;
import fidecompro.service.ClienteService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ClienteFrame extends JFrame {

    private ClienteService service = ClienteService.getInstance();
    // Ahora incluimos la columna "Teléfono"
    private DefaultTableModel model
            = new DefaultTableModel(new Object[]{"ID", "Usuario", "Nombre", "Teléfono"}, 0);

    public ClienteFrame() {
        super("Gestión de Clientes");
        setLayout(new BorderLayout());

        JTable table = new JTable(model);
        refreshTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnAdd = new JButton("Nuevo Cliente");
        add(btnAdd, BorderLayout.SOUTH);
        btnAdd.addActionListener(e -> {
            // Pedimos todos los datos, incluida la validación de teléfono
            String idS = JOptionPane.showInputDialog("ID:");
            String u = JOptionPane.showInputDialog("Usuario:");
            String c = JOptionPane.showInputDialog("Contraseña:");
            String n = JOptionPane.showInputDialog("Nombre:");
            String tel = JOptionPane.showInputDialog("Teléfono (8 dígitos):");

            // Validación HU‑003: teléfono de 8 dígitos
            if (tel == null || !tel.matches("\\d{8}")) {
                JOptionPane.showMessageDialog(this,
                        "El teléfono debe tener exactamente 8 dígitos numéricos.",
                        "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                service.agregar(new Cliente(
                        Integer.parseInt(idS),
                        u, c, n,
                        tel // suponiendo que tu constructor acepta teléfono en este parámetro
                ));
                refreshTable();
            } catch (EntidadDuplicadaException ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void refreshTable() {
        model.setRowCount(0);
        service.listar().forEach(c
                -> model.addRow(new Object[]{
            c.getId(), c.getUsuario(), c.getNombre(), c.getTelefono()
        })
        );
    }
}
