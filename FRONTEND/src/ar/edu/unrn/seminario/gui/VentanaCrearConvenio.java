package ar.edu.unrn.seminario.gui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.AlumnoDTO;
import ar.edu.unrn.seminario.dto.EntidadDTO;
import ar.edu.unrn.seminario.dto.PropuestaDTO;
import ar.edu.unrn.seminario.dto.TutorProfesorDTO;

public class VentanaCrearConvenio extends JFrame {
	
	 private JTextField tfEntidad, tfCuitEntidad;
	    private JTextField tfEstudiante, tfDniEst;
	    private JTextField tfTutor, tfDniTutor;

	    private IApi api;
	    private PropuestaDTO propuestaSeleccionada;
	    private EntidadDTO entidadDto;
	    private AlumnoDTO alumnoDto;
	    private TutorProfesorDTO turorDto;

	    public VentanaCrearConvenio(IApi api, String tituloProyecto) {
	        setTitle("Generar Acta Acuerdo");
	        setSize(600, 400);
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setLocationRelativeTo(null); // Centrar ventana
	        setLayout(new GridBagLayout());

	        propuestaSeleccionada = api.obtenerPropuestaPorTitulo(tituloProyecto);
	        entidadDto = api.obtenerEntidad(propuestaSeleccionada.getIdEntidad());
	        alumnoDto = api.obtenerAlumno(propuestaSeleccionada.getIdAlumno());
	        turorDto = api.obtenerProfeso(propuestaSeleccionada.getIdProfesoPrincipal());

	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.insets = new Insets(10, 10, 10, 10);
	        gbc.fill = GridBagConstraints.HORIZONTAL;

	        // ===== ENTIDAD =====
	        gbc.gridx = 0;
	        gbc.gridy = 0;
	        add(new JLabel("Entidad:"), gbc);

	        tfEntidad = new JTextField();
	        tfEntidad.setEditable(false);
	        gbc.gridx = 1;
	        gbc.gridy = 0;
	        add(tfEntidad, gbc);

	        gbc.gridx = 0;
	        gbc.gridy = 1;
	        add(new JLabel("CUIT:"), gbc);

	        tfCuitEntidad = new JTextField();
	        tfCuitEntidad.setEditable(false);
	        gbc.gridx = 1;
	        gbc.gridy = 1;
	        add(tfCuitEntidad, gbc);

	        // ===== ESTUDIANTE =====
	        gbc.gridx = 0;
	        gbc.gridy = 2;
	        add(new JLabel("Estudiante:"), gbc);

	        tfEstudiante = new JTextField();
	        tfEstudiante.setEditable(false);
	        gbc.gridx = 1;
	        gbc.gridy = 2;
	        add(tfEstudiante, gbc);

	        gbc.gridx = 0;
	        gbc.gridy = 3;
	        add(new JLabel("DNI Estudiante:"), gbc);

	        tfDniEst = new JTextField();
	        tfDniEst.setEditable(false);
	        gbc.gridx = 1;
	        gbc.gridy = 3;
	        add(tfDniEst, gbc);

	        // ===== TUTOR ACADÉMICO =====
	        gbc.gridx = 0;
	        gbc.gridy = 4;
	        add(new JLabel("Tutor Académico:"), gbc);

	        tfTutor = new JTextField();
	        tfTutor.setEditable(false);
	        gbc.gridx = 1;
	        gbc.gridy = 4;
	        add(tfTutor, gbc);

	        gbc.gridx = 0;
	        gbc.gridy = 5;
	        add(new JLabel("DNI Tutor:"), gbc);

	        tfDniTutor = new JTextField();
	        tfDniTutor.setEditable(false);
	        gbc.gridx = 1;
	        gbc.gridy = 5;
	        add(tfDniTutor, gbc);

	        // ===== BOTONES =====
	        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	        JButton btnGenerar = new JButton("Generar Acta");
	        btnGenerar.addActionListener(e -> {
	            generarConvenio(api);
	            dispose();
	        });
	        JButton btnCancelar = new JButton("Cancelar");
	        btnCancelar.addActionListener(e -> dispose());

	        panelBotones.add(btnGenerar);
	        panelBotones.add(btnCancelar);

	        gbc.gridx = 0;
	        gbc.gridy = 6;
	        gbc.gridwidth = 2;
	        add(panelBotones, gbc);

	        cargarDatos();
	        setVisible(true);
	    }

	    private void cargarDatos() {
	        // Cargar datos de la Entidad
	        if (entidadDto != null) {
	            tfEntidad.setText(entidadDto.getNombre());
	            tfCuitEntidad.setText(entidadDto.getCuit());
	        }

	        // Cargar datos del Estudiante
	        if (alumnoDto != null) {
	            tfEstudiante.setText(alumnoDto.getNombre() + " " + alumnoDto.getApellido());
	            tfDniEst.setText(alumnoDto.getDni());
	        }

	        // Cargar datos del Tutor Académico
	        if (turorDto != null) {
	            tfTutor.setText(turorDto.getNombre() + " " + turorDto.getApellido());
	            tfDniTutor.setText(turorDto.getDni());
	        }
	    }


    private void generarConvenio(IApi api) {
    	
    	 LocalDate horaCreado = LocalDate.now();
    	 String hora = parsearLocalDateAString(horaCreado);
        api.crearConvenio(hora, "nada", propuestaSeleccionada.getTitulo(), propuestaSeleccionada.getIdAlumno(), propuestaSeleccionada.getIdProfesoPrincipal());
        JOptionPane.showMessageDialog(this, "¡Acta generada con éxito!");
        
    }
    public String parsearLocalDateAString(LocalDate fecha) {
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return fecha.format(formatter);
    }
}
	   

