
package fidecompro.ui;

import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {
    public MainMenuFrame() {
        super("Menú Principal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3,1));
        JButton btnClientes = new JButton("Gestión de Clientes");
        JButton btnProductos = new JButton("Gestión de Productos");
        JButton btnFacturas  = new JButton("Gestión de Facturas");
        add(btnClientes); add(btnProductos); add(btnFacturas);

        btnClientes.addActionListener(e -> new ClienteFrame().setVisible(true));
        btnProductos.addActionListener(e -> new ProductoFrame().setVisible(true));
        btnFacturas.addActionListener(e -> new FacturaFrame().setVisible(true));

        pack();
        setLocationRelativeTo(null);
    }
}