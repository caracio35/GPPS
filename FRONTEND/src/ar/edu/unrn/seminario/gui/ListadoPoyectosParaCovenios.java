package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.PropuestaDTO;
import ar.edu.unrn.seminario.exception.InvalidCantHorasExcepcion;

public class ListadoPoyectosParaCovenios extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private IApi api;
    List<PropuestaDTO> propuestas;

    public ListadoPoyectosParaCovenios(IApi api) {
        setTitle("Proyectos Aprobados");
        setSize(600, 400);
        setLayout(new BorderLayout());

        this.api = api;

        try {
            propuestas = api.obtenerTodasPropuestas();// a ver deveria tener solo las aprobadas?
        } catch (InvalidCantHorasExcepcion e) {
            // rulo
            JOptionPane.showMessageDialog(this,
                    "Error al guardar la propuesta: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        // Cambiamos las columnas para mostrar Título y Área de Interés
        modelo = new DefaultTableModel(new Object[] { "Título", "Área de Interés" }, 0);
        tabla = new JTable(modelo);
        cargarDatos();

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Panel de botones: Generar y Cancelar
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton btnGenerar = new JButton("Generar Convenio");
        btnGenerar.addActionListener(this::abrirVentanaConvenio);
        panelBotones.add(btnGenerar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        panelBotones.add(btnCancelar);

        add(panelBotones, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void cargarDatos() {
        modelo.setRowCount(0);
        for (PropuestaDTO p : propuestas) {
            // Filtrar solo las aprobadas
            if (p.isAceptada() == 1) { // 0 = no aceptada, 1 = aceptada,-1 = rechazada
                String areaInteres = (p.getAreaInteres() != null) ? p.getAreaInteres() : "Sin área asignada";
                modelo.addRow(new Object[] {
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
