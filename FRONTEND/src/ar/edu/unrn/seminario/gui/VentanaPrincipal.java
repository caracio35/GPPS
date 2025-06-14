package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.UsuarioSimplificadoDTO;

public class VentanaPrincipal extends JFrame {
    private JPanel contentPane;
    private IApi api;
    private UsuarioSimplificadoDTO usuario;

    public VentanaPrincipal(UsuarioSimplificadoDTO usuario, IApi api) {
        this.api = api;
        this.usuario = usuario;
        inicializarVentana();
        configurarMenuSegunRol();
    }

    private void inicializarVentana() {
        setTitle("Sistema GPPS - " + usuario.getRol());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        // Añadir un mensaje de bienvenida
        JLabel welcomeLabel = new JLabel("Bienvenido/a - " + usuario.getNombre(), SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        contentPane.add(welcomeLabel, BorderLayout.CENTER);
    }

    private void configurarMenuSegunRol() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Menú Archivo (común para todos)
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem menuSalir = new JMenuItem("Salir");
        menuSalir.addActionListener(e -> System.exit(0));
        menuArchivo.add(menuSalir);
        menuBar.add(menuArchivo);

        // Configurar menús específicos según el rol
        switch(usuario.getRol()) {
            case "Director de Carrera":
                agregarMenuDirector(menuBar);
                break;
            case "Tutor":
                agregarMenuTutor(menuBar);
                break;
            case "Institución":
                agregarMenuInstitucion(menuBar);
                break;
            case "Alumno":
                agregarMenuUsuarioRegular(menuBar);
                break;
        }

        // Menú de propuestas (común para todos)
        JMenu propuestasMenu = new JMenu("Propuestas");
        menuBar.add(propuestasMenu);

        JMenuItem verPropuestas = new JMenuItem("Ver propuestas");
        verPropuestas.addActionListener(e -> {
            // Aquí deberías añadir el código para mostrar las propuestas
            // Por ejemplo:
            // ListadoPropuestas listado = new ListadoPropuestas(api);
            // listado.setLocationRelativeTo(null);
            // listado.setVisible(true);
        });
        propuestasMenu.add(verPropuestas);

        JMenuItem cargarPropuestas = new JMenuItem("Cargar propuestas");
        cargarPropuestas.addActionListener(e -> {
            CargarPropuesta cargar = new CargarPropuesta(this, usuario);
            cargar.setVisible(true);
        });
        propuestasMenu.add(cargarPropuestas);
    }

    private void agregarMenuDirector(JMenuBar menuBar) {
        JMenu menuGestion = new JMenu("Gestión");

        JMenuItem gestionUsuarios = new JMenuItem("Gestionar Usuarios");
        gestionUsuarios.addActionListener(e -> {
            ListadoUsuario listado = new ListadoUsuario(api);
            listado.setLocationRelativeTo(null);
            listado.setVisible(true);
        });

        menuGestion.add(gestionUsuarios);
        menuGestion.add(new JMenuItem("Gestionar Tutores"));
        menuGestion.add(new JMenuItem("Gestionar Instituciones"));
        menuGestion.add(new JMenuItem("Ver Estadísticas"));
        menuBar.add(menuGestion);
    }

    private void agregarMenuTutor(JMenuBar menuBar) {
        // ... existing code ...
    }

    private void agregarMenuInstitucion(JMenuBar menuBar) {
        // ... existing code ...
    }

    private void agregarMenuUsuarioRegular(JMenuBar menuBar) {
        // ... existing code ...
    }
}