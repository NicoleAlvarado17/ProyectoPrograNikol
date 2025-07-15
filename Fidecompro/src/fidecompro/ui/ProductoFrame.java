package fidecompro.ui;

import fidecompro.Producto;
import fidecompro.service.ProductoService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductoFrame extends JFrame {

    private ProductoService service = ProductoService.getInstance();
    private DefaultTableModel model
            = new DefaultTableModel(new Object[]{"ID", "Nombre", "Precio", "Stock"}, 0);

    public ProductoFrame() {
        super("Gestión de Productos");
        setLayout(new BorderLayout());

        JTable table = new JTable(model);
        refreshTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnAdd = new JButton("Nuevo Producto");
        add(btnAdd, BorderLayout.SOUTH);
        btnAdd.addActionListener(e -> {
            String idS = JOptionPane.showInputDialog("ID:");
            String nombre = JOptionPane.showInputDialog("Nombre:");
            String precioS = JOptionPane.showInputDialog("Precio:");
            String stockS = JOptionPane.showInputDialog("Stock:");

            double precio;
            int stock;
            try {
                precio = Double.parseDouble(precioS.trim());
                stock = Integer.parseInt(stockS.trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Precio o stock no válidos.",
                        "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validación HU‑004
            if (precio <= 0) {
                JOptionPane.showMessageDialog(this,
                        "El precio debe ser mayor que cero.",
                        "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (stock < 0) {
                JOptionPane.showMessageDialog(this,
                        "El stock no puede ser negativo.",
                        "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                service.agregar(new Producto(
                        Integer.parseInt(idS), nombre, precio, stock
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
        service.listar().forEach(p
                -> model.addRow(new Object[]{
            p.getId(), p.getNombre(), p.getPrecio(), p.getStock()
        })
        );
    }
}
