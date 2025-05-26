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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private JTextField tituloField;
    private JTextField areaField;
    private JTextArea objetivoArea;
    private JTextArea descripcionArea;
    private JTable actividadesTable;
    private DefaultTableModel tableModel;
    private JLabel totalHorasLabel;
    private int totalHoras = 0;
    private UsuarioSimplificadoDTO usuario;

    public VerPropuestas(JFrame parent, UsuarioSimplificadoDTO usuario , IApi api) {
        super(parent, "Ver Propuesta", true);
        this.usuario = usuario;

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
        JLabel header = new JLabel("Ver Propuesta");
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
        tituloField.setEditable(false);
        tituloPanel.add(tituloField);
        formPanel.add(tituloPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Área de interés
        JPanel areaPanel = createFieldPanel("Área de interés:", labelFont, labelColor);
        areaField = createTextField();
        areaField.setEditable(false);
        areaPanel.add(areaField);
        formPanel.add(areaPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Objetivo del proyecto
        JPanel objetivoPanel = createFieldPanel("Objetivo del proyecto:", labelFont, labelColor);
        objetivoArea = createTextArea(3);
        objetivoArea.setEditable(false);
        JScrollPane objetivoScroll = new JScrollPane(objetivoArea);
        objetivoScroll.setPreferredSize(new Dimension(400, 80));
        objetivoPanel.add(objetivoScroll);
        formPanel.add(objetivoPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Descripción del proyecto
        JPanel descripcionPanel = createFieldPanel("Breve descripción del proyecto:", labelFont, labelColor);
        descripcionArea = createTextArea(4);
        descripcionArea.setEditable(false);
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
        String[] columnNames = { "Actividad", "Horas" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        actividadesTable = new JTable(tableModel);
        actividadesTable.setRowHeight(25);
        actividadesTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        JScrollPane tableScroll = new JScrollPane(actividadesTable);
        tableScroll.setPreferredSize(new Dimension(400, 150));
        formPanel.add(tableScroll);

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

        // Agregar panel de formulario al panel principal
        JScrollPane formScrollPane = new JScrollPane(formPanel);
        formScrollPane.setBorder(null);
        formScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(formScrollPane, BorderLayout.CENTER);

        // Panel inferior con botón de cerrar
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

    public void cargarTodasLasPropuestas(IApi api) {
        // Simulamos el panel de pestañas que ya tenías
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Usamos la capa de persistencia para obtener las propuestas
        List<PropuestaDTO> propuestas = api.ObtenerTodasPropuestas();

        for (PropuestaDTO p : propuestas) {
            // Crear panel para cada propuesta
            JPanel propuestaPanel = new JPanel();
            propuestaPanel.setLayout(new BoxLayout(propuestaPanel, BoxLayout.Y_AXIS));
            propuestaPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

            // Título
            JPanel tituloPanel = createFieldPanel("Título de propuesta:", new Font("Segoe UI", Font.BOLD, 14),
                    new Color(70, 70, 70));
            JTextField tituloField = createTextField();
            tituloField.setText(p.getTitulo());
            tituloField.setEditable(false);
            tituloPanel.add(tituloField);
            propuestaPanel.add(tituloPanel);
            propuestaPanel.add(Box.createRigidArea(new Dimension(0, 10)));

            // Área de interés
            JPanel areaPanel = createFieldPanel("Área de interés:", new Font("Segoe UI", Font.BOLD, 14),
                    new Color(70, 70, 70));
            JTextField areaField = createTextField();
            areaField.setText(p.getAreaInteres());
            areaField.setEditable(false);
            areaPanel.add(areaField);
            propuestaPanel.add(areaPanel);
            propuestaPanel.add(Box.createRigidArea(new Dimension(0, 10)));

            // Objetivo
            JPanel objetivoPanel = createFieldPanel("Objetivo del proyecto:", new Font("Segoe UI", Font.BOLD, 14),
                    new Color(70, 70, 70));
            JTextArea objetivoArea = createTextArea(3);
            objetivoArea.setText(p.getObjetivo());
            objetivoArea.setEditable(false);
            JScrollPane objetivoScroll = new JScrollPane(objetivoArea);
            objetivoScroll.setPreferredSize(new Dimension(400, 80));
            objetivoPanel.add(objetivoScroll);
            propuestaPanel.add(objetivoPanel);
            propuestaPanel.add(Box.createRigidArea(new Dimension(0, 10)));

            // Descripción
            JPanel descripcionPanel = createFieldPanel("Breve descripción del proyecto:", new Font("Segoe UI", Font.BOLD, 14),
                    new Color(70, 70, 70));
            JTextArea descripcionArea = createTextArea(4);
            descripcionArea.setText(p.getDescripcion());
            descripcionArea.setEditable(false);
            JScrollPane descripcionScroll = new JScrollPane(descripcionArea);
            descripcionScroll.setPreferredSize(new Dimension(400, 100));
            descripcionPanel.add(descripcionScroll);
            propuestaPanel.add(descripcionPanel);
            propuestaPanel.add(Box.createRigidArea(new Dimension(0, 15)));

            // Actividades
            JPanel actividadesPanel = createFieldPanel("Actividades y horas:", new Font("Segoe UI", Font.BOLD, 14),
                    new Color(70, 70, 70));
            String[] columnNames = { "Actividad", "Horas" };
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            int totalHoras = 0;
            for (ActividadDTO act : p.getActividades()) {
                tableModel.addRow(new Object[]{ act.getnombre(), act.getHoras() });
                totalHoras += act.getHoras();
            }

            JTable actividadesTable = new JTable(tableModel);
            actividadesTable.setRowHeight(25);
            actividadesTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
            JScrollPane tableScroll = new JScrollPane(actividadesTable);
            tableScroll.setPreferredSize(new Dimension(400, 150));
            actividadesPanel.add(tableScroll);
            propuestaPanel.add(actividadesPanel);

            // Total de horas
            JPanel totalPanel = new JPanel();
            totalPanel.setOpaque(false);
            totalPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
            JLabel totalLabel = new JLabel("Total de horas: " + totalHoras);
            totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            totalPanel.add(totalLabel);
            propuestaPanel.add(totalPanel);

            // Agregar la propuesta como una nueva pestaña
            tabbedPane.addTab(p.getTitulo(), propuestaPanel);
        }

        // Agregar el panel de pestañas al contenido de la ventana
        getContentPane().removeAll(); // Limpia lo anterior
        getContentPane().add(new JScrollPane(tabbedPane), BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
