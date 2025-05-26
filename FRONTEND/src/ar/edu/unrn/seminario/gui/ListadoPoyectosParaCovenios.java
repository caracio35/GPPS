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

        modelo = new DefaultTableModel(new Object[]{"Título", "Tutor", "Entidad"}, 0);
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
            
            String tutor = (p.getIdProfesoPrincipal() != 0) ? String.valueOf(p.getIdProfesoPrincipal()) : "No asignado";
            String entidad = (p.getIdEntidad() != 0) ? String.valueOf(p.getIdEntidad()) : "No asignada";
            modelo.addRow(new Object[]{
                    p.getTitulo(),
                    tutor,
                    entidad
            });
        }
    }

    private void abrirVentanaConvenio(ActionEvent e) {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccioná un proyecto.");
            return;
        }

        
        String tituloPropuesta = (String) modelo.getValueAt(filaSeleccionada, 0);

       
        new VentanaCrearConvenio(api ,tituloPropuesta);
    }
}

