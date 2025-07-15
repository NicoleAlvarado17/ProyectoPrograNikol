
package fidecompro.ui;

import fidecompro.Usuario;

import javax.swing.*;

public class LoginFrame extends JFrame {
    private JTextField tfUsuario = new JTextField(15);
    private JPasswordField pfContra = new JPasswordField(15);

    public LoginFrame() {   //usuario admin  contraseña 123
        super("Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);  
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(new JLabel("Usuario:")); add(tfUsuario);
        add(new JLabel("Contraseña:")); add(pfContra);
        JButton btnLogin = new JButton("Ingresar");
        add(btnLogin);
        btnLogin.addActionListener(e -> {
            String u = tfUsuario.getText(), c = new String(pfContra.getPassword());
            if (new Usuario(u,c).validar(u,c)) {
                new MainMenuFrame().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,"Credenciales inválidas");
            }
        });
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}

