package ar.edu.unrn.seminario.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class VentanaCrearConvenio extends JFrame {
	 private JComboBox<String> comboProyectos;
	    private JTextField tfEntidad, tfRepresentante, tfDniRep;
	    private JTextField tfEstudiante, tfDniEst, tfCarrera;
	    private JTextField tfTutor, tfDniTutor, tfSupervisor, tfDniSupervisor;

	    private Map<String, ConvenioData> datosConvenioMock;

	    public VentanaCrearConvenio(String titulo ) {
	        setTitle("Generar Acta Acuerdo");
	        setSize(600, 500);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLayout(new GridLayout(12, 2, 5, 5));

	        inicializarDatosMock();

	        comboProyectos = new JComboBox<>(datosConvenioMock.keySet().toArray(new String[0]));
	        comboProyectos.addActionListener(this::autocompletarCampos);

	        tfEntidad = new JTextField(); tfEntidad.setEditable(false);
	        tfRepresentante = new JTextField(); tfRepresentante.setEditable(false);
	        tfDniRep = new JTextField(); tfDniRep.setEditable(false);
	        tfEstudiante = new JTextField(); tfEstudiante.setEditable(false);
	        tfDniEst = new JTextField(); tfDniEst.setEditable(false);
	        tfCarrera = new JTextField(); tfCarrera.setEditable(false);
	        tfTutor = new JTextField(); tfTutor.setEditable(false);
	        tfDniTutor = new JTextField(); tfDniTutor.setEditable(false);
	        tfSupervisor = new JTextField(); tfSupervisor.setEditable(false);
	        tfDniSupervisor = new JTextField(); tfDniSupervisor.setEditable(false);

	        add(new JLabel("Seleccionar Proyecto Aprobado:")); add(comboProyectos);
	        add(new JLabel("Entidad:")); add(tfEntidad);
	        add(new JLabel("Representante:")); add(tfRepresentante);
	        add(new JLabel("DNI Representante:")); add(tfDniRep);
	        add(new JLabel("Estudiante:")); add(tfEstudiante);
	        add(new JLabel("DNI Estudiante:")); add(tfDniEst);
	        add(new JLabel("Carrera:")); add(tfCarrera);
	        add(new JLabel("Tutor Académico:")); add(tfTutor);
	        add(new JLabel("DNI Tutor:")); add(tfDniTutor);
	        add(new JLabel("Docente Supervisor:")); add(tfSupervisor);
	        add(new JLabel("DNI Supervisor:")); add(tfDniSupervisor);

	        JButton btnGenerar = new JButton("Generar Acta");
	        btnGenerar.addActionListener(e -> JOptionPane.showMessageDialog(this, "¡Acta generada con éxito!"));

	        add(btnGenerar); add(new JLabel()); // Botón y espacio

	        setVisible(true);
	    }

	    private void autocompletarCampos(ActionEvent e) {
	        String proyectoSeleccionado = (String) comboProyectos.getSelectedItem();
	        ConvenioData data = datosConvenioMock.get(proyectoSeleccionado);

	        tfEntidad.setText(data.entidad);
	        tfRepresentante.setText(data.representante);
	        tfDniRep.setText(data.dniRep);
	        tfEstudiante.setText(data.estudiante);
	        tfDniEst.setText(data.dniEst);
	        tfCarrera.setText(data.carrera);
	        tfTutor.setText(data.tutor);
	        tfDniTutor.setText(data.dniTutor);
	        tfSupervisor.setText(data.supervisor);
	        tfDniSupervisor.setText(data.dniSupervisor);
	    }

	    private void inicializarDatosMock() {
	        datosConvenioMock = new HashMap<>();

	        datosConvenioMock.put("Proyecto - Sistema de Gestión", new ConvenioData(
	                "Fundación X", "Lic. María Pérez", "30111222",
	                "Ana López", "44111222", "Licenciatura en Sistemas",
	                "Prof. Juan Gómez", "22111000",
	                "Prof. Laura Díaz", "25111444"
	        ));

	        datosConvenioMock.put("Proyecto - App de salud", new ConvenioData(
	                "Asociación Civil SaludAR", "Dr. Pablo Núñez", "32122333",
	                "Carlos Méndez", "45123456", "Licenciatura en Sistemas",
	                "Dra. Susana Ríos", "27888999",
	                "Ing. Ernesto Vargas", "30222111"
	        ));
	    }

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(VentanaCrearConvenio::new);
	    }

	    static class ConvenioData {
	        String entidad, representante, dniRep;
	        String estudiante, dniEst, carrera;
	        String tutor, dniTutor;
	        String supervisor, dniSupervisor;

	        public ConvenioData(String entidad, String representante, String dniRep,
	                            String estudiante, String dniEst, String carrera,
	                            String tutor, String dniTutor,
	                            String supervisor, String dniSupervisor) {
	            this.entidad = entidad;
	            this.representante = representante;
	            this.dniRep = dniRep;
	            this.estudiante = estudiante;
	            this.dniEst = dniEst;
	            this.carrera = carrera;
	            this.tutor = tutor;
	            this.dniTutor = dniTutor;
	            this.supervisor = supervisor;
	            this.dniSupervisor = dniSupervisor;
	        }
	    }
	}

