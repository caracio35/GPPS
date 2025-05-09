package ar.edu.unrn.seminario.gui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.dto.UsuarioSimplificadoDTO;


public class LoginScreen extends JFrame {
    private JTextField nombreField;
    private JTextField apellidoField;
    private JTextField dniField;
    private JTextField correoField;
    private JComboBox<String> roleComboBox;
    private JButton aceptarButton;
    private JButton cancelarButton;
    private IApi api;

    public LoginScreen(IApi api) {
        this.api = api;
        // Configuración básica de la ventana
        setTitle("Registro de Usuario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        // Panel principal con GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos de entrada
        agregarCampo(mainPanel, "Nombre:", nombreField = new JTextField(20), 0);
        agregarCampo(mainPanel, "Apellido:", apellidoField = new JTextField(20), 1);
        agregarCampo(mainPanel, "DNI:", dniField = new JTextField(20), 2);
        agregarCampo(mainPanel, "Correo:", correoField = new JTextField(20), 3);

        // ComboBox de roles
        String[] roles = {"Alumno", "Tutor", "Institución", "Director de Carrera"};
        roleComboBox = new JComboBox<>(roles);
        agregarCampo(mainPanel, "Rol:", roleComboBox, 4);

        // Panel para botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        aceptarButton = new JButton("Aceptar");
        cancelarButton = new JButton("Cancelar");

        buttonPanel.add(aceptarButton);
        buttonPanel.add(cancelarButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);

        // Agregar acciones a los botones
        aceptarButton.addActionListener(e -> crearUsuarioYAbrirVentana());
        cancelarButton.addActionListener(e -> System.exit(0));

        // Agregar el panel principal a la ventana
        add(mainPanel);
    }

    private void agregarCampo(JPanel panel, String etiqueta, JComponent campo, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel(etiqueta), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(campo, gbc);
    }

    private void crearUsuarioYAbrirVentana() {
        // Validación básica
        if (nombreField.getText().isEmpty() || apellidoField.getText().isEmpty() || 
            dniField.getText().isEmpty() || correoField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor, complete todos los campos",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear DTO
        UsuarioSimplificadoDTO usuario = new UsuarioSimplificadoDTO(
            nombreField.getText(),
            apellidoField.getText(),
            dniField.getText(),
            correoField.getText(),
            (String) roleComboBox.getSelectedItem()
        );

        // Abrir ventana principal
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(usuario,api);
            ventanaPrincipal.setVisible(true);
            this.dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginScreen loginScreen = new LoginScreen(null); // Puedes pasar la API real aquí
            loginScreen.setVisible(true);
        });
    }
}
   