package ar.edu.unrn.seminario.gui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.AlumnoDTO;
import ar.edu.unrn.seminario.dto.EntidadDTO;
import ar.edu.unrn.seminario.dto.PropuestaDTO;

public class VentanaCrearConvenio extends JFrame {

	private JTextField tfEntidad, tfCuitEntidad;
	private JTextField tfEstudiante, tfDniEst;
	private JTextField tfTutor, tfDniTutor;
	private JDatePickerImpl datePickerInicio;
	private JTextField tfFechaFin;

	private IApi api;
	private PropuestaDTO propuestaSeleccionada;
	private EntidadDTO entidadDto;
	private AlumnoDTO alumnoDto;

	public VentanaCrearConvenio(IApi api, String tituloProyecto) {
		setTitle("Generar Acta Acuerdo");
		setSize(600, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new GridBagLayout());

		this.api = api;

		try {
			propuestaSeleccionada = api.obtenerPropuestaPorTitulo(tituloProyecto);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
					"Error al obtener la propuesta: " + e.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}

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
		add(tfEntidad, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		add(new JLabel("CUIT:"), gbc);

		tfCuitEntidad = new JTextField();
		tfCuitEntidad.setEditable(false);
		gbc.gridx = 1;
		add(tfCuitEntidad, gbc);

		// ===== ESTUDIANTE =====
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(new JLabel("Estudiante:"), gbc);

		tfEstudiante = new JTextField();
		tfEstudiante.setEditable(false);
		gbc.gridx = 1;
		add(tfEstudiante, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		add(new JLabel("DNI Estudiante:"), gbc);

		tfDniEst = new JTextField();
		tfDniEst.setEditable(false);
		gbc.gridx = 1;
		add(tfDniEst, gbc);

		// ===== TUTOR ACADÉMICO =====
		gbc.gridx = 0;
		gbc.gridy = 4;
		add(new JLabel("Tutor Académico:"), gbc);

		tfTutor = new JTextField();
		tfTutor.setEditable(false);
		gbc.gridx = 1;
		add(tfTutor, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		add(new JLabel("DNI Tutor:"), gbc);

		tfDniTutor = new JTextField();
		tfDniTutor.setEditable(false);
		gbc.gridx = 1;
		add(tfDniTutor, gbc);

		// ===== FECHA DE INICIO =====
		gbc.gridx = 0;
		gbc.gridy = 6;
		add(new JLabel("Fecha de Inicio:"), gbc);

		UtilDateModel model = new UtilDateModel();
		model.setValue(new Date()); // por defecto hoy
		Properties p = new Properties();
		p.put("text.today", "Hoy");
		p.put("text.month", "Mes");
		p.put("text.year", "Año");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePickerInicio = new JDatePickerImpl(datePanel, new DateComponentFormatter());

		// Bloquear fechas pasadas
		datePanel.addActionListener(e -> {
			Date selectedDate = (Date) datePickerInicio.getModel().getValue();
			if (selectedDate != null && selectedDate.before(new Date())) {
				JOptionPane.showMessageDialog(this, "¡No se pueden seleccionar fechas pasadas!", "Error",
						JOptionPane.ERROR_MESSAGE);
				model.setValue(new Date());
			}
		});

		gbc.gridx = 1;
		add(datePickerInicio, gbc);

		// ===== FECHA DE FIN =====
		gbc.gridx = 0;
		gbc.gridy = 7;
		add(new JLabel("Fecha de Fin:"), gbc);

		tfFechaFin = new JTextField();
		tfFechaFin.setEditable(false);
		gbc.gridx = 1;
		add(tfFechaFin, gbc);

		// ===== BOTONES =====
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton btnGenerar = new JButton("Generar Acta");
		btnGenerar.addActionListener(e -> generarConvenio());
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(e -> dispose());

		panelBotones.add(btnGenerar);
		panelBotones.add(btnCancelar);

		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.gridwidth = 2;
		add(panelBotones, gbc);

		cargarDatos();
		setVisible(true);
	}

	private void cargarDatos() {
		if (entidadDto != null) {
			tfEntidad.setText(entidadDto.getNombre());
			tfCuitEntidad.setText(entidadDto.getCuit());
		}
		if (alumnoDto != null) {
			tfEstudiante.setText(alumnoDto.getNombre() + " " + alumnoDto.getApellido());
			tfDniEst.setText(alumnoDto.getDni());
		}
		if (turorDto != null) {
			tfTutor.setText(turorDto.getNombre() + " " + turorDto.getApellido());
			tfDniTutor.setText(turorDto.getDni());
		}
	}

	private void generarConvenio() {
		try {
			Date fechaInicioDate = (Date) datePickerInicio.getModel().getValue();
			LocalDate fechaInicio = fechaInicioDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

			if (fechaInicio.isBefore(LocalDate.now())) {
				JOptionPane.showMessageDialog(this, "La fecha de inicio no puede ser anterior a hoy.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// TODO: Calcular fecha de fin real con las horas de actividades (placeholder)
			String fechaFin = "2025-12-31";
			tfFechaFin.setText(fechaFin);

			String fechaInicioStr = parsearLocalDateAString(fechaInicio);
			api.crearConvenio(fechaInicioStr, fechaFin, propuestaSeleccionada.getTitulo(),
					propuestaSeleccionada.getIdAlumno(), propuestaSeleccionada.getIdProfesoPrincipal());

			JOptionPane.showMessageDialog(this, "¡Acta generada con éxito!");
			dispose();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al generar el acta: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public String parsearLocalDateAString(LocalDate fecha) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return fecha.format(formatter);
	}
}