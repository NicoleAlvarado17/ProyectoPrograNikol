
package fidecompro.ui;

import fidecompro.Producto;
import fidecompro.service.ProductoService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductoFrame extends JFrame {
    private ProductoService service = new ProductoService();
    private DefaultTableModel model = new DefaultTableModel(
        new Object[]{"ID","Nombre","Precio","Stock"},0);

    public ProductoFrame() {
        super("Productos");
        setLayout(new BorderLayout());
        JTable table = new JTable(model);
        refreshTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnAdd = new JButton("Nuevo Producto");
        add(btnAdd, BorderLayout.SOUTH);
        btnAdd.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog("ID:"));
                String nombre = JOptionPane.showInputDialog("Nombre:");
                double precio = Double.parseDouble(JOptionPane.showInputDialog("Precio:"));
                int stock = Integer.parseInt(JOptionPane.showInputDialog("Stock:"));
                service.agregar(new Producto(id, nombre, precio, stock));
                refreshTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        setSize(500,300);
        setLocationRelativeTo(null);
    }

    private void refreshTable() {
        model.setRowCount(0);
        service.listar().forEach(p ->
            model.addRow(new Object[]{
                p.getId(), p.getNombre(), p.getPrecio(), p.getStock()
            })
        );
    }
}
