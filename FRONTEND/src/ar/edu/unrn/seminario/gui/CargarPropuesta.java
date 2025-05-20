package ar.edu.unrn.seminario.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


import java.awt.BorderLayout;
import java.awt.Dimension;

import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.ActividadDTO;
import ar.edu.unrn.seminario.dto.PropuestaDTO;
import ar.edu.unrn.seminario.dto.UsuarioSimplificadoDTO;
import java.util.ArrayList;
import java.util.List;

public class CargarPropuesta extends JDialog {
    private JTextField tituloField;
    private JTextField areaField;
    private JTextArea objetivoArea;
    private JTextArea descripcionArea;
    private JTable actividadesTable;
    private DefaultTableModel tableModel;
    private JLabel totalHorasLabel;
    private int totalHoras = 0;
    private UsuarioSimplificadoDTO usuario; // Agregar el DTO de usuario

    public CargarPropuesta(JFrame parent, UsuarioSimplificadoDTO usuario) {
        super(parent, "Cargar Propuesta", true);
        this.usuario = usuario; // Guardar el usuario que está subiendo la propuesta

        // Panel con fondo degradado
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(240, 248, 255);
                Color color2 = new Color(248, 248, 255);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Panel superior con título
        JLabel header = new JLabel("Carga de Propuesta");
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setForeground(new Color(33, 150, 243));
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel.add(header, BorderLayout.NORTH);

        // Panel central con formulario
        JPanel formPanel = new JPanel();
        formPanel.setOpaque(false);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        
        // Estilo para las etiquetas
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Color labelColor = new Color(70, 70, 70);

        // Título
        JPanel tituloPanel = createFieldPanel("Título de propuesta:", labelFont, labelColor);
        tituloField = createTextField();
        tituloPanel.add(tituloField);
        formPanel.add(tituloPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Área de interés
        JPanel areaPanel = createFieldPanel("Área de interés:", labelFont, labelColor);
        areaField = createTextField();
        areaPanel.add(areaField);
        formPanel.add(areaPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Objetivo del proyecto
        JPanel objetivoPanel = createFieldPanel("Objetivo del proyecto:", labelFont, labelColor);
        objetivoArea = createTextArea(3);
        JScrollPane objetivoScroll = new JScrollPane(objetivoArea);
        objetivoScroll.setPreferredSize(new Dimension(400, 80));
        objetivoPanel.add(objetivoScroll);
        formPanel.add(objetivoPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Descripción del proyecto
        JPanel descripcionPanel = createFieldPanel("Breve descripción del proyecto:", labelFont, labelColor);
        descripcionArea = createTextArea(4);
        JScrollPane descripcionScroll = new JScrollPane(descripcionArea);
        descripcionScroll.setPreferredSize(new Dimension(400, 100));
        descripcionPanel.add(descripcionScroll);
        formPanel.add(descripcionPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Tabla de actividades
        JLabel actividadesLabel = new JLabel("Actividades y horas:");
        actividadesLabel.setFont(labelFont);
        actividadesLabel.setForeground(labelColor);
        actividadesLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        formPanel.add(actividadesLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Modelo de tabla
        String[] columnNames = {"Actividad", "Horas"};
        tableModel = new DefaultTableModel(columnNames, 0);
        actividadesTable = new JTable(tableModel);
        actividadesTable.setRowHeight(25);
        actividadesTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        JScrollPane tableScroll = new JScrollPane(actividadesTable);
        tableScroll.setPreferredSize(new Dimension(400, 150));
        formPanel.add(tableScroll);
        
        // Panel para botones de la tabla
        JPanel tableButtonPanel = new JPanel();
        tableButtonPanel.setOpaque(false);
        tableButtonPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        
        JButton addRowButton = new JButton("Agregar Actividad");
        addRowButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        addRowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.addRow(new Object[]{"", 0});
            }
        });
        
        JButton removeRowButton = new JButton("Eliminar Actividad");
        removeRowButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        removeRowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = actividadesTable.getSelectedRow();
                if (selectedRow != -1) {
                    tableModel.removeRow(selectedRow);
                    calcularTotalHoras();
                }
            }
        });
        
        tableButtonPanel.add(addRowButton);
        tableButtonPanel.add(removeRowButton);
        formPanel.add(tableButtonPanel);
        
