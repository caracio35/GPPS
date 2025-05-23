package ar.edu.unrn.seminario.gui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.PropuestaDTO;
public class ListadoPoyectosParaCovenios extends JFrame {

	 private JTable tabla;
	    private DefaultTableModel modelo;
	    private PersistenceApi api;
	    private List<PropuestaDTO> propuestas;

	    public ListadoPoyectosParaCovenios() {
	        setTitle("Proyectos Aprobados");
	        setSize(600, 400);
	        setLayout(new BorderLayout());

	        api = new PersistenceApi();
	        propuestas = api.obtenerPropuestasAprobadas(); //crear metodo pra traer los comvenios ya listo para crear comvenio

	        modelo = new DefaultTableModel(new Object[]{ "Título", "Tutor", "Entidad"}, 0);
	        tabla = new JTable(modelo);
	        cargarDatos();

	        add(new JScrollPane(tabla), BorderLayout.CENTER);

	        JButton btnGenerar = new JButton("Generar Convenio");
	        btnGenerar.addActionListener(this::abrirVentanaConvenio);
	        add(btnGenerar, BorderLayout.SOUTH);

	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setVisible(true);
	    }

	    private void cargarDatos() {
	        //crea metodo para cargar los datos de los comvenios a la lista 
	    }

	    private void abrirVentanaConvenio(ActionEvent e) {
	        int filaSeleccionada = tabla.getSelectedRow();
	        if (filaSeleccionada == -1) {
	            JOptionPane.showMessageDialog(this, "Seleccioná un proyecto.");
	            return;
	        }

	        String tituloPropuesta = "";
	        new VentanaCrearConvenio(tituloPropuesta); // Pasa el ID seleccionado
	    }
	}

