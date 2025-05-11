package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.UsuarioSimplificadoDTO;

public class LoginScreen extends JFrame {
    private JTextField nombreField;
    private JTextField apellidoField;
    private JTextField dniField;
    private JTextField correoField;
    private JTextField nombreUsuarioField; // Nuevo campo para nombre de usuario
    private JComboBox<String> roleComboBox;
    private JButton aceptarButton;
    private JButton cancelarButton;
    private IApi api;

    // Colores para el diseño moderno
    private static final Color COLOR_PRIMARIO = new Color(41, 128, 185);
    private static final Color COLOR_SECUNDARIO = new Color(52, 152, 219);
    private static final Color COLOR_FONDO = new Color(236, 240, 241);
    private static final Color COLOR_TEXTO = new Color(44, 62, 80);
    private static final Color COLOR_BOTON = new Color(52, 152, 219);
    private static final Color COLOR_BOTON_HOVER = new Color(41, 128, 185);

    public LoginScreen(IApi api) {
        this.api = api;
        // Configuración básica de la ventana
        setTitle("Registro de Usuario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 550);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO);

        // Intentar establecer un look and feel más moderno
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(COLOR_FONDO);
        mainPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        // Panel de título
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(COLOR_FONDO);
        JLabel titleLabel = new JLabel("Registro de Usuario");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(COLOR_PRIMARIO);
        titlePanel.add(titleLabel);

        // Panel de formulario con GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(COLOR_FONDO);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // Campos de entrada con estilo mejorado
        nombreUsuarioField = crearCampoTextoModerno();
        nombreField = crearCampoTextoModerno();
        apellidoField = crearCampoTextoModerno();
        dniField = crearCampoTextoModerno();
        correoField = crearCampoTextoModerno();

        // ComboBox de roles con estilo mejorado
        String[] roles = { "Alumno", "Tutor", "Institución", "Director de Carrera" };
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setBackground(Color.WHITE);
        roleComboBox.setForeground(COLOR_TEXTO);
        roleComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        ((JComponent) roleComboBox.getRenderer()).setBorder(new EmptyBorder(5, 10, 5, 10));

        // Agregar campos al formulario
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // agregarCampo(mainPanel, "Nombre de Usuario:", nombreUsuarioField = new
        // JTextField(20), 5, gbc);
        agregarCampo(formPanel, "Nombre de Usuario:", nombreUsuarioField, 0, gbc);
        agregarCampo(formPanel, "Nombre:", nombreField, 1, gbc);
        agregarCampo(formPanel, "Apellido:", apellidoField, 2, gbc);
        agregarCampo(formPanel, "DNI:", dniField, 3, gbc);
        agregarCampo(formPanel, "Correo:", correoField, 4, gbc);
        agregarCampo(formPanel, "Rol:", roleComboBox, 5, gbc);

        // Panel para botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(COLOR_FONDO);

        aceptarButton = crearBotonModerno("Aceptar");
        cancelarButton = crearBotonModerno("Cancelar");
        cancelarButton.setBackground(new Color(231, 76, 60));
        cancelarButton.setForeground(Color.WHITE);

        buttonPanel.add(aceptarButton);
        buttonPanel.add(cancelarButton);

        // Agregar acciones a los botones
        aceptarButton.addActionListener(e -> crearUsuarioYAbrirVentana());
        cancelarButton.addActionListener(e -> System.exit(0));

        // Agregar los paneles al panel principal
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Agregar el panel principal a la ventana
        add(mainPanel);
    }

    private JTextField crearCampoTextoModerno() {
        JTextField campo = new JTextField(20);
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
        campo.setForeground(COLOR_TEXTO);
        campo.setBorder(new CompoundBorder(
                new LineBorder(COLOR_SECUNDARIO, 1, true),
                new EmptyBorder(8, 10, 8, 10)));
        campo.setPreferredSize(new Dimension(campo.getPreferredSize().width, 35));
        return campo;
    }

    private JButton crearBotonModerno(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBackground(COLOR_BOTON);
        boton.setForeground(Color.WHITE);
        boton.setBorder(new EmptyBorder(10, 20, 10, 20));
        boton.setFocusPainted(false);
        boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Efecto hover
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(COLOR_BOTON_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(COLOR_BOTON);
            }
        });

        return boton;
    }

    private void agregarCampo(JPanel panel, String etiqueta, JComponent campo, int y, GridBagConstraints gbc) {
        JLabel label = new JLabel(etiqueta);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(COLOR_TEXTO);

        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(campo, gbc);
    }

    private void crearUsuarioYAbrirVentana() {
        // Validación básica
        if (nombreField.getText().isEmpty() || apellidoField.getText().isEmpty() ||
                dniField.getText().isEmpty() || correoField.getText().isEmpty() ||
                nombreUsuarioField.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Por favor, complete todos los campos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Convertir el DNI a entero
        int dni;
        try {
            dni = Integer.parseInt(dniField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "El DNI debe ser un número",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear DTO con el nuevo constructor
        UsuarioSimplificadoDTO usuario = new UsuarioSimplificadoDTO(
                nombreField.getText(),
                apellidoField.getText(),
                correoField.getText(),
                (String) roleComboBox.getSelectedItem(),
                dni,
                nombreUsuarioField.getText());

        // Abrir ventana principal
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(usuario, api);
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
