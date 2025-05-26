package ar.edu.unrn.seminario.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.PropuestaDTO;

public class ListadoPoyectosParaCovenios extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private IApi api;
    List<PropuestaDTO> propuestas ;

    public ListadoPoyectosParaCovenios(IApi api) {
    	   setTitle("Proyectos Aprobados");
    	    setSize(600, 400);
    	    setLayout(new BorderLayout());

    	    this.api = api;

    	    propuestas = api.obtenerTodasPropuestas();

    	    // Cambiamos las columnas para mostrar Título y Área de Interés
    	    modelo = new DefaultTableModel(new Object[]{"Título", "Área de Interés"}, 0);
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
    	    modelo.setRowCount(0);
    	    for (PropuestaDTO p : propuestas) {
    	        // Filtrar solo las aprobadas
    	        if (p.isAceptada()) { // o p.getEstado().equals("Aprobada")
    	            String areaInteres = (p.getAreaInteres() != null) ? p.getAreaInteres() : "Sin área asignada";
    	            modelo.addRow(new Object[]{
    	                    p.getTitulo(),
    	                    areaInteres
    	            });
    	        }
    	    }
    	}
    	

    	private void abrirVentanaConvenio(ActionEvent e) {
    	    int filaSeleccionada = tabla.getSelectedRow();
    	    if (filaSeleccionada == -1) {
    	        JOptionPane.showMessageDialog(this, "Seleccioná un proyecto.");
    	        return;
    	    }

    	    String tituloPropuesta = (String) modelo.getValueAt(filaSeleccionada, 0);

    	    new VentanaCrearConvenio(api, tituloPropuesta);
    	}
}

