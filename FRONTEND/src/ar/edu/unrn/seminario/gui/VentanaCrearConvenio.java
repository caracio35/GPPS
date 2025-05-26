package ar.edu.unrn.seminario.gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.PropuestaDTO;

public class VentanaCrearConvenio extends JFrame {
    private JTextField tfEntidad, tfRepresentante, tfDniRep;
    private JTextField tfEstudiante, tfDniEst, tfCarrera;
    private JTextField tfTutor, tfDniTutor, tfSupervisor, tfDniSupervisor;

    private IApi api;
    private PropuestaDTO propuestaSeleccionada;

    public VentanaCrearConvenio(IApi api ,String tituloProyecto) {
        setTitle("Generar Acta Acuerdo");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(12, 2, 5, 5));

        propuestaSeleccionada = api.obtenerPropuestaPorTitulo(tituloProyecto); 
        
        
        tfEntidad = new JTextField(); 
        tfEntidad.setEditable(false);
        tfRepresentante = new JTextField();
        tfRepresentante.setEditable(false);
        tfDniRep = new JTextField(); 
        tfDniRep.setEditable(false);
        tfEstudiante = new JTextField(); 
        tfEstudiante.setEditable(false);
        tfDniEst = new JTextField(); 
        tfDniEst.setEditable(false);
        tfCarrera = new JTextField(); 
        tfCarrera.setEditable(false);
        tfTutor = new JTextField(); 
        tfTutor.setEditable(false);
        tfDniTutor = new JTextField(); 
        tfDniTutor.setEditable(false);
        tfSupervisor = new JTextField(); 
        tfSupervisor.setEditable(false);
        tfDniSupervisor = new JTextField(); 
        tfDniSupervisor.setEditable(false);

        add(new JLabel("Título Proyecto:"));
        add(new JLabel(propuestaSeleccionada.getTitulo()));
        add(new JLabel("Entidad:")); 
        add(tfEntidad);
        add(new JLabel("Representante:")); 
        add(tfRepresentante);
        add(new JLabel("DNI Representante:")); 
        add(tfDniRep);
        add(new JLabel("Estudiante:"));
        add(tfEstudiante);
        add(new JLabel("DNI Estudiante:")); 
        add(tfDniEst);
        add(new JLabel("Carrera:")); 
        add(tfCarrera);
        add(new JLabel("Tutor Académico:")); 
        add(tfTutor);
        add(new JLabel("DNI Tutor:"));
        add(tfDniTutor);
        add(new JLabel("Docente Supervisor:")); 
        add(tfSupervisor);
        add(new JLabel("DNI Supervisor:")); 
        add(tfDniSupervisor);

        JButton btnGenerar = new JButton("Generar Acta");
        btnGenerar.addActionListener(e -> generarConvenio());
        add(btnGenerar); add(new JLabel()); 
        cargarDatos();
        setVisible(true);
    }

    private void cargarDatos() {
        // ⚠️ Acá tenés que usar tu DTO real. Ejemplo:
        tfEntidad.setText("Entidad vinculada (ejemplo)"); // Podés agregar el nombre real de la entidad
        tfRepresentante.setText("Representante Ejemplo");
        tfDniRep.setText("30111222");
        tfEstudiante.setText("Alumno Ejemplo");
        tfDniEst.setText("44111222");
        tfCarrera.setText("Carrera Ejemplo");
        tfTutor.setText("Tutor Ejemplo");
        tfDniTutor.setText("22111000");
        tfSupervisor.setText("Supervisor Ejemplo");
        tfDniSupervisor.setText("25111444");

        // 🔹 En la práctica, todos estos valores deberían salir de `propuestaSeleccionada` o de tu base de datos.
    }

    private void generarConvenio() {
        // Lógica para generar el acta de convenio en la base de datos
        JOptionPane.showMessageDialog(this, "¡Acta generada con éxito!");
        // ⚠️ Podés implementar la lógica de persistencia real acá
    }
}
	   

