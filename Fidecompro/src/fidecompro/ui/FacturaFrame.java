
package fidecompro.ui;

import fidecompro.*;
import fidecompro.service.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FacturaFrame extends JFrame {
    private ClienteService cServ = new ClienteService();
    private ProductoService pServ = new ProductoService();
    private FacturaService fServ = new FacturaService();
    private JButton btnAgregar = new JButton ("Agregar");
    private JButton btnGenerar = new JButton ("Generar");

    private JComboBox<Cliente> cbClientes;
    private JComboBox<Producto> cbProductos;
    private JTextField tfCantidad = new JTextField(5);
    private DefaultTableModel model = new DefaultTableModel(
        new Object[]{"Producto","Cant.","Subtotal"},0);

    public FacturaFrame() {
        super("Facturar");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // norte: seleccionar cliente
        cbClientes = new JComboBox<>(cServ.listar().toArray(new Cliente[0]));
        JPanel pNorte = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pNorte.add(new JLabel("cliente"));
        pNorte.add(cbClientes);
        add(pNorte, BorderLayout.NORTH);

        // centro: tabla detalles
        JTable tabla = new JTable(model);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // sur: añadir prodcutos 
        cbProductos = new JComboBox<>(pServ.listar().toArray(new Producto[0]));
        JButton btnAdd = new JButton("Agregar");
        JButton btnGen = new JButton("Generar Factura");
         JPanel pSur = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pSur.add(new JLabel("Producto:"));
        pSur.add(cbProductos);
        pSur.add(new JLabel("Cantidad:"));
        pSur.add(tfCantidad);
        pSur.add(btnAgregar);
        pSur.add(btnGenerar);
        add(pSur, BorderLayout.SOUTH);

      // AL PULSAR AGREGAR
      
        btnAgregar.addActionListener(e -> {
            Producto prod = (Producto) cbProductos.getSelectedItem();
            int qty;
            try {
                qty = Integer.parseInt(tfCantidad.getText());
                if (qty <= 0) throw new NumberFormatException();
                prod.actualizarStock(qty);          // lanza excepción si falta stock
                double sub = prod.getPrecio() * qty;
                model.addRow(new Object[]{ prod.toString(), qty, sub });
                tfCantidad.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                  "Cantidad inválida", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                  ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);}
        });
        // Al pulsar “Generar Factura”:
        
        btnGenerar.addActionListener(e -> {
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,
                  "Agrega al menos un producto", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Cliente cli = (Cliente) cbClientes.getSelectedItem();
            int nuevoID = fServ.nextFacturaId();
            Factura f = new Factura(nuevoID, cli
            );
            // Recorrer filas de la tabla para armar detalles
            for (int i = 0; i < model.getRowCount(); i++) {
                // El combo muestra toString(), pero necesitamos buscar el objeto
                String textoProd = model.getValueAt(i, 0).toString();
                int idProd = Integer.parseInt(textoProd.split("-")[0].trim());
                int cantidad   = (int) model.getValueAt(i, 1);

                // Busca el producto por su ID extraído del texto “ID – Nombre”
                Producto prod = pServ.listar().stream()
                    .filter(p -> p.getId() == idProd)
                    .findFirst()
                    .orElseThrow();
                  new RuntimeException("producto no encontrado: "+ idProd);  
                f.agregarDetalle(new Detalle(prod, cantidad));
            }

            try {
                fServ.generarFactura(f);
                JOptionPane.showMessageDialog(this,
                  "Factura #" + f.getId() +
                  " generada. Total: $" + f.calcularTotal());
                
                
                //factura en txt

               int id = f.getId();
        String fileName = "Factura_" + id + ".txt";

        try (FileWriter fw = new FileWriter(fileName);
             PrintWriter pw = new PrintWriter(fw)) {

            // Encabezado
            pw.println("FIDECOMPRO");
            pw.println("Factura #" + id);
            pw.println("Fecha: " + f.getFecha());
            pw.println("Cliente: " + f.getCliente().getNombre());
            pw.println("======================================");

            // Detalles
            pw.printf("%-5s %-20s %5s %10s%n",
                "Cant", "Producto", "Precio", "Subtotal");
            for (Detalle d : f.getDetalles()) {
                pw.printf("%-5d %-20s %5.2f %10.2f%n",
                    d.getCantidad(),
                    d.getProducto().getNombre(),
                    d.getProducto().getPrecio(),
                    d.getSubtotal());
            }
            pw.println("======================================");
            pw.printf("TOTAL: %.2f%n", f.calcularTotal());

            pw.flush();
            JOptionPane.showMessageDialog(this,
                "Factura exportada a:\n" + fileName,
                "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException io) {
            JOptionPane.showMessageDialog(this,
                "No se pudo generar el TXT:\n" + io.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        } 
                
                
            
                model.setRowCount(0);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                  ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        pack();
        setLocationRelativeTo(null);
    }
}

