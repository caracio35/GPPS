package ar.edu.unrn.seminario.gui;

import javax.swing.*;
import java.awt.*;

public class CargarPropuesta extends JDialog {
    public CargarPropuesta(JFrame parent) {
        super(parent, "Cargar Propuesta", true);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("Título de propuesta:"));
        JTextField tituloField = new JTextField();
        add(tituloField);

        add(new JLabel("Descripción:"));
        JTextArea descripcionArea = new JTextArea(3, 20);
        add(new JScrollPane(descripcionArea));

        add(new JLabel("Tiempo estimado:"));
        JTextField tiempoField = new JTextField();
        add(tiempoField);

        add(new JLabel("Área de interés:"));
        JTextField areaField = new JTextField();
        add(areaField);

        add(new JLabel("Tutor:"));
        JTextField tutorField = new JTextField();
        add(tutorField);

        JButton guardarBtn = new JButton("Guardar");
        add(guardarBtn);

        JButton cancelarBtn = new JButton("Cancelar");
        cancelarBtn.addActionListener(e -> dispose());
        add(cancelarBtn);

        setSize(400, 300);
        setLocationRelativeTo(parent);
    }
}