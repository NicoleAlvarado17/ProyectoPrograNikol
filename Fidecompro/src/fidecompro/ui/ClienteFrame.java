
package fidecompro.ui;
 
import fidecompro.Cliente;
import fidecompro.service.ClienteService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ClienteFrame extends JFrame {
    private ClienteService service = new ClienteService();
    private DefaultTableModel model = new DefaultTableModel(new Object[]{"ID","Usuario","Nombre"},0);

    public ClienteFrame() {
        super("Clientes");
        setLayout(new BorderLayout());
        JTable table = new JTable(model);
        refreshTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnAdd = new JButton("Nuevo Cliente");
        add(btnAdd, BorderLayout.SOUTH);
        btnAdd.addActionListener(e -> {
            String idS = JOptionPane.showInputDialog("ID:");
            String u   = JOptionPane.showInputDialog("Usuario:");
            String c   = JOptionPane.showInputDialog("Contraseña:");
            String n   = JOptionPane.showInputDialog("Nombre:");
            try {
                service.agregar(new Cliente(
                    Integer.parseInt(idS), u, c, n, "", ""));
                refreshTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        setSize(400,300);
        setLocationRelativeTo(null);
    }

    private void refreshTable() {
        model.setRowCount(0);
        service.listar().forEach(c ->
            model.addRow(new Object[]{c.getId(), c.getUsuario(), c.getNombre()})
        );
    }
}