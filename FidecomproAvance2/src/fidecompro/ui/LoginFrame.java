package fidecompro.ui;

import fidecompro.Usuario;
import javax.swing.*;

public class LoginFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField tfUsuario = new JTextField(15);
    private JPasswordField pfContra = new JPasswordField(15);
    private JButton btnLogin = new JButton("Ingresar");

    // Para HU‑001: contador de intentos y bloqueo
    private int intentosFallidos = 0;
    private long bloqueoHasta = 0L;  // timestamp en millis

    public LoginFrame() {
        super("Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(Box.createVerticalStrut(10));

        add(new JLabel("Usuario:"));
        add(tfUsuario);
        add(Box.createVerticalStrut(5));

        add(new JLabel("Contraseña:"));
        add(pfContra);
        add(Box.createVerticalStrut(10));

        add(btnLogin);
        add(Box.createVerticalStrut(10));

        btnLogin.addActionListener(e -> realizarLogin());

        pack();
        setLocationRelativeTo(null);
    }

    private void realizarLogin() {
        long ahora = System.currentTimeMillis();

        // 1) Verificar bloqueo
        if (ahora < bloqueoHasta) {
            long segRestantes = (bloqueoHasta - ahora) / 1000;
            JOptionPane.showMessageDialog(this,
                    "Cuenta bloqueada. Intente de nuevo en " + segRestantes + " segundos.",
                    "Bloqueo temporal",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2) Validar campos vacíos (HU‑001)
        String u = tfUsuario.getText().trim();
        String c = new String(pfContra.getPassword()).trim();
        if (u.isEmpty() || c.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe ingresar usuario y contraseña.",
                    "Error de validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 3) Intentar autenticar
        boolean esValido = new Usuario(u, c).validar(u, c);
        if (esValido) {
            // Login exitoso: resetear contador y abrir menú principal
            intentosFallidos = 0;
            new MainMenuFrame().setVisible(true);
            dispose();
        } else {
            // Credenciales inválidas: aumentar contador
            intentosFallidos++;
            if (intentosFallidos >= 3) {
                bloqueoHasta = ahora + (5 * 60_000);  // 5 minutos
                intentosFallidos = 0;
                JOptionPane.showMessageDialog(this,
                        "Ha excedido 3 intentos. Bloqueado por 5 minutos.",
                        "Bloqueo",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Credenciales inválidas. Intento " + intentosFallidos + " de 3.",
                        "Error de autenticación",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
