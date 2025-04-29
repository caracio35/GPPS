package ar.edu.unrn.seminario.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CargarPropuesta extends JDialog {
    public CargarPropuesta(JFrame parent) {
        super(parent, "Cargar Propuesta", true);

        JLabel header = new JLabel("Carga de Propuesta");
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setForeground(new Color(33, 150, 243));

        JLabel tituloLabel = new JLabel("Título de propuesta:");
        JTextField tituloField = new JTextField(20);
        tituloField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tituloField.setBorder(BorderFactory.createCompoundBorder(
                tituloField.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        JLabel descripcionLabel = new JLabel("Descripción:");
        JTextArea descripcionArea = new JTextArea(3, 20);
        descripcionArea.setLineWrap(true);
        descripcionArea.setWrapStyleWord(true);
        descripcionArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descripcionArea.setBorder(BorderFactory.createCompoundBorder(
                descripcionArea.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        JScrollPane descripcionScroll = new JScrollPane(descripcionArea);

        JLabel tiempoLabel = new JLabel("Tiempo estimado:");
        JTextField tiempoField = new JTextField(20);
        tiempoField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tiempoField.setBorder(BorderFactory.createCompoundBorder(
                tiempoField.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        JLabel areaLabel = new JLabel("Área de interés:");
        JTextField areaField = new JTextField(20);
        areaField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        areaField.setBorder(BorderFactory.createCompoundBorder(
                areaField.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        JLabel tutorLabel = new JLabel("Tutor:");
        JTextField tutorField = new JTextField(20);
        tutorField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tutorField.setBorder(BorderFactory.createCompoundBorder(
                tutorField.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        JButton guardarBtn = new JButton("Guardar");
        guardarBtn.setBackground(new Color(76, 175, 80));
        guardarBtn.setForeground(Color.WHITE);
        guardarBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        guardarBtn.setFocusPainted(false);

        JButton cancelarBtn = new JButton("Cancelar");
        cancelarBtn.setBackground(new Color(244, 67, 54));
        cancelarBtn.setForeground(Color.WHITE);
        cancelarBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancelarBtn.setFocusPainted(false);
        cancelarBtn.addActionListener(e -> dispose());

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(header)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(tituloLabel)
                                        .addComponent(descripcionLabel)
                                        .addComponent(tiempoLabel)
                                        .addComponent(areaLabel)
                                        .addComponent(tutorLabel))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(tituloField)
                                        .addComponent(descripcionScroll)
                                        .addComponent(tiempoField)
                                        .addComponent(areaField)
                                        .addComponent(tutorField)))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(guardarBtn, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                                .addComponent(cancelarBtn, GroupLayout.PREFERRED_SIZE, 120,
                                        GroupLayout.PREFERRED_SIZE)));

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(header)
                        .addGap(10)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(tituloLabel)
                                .addComponent(tituloField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(descripcionLabel)
                                .addComponent(descripcionScroll))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(tiempoLabel)
                                .addComponent(tiempoField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(areaLabel)
                                .addComponent(areaField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(tutorLabel)
                                .addComponent(tutorField))
                        .addGap(20)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(guardarBtn)
                                .addComponent(cancelarBtn)));

        setContentPane(panel);
        setSize(520, 420);
        setLocationRelativeTo(parent);
    }
}