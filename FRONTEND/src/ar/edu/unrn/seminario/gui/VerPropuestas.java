package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import accesos.ConnectionManager;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.ActividadDTO;
import ar.edu.unrn.seminario.dto.PropuestaDTO;
import ar.edu.unrn.seminario.dto.UsuarioSimplificadoDTO;

public class VerPropuestas extends JDialog {
    private UsuarioSimplificadoDTO usuario;
    private boolean aprobadas;

    public VerPropuestas(JFrame parent, UsuarioSimplificadoDTO usuario, IApi api,boolean aprobadas) {
        super(parent, "Ver Propuestas", true);
        this.usuario = usuario;
        this.aprobadas = aprobadas;
       

        // Panel principal con degradado
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

        // Cabecera
        JLabel header = new JLabel("Ver Propuestas");
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setForeground(new Color(33, 150, 243));
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel.add(header, BorderLayout.NORTH);

        // Cargar propuestas en un panel de pestañas
        cargarTodasLasPropuestas(api, panel);

        // Botón de cerrar
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton cerrarBtn = new JButton("Cerrar");
        cerrarBtn.setBackground(new Color(244, 67, 54));
        cerrarBtn.setForeground(Color.WHITE);
        cerrarBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cerrarBtn.setFocusPainted(false);
        cerrarBtn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        cerrarBtn.addActionListener(e -> dispose());
        cerrarBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cerrarBtn.setBackground(new Color(211, 47, 47));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cerrarBtn.setBackground(new Color(244, 67, 54));
            }
        });
        buttonPanel.add(cerrarBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(panel);
        setSize(650, 700);
        setLocationRelativeTo(parent);
        getRootPane().setDefaultButton(cerrarBtn);
    }

    private void cargarTodasLasPropuestas(IApi api, JPanel panel) {
        // Create table model with columns
        String[] columnNames = {"Título", "Área de interés", "Descripción"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        List<PropuestaDTO> propuestasTodas = api.obtenerTodasPropuestas();
        List<PropuestaDTO> propuestasAprobadas = new ArrayList<>();

        // Add proposals to table
        for (PropuestaDTO p : propuestasTodas) {
            if (!aprobadas) {    
                if (p.isAceptada()) {
                    propuestasAprobadas.add(p);
                    tableModel.addRow(new Object[]{
                        p.getTitulo(),
                        p.getAreaInteres(),
                        p.getDescripcion()
                    });
                }
            } else {
                if (!p.isAceptada()) { // Cambiado aquí: ahora muestra las NO aceptadas cuando aprobadas es false
                    propuestasAprobadas.add(p);
                    tableModel.addRow(new Object[]{
                        p.getTitulo(),
                        p.getAreaInteres(),
                        p.getDescripcion()
                    });
                }
            }
        }
        // Create and configure table
        JTable propuestasTable = new JTable(tableModel);
        propuestasTable.setRowHeight(25);
        propuestasTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(propuestasTable);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        // Add detail button
        JButton verDetalleBtn = new JButton("Ver Detalle");
        verDetalleBtn.setBackground(new Color(33, 150, 243));
        verDetalleBtn.setForeground(Color.WHITE);
        verDetalleBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        verDetalleBtn.setFocusPainted(false);
        verDetalleBtn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        verDetalleBtn.setEnabled(false);

        // Add selection listener to enable/disable button
        propuestasTable.getSelectionModel().addListSelectionListener(e -> {
            verDetalleBtn.setEnabled(propuestasTable.getSelectedRow() != -1);
        });

        // Add button action
        verDetalleBtn.addActionListener(e -> {
            int selectedRow = propuestasTable.getSelectedRow();
            if (selectedRow != -1) {
                PropuestaDTO propuesta = propuestasAprobadas.get(selectedRow);
                mostrarDetallePropuesta(propuesta);
            }
        });

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(verDetalleBtn);

        // Add components to main panel using BorderLayout
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(contentPanel, BorderLayout.CENTER);
    }

    private void mostrarDetallePropuesta(PropuestaDTO propuesta) {
        JDialog detalleDialog = new JDialog(this, "Detalle de Propuesta", true);
        detalleDialog.setLayout(new BorderLayout(10, 10));

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
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Add fields
        panel.add(createField("Título de propuesta:", propuesta.getTitulo()));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(createField("Área de interés:", propuesta.getAreaInteres()));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(createTextAreaField("Objetivo del proyecto:", propuesta.getObjetivo(), 3));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(createTextAreaField("Breve descripción del proyecto:", propuesta.getDescripcion(), 4));
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Actividades
        JLabel actividadesLabel = new JLabel("Actividades y horas:");
        actividadesLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        actividadesLabel.setForeground(new Color(70, 70, 70));
        panel.add(actividadesLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Tabla de actividades
        String[] columnNames = {"Actividad", "Horas"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        int totalHoras = 0;
        for (ActividadDTO act : propuesta.getActividades()) {
            tableModel.addRow(new Object[]{act.getnombre(), act.getHoras()});
            totalHoras += act.getHoras();
        }

        JTable actividadesTable = new JTable(tableModel);
        actividadesTable.setRowHeight(25);
        actividadesTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        JScrollPane tableScroll = new JScrollPane(actividadesTable);
        tableScroll.setPreferredSize(new Dimension(400, 150));
        panel.add(tableScroll);

        // Total de horas
        JLabel totalLabel = new JLabel("Total de horas: " + totalHoras);
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalLabel.setForeground(new Color(33, 150, 243));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(totalLabel);

        // Scroll pane for the main panel
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(null);
        detalleDialog.add(scrollPane, BorderLayout.CENTER);

        // Close button
        JButton cerrarBtn = new JButton("Cerrar");
        cerrarBtn.setBackground(new Color(244, 67, 54));
        cerrarBtn.setForeground(Color.WHITE);
        cerrarBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cerrarBtn.addActionListener(e -> detalleDialog.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(cerrarBtn);
        detalleDialog.add(buttonPanel, BorderLayout.SOUTH);

        detalleDialog.setSize(750, 900);
        detalleDialog.setLocationRelativeTo(this);
        detalleDialog.setVisible(true);
    }

    private JPanel createField(String labelText, String fieldValue) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(70, 70, 70));
        label.setAlignmentX(JLabel.LEFT_ALIGNMENT);

        JTextField field = new JTextField(fieldValue);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setEditable(false);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        field.setAlignmentX(JTextField.LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, field.getPreferredSize().height));

        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(field);

        return panel;
    }

    private JPanel createTextAreaField(String labelText, String fieldValue, int rows) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(70, 70, 70));
        label.setAlignmentX(JLabel.LEFT_ALIGNMENT);

        JTextArea area = new JTextArea(rows, 20);
        area.setText(fieldValue);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        area.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));

        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setPreferredSize(new Dimension(400, rows * 25));

        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(scrollPane);

        return panel;
    }
}