        // Total de horas
        JPanel totalPanel = new JPanel();
        totalPanel.setOpaque(false);
        totalPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        
        JLabel totalLabel = new JLabel("Total de horas:");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalHorasLabel = new JLabel("0");
        totalHorasLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalHorasLabel.setForeground(new Color(33, 150, 243));
        
        totalPanel.add(totalLabel);
        totalPanel.add(totalHorasLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(totalPanel);
        
        // Agregar listener para actualizar total de horas
        tableModel.addTableModelListener(e -> calcularTotalHoras());
        
        // Agregar panel de formulario al panel principal
        JScrollPane formScrollPane = new JScrollPane(formPanel);
        formScrollPane.setBorder(null);
        formScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(formScrollPane, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        
        JButton guardarBtn = new JButton("Subir Propuesta");
        guardarBtn.setBackground(new Color(76, 175, 80));
        guardarBtn.setForeground(Color.WHITE);
        guardarBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        guardarBtn.setFocusPainted(false);
        guardarBtn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        guardarBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                guardarBtn.setBackground(new Color(56, 142, 60));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                guardarBtn.setBackground(new Color(76, 175, 80));
            }
        });
        
        guardarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recogerDatos();
            }
        });

        JButton cancelarBtn = new JButton("Cancelar");
        cancelarBtn.setBackground(new Color(244, 67, 54));
        cancelarBtn.setForeground(Color.WHITE);
        cancelarBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancelarBtn.setFocusPainted(false);
        cancelarBtn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        cancelarBtn.addActionListener(e -> dispose());
        
        cancelarBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cancelarBtn.setBackground(new Color(211, 47, 47));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                cancelarBtn.setBackground(new Color(244, 67, 54));
            }
        });
        
        buttonPanel.add(guardarBtn);
        buttonPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonPanel.add(cancelarBtn);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Agregar filas iniciales a la tabla
        tableModel.addRow(new Object[]{"", 0});
        tableModel.addRow(new Object[]{"", 0});
        tableModel.addRow(new Object[]{"", 0});

        setContentPane(panel);
        setSize(650, 700);
        setLocationRelativeTo(parent);
        getRootPane().setDefaultButton(guardarBtn);
    }
    
    private JPanel createFieldPanel(String labelText, Font labelFont, Color labelColor) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        label.setForeground(labelColor);
        label.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        return panel;
    }
    
    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        field.setAlignmentX(JTextField.LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, field.getPreferredSize().height));
        return field;
    }
    
    private JTextArea createTextArea(int rows) {
        JTextArea area = new JTextArea(rows, 20);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        area.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        return area;
    }
    
    private void calcularTotalHoras() {
        totalHoras = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Object value = tableModel.getValueAt(i, 1);
            if (value != null) {
                try {
                    totalHoras += Integer.parseInt(value.toString());
                } catch (NumberFormatException e) {
                    // Ignorar valores no numéricos
                }
            }
        }
        totalHorasLabel.setText(String.valueOf(totalHoras));
    }
    
    void recogerDatos() {
        try {
            // Validar campos obligatorios
            if (tituloField.getText().trim().isEmpty() || 
                areaField.getText().trim().isEmpty() || 
                objetivoArea.getText().trim().isEmpty() || 
                descripcionArea.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Por favor complete todos los campos obligatorios",
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Armar lista de actividades desde la tabla y calcular total de horas
            int totalHoras = 0;
            List<ActividadDTO> actividades = new ArrayList<>();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String nombre = tableModel.getValueAt(i, 0).toString().trim();
                String horasStr = tableModel.getValueAt(i, 1).toString().trim();
                if (!nombre.isEmpty() && !horasStr.equals("0")) {
                    try {
                        int horas = Integer.parseInt(horasStr);
                        totalHoras += horas;
                        actividades.add(new ActividadDTO(nombre, horas));
                    } catch (NumberFormatException e) {
                        // Ignorar filas con datos inválidos
                    }
                }
            }

            // Crear DTO de propuesta
            PropuestaDTO propuesta = new PropuestaDTO(
                tituloField.getText().trim(),
                areaField.getText().trim(),
                objetivoArea.getText().trim(),
                descripcionArea.getText().trim(),
                usuario.getDni(), // Asegurate de tener este campo en UsuarioSimplificadoDTO
                totalHoras
            );

            // Llamar a la API de persistencia
            PersistenceApi api = new PersistenceApi();
            api.guardarPropuesta(propuesta, actividades);

            JOptionPane.showMessageDialog(this,
                "La propuesta se ha guardado exitosamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
            
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al guardar la propuesta: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

}