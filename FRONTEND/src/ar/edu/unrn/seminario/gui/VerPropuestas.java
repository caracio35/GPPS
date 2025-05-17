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

    public VerPropuestas(JFrame parent, UsuarioSimplificadoDTO usuario) {
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
        String[] columnNames = {"Actividad", "Horas"};
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

    // Método para cargar los datos de una propuesta
    public void cargarPropuesta(int propuestaId) {
        try {
            Connection conn = ConnectionManager.getConnection();
            
            // Cargar datos de la propuesta
            String sqlPropuesta = "SELECT * FROM propuesta WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlPropuesta);
            pstmt.setInt(1, propuestaId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                tituloField.setText(rs.getString("titulo"));
                areaField.setText(rs.getString("area_interes"));
                descripcionArea.setText(rs.getString("descripcion"));
                objetivoArea.setText(rs.getString("objetivo"));
                
                // Cargar actividades
                String sqlActividades = "SELECT * FROM actividad WHERE propuesta_id = ?";
                PreparedStatement pstmtAct = conn.prepareStatement(sqlActividades);
                pstmtAct.setInt(1, propuestaId);
                ResultSet rsAct = pstmtAct.executeQuery();
                
                // Limpiar tabla
                while (tableModel.getRowCount() > 0) {
                    tableModel.removeRow(0);
                }
                
                // Agregar actividades a la tabla
                while (rsAct.next()) {
                    tableModel.addRow(new Object[]{
                        rsAct.getString("nombre_actividad"),
                        rsAct.getInt("horas")
                    });
                }
                
                calcularTotalHoras();
            }
            
            conn.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar la propuesta: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void cargarTodasLasPropuestas() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            
            // Cargar todas las propuestas
            String sqlPropuestas = "SELECT * FROM propuesta";
            pstmt = conn.prepareStatement(sqlPropuestas);
            rs = pstmt.executeQuery();
            
            // Panel principal con pestañas para cada propuesta
            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            
            while (rs.next()) {
                // Crear panel para cada propuesta
                JPanel propuestaPanel = new JPanel();
                propuestaPanel.setLayout(new BoxLayout(propuestaPanel, BoxLayout.Y_AXIS));
                propuestaPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
                
                // Título
                JPanel tituloPanel = createFieldPanel("Título de propuesta:", new Font("Segoe UI", Font.BOLD, 14), new Color(70, 70, 70));
                JTextField tituloField = createTextField();
                tituloField.setText(rs.getString("titulo"));
                tituloField.setEditable(false);
                tituloPanel.add(tituloField);
                propuestaPanel.add(tituloPanel);
                propuestaPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                
                // Área de interés
                JPanel areaPanel = createFieldPanel("Área de interés:", new Font("Segoe UI", Font.BOLD, 14), new Color(70, 70, 70));
                JTextField areaField = createTextField();
                areaField.setText(rs.getString("area_interes"));
                areaField.setEditable(false);
                areaPanel.add(areaField);
                propuestaPanel.add(areaPanel);
                propuestaPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                
                // Objetivo
                JPanel objetivoPanel = createFieldPanel("Objetivo del proyecto:", new Font("Segoe UI", Font.BOLD, 14), new Color(70, 70, 70));
                JTextArea objetivoArea = createTextArea(3);
                objetivoArea.setText(rs.getString("objetivo"));
                objetivoArea.setEditable(false);
                JScrollPane objetivoScroll = new JScrollPane(objetivoArea);
                objetivoScroll.setPreferredSize(new Dimension(400, 80));
                objetivoPanel.add(objetivoScroll);
                propuestaPanel.add(objetivoPanel);
                propuestaPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                
                // Descripción
                JPanel descripcionPanel = createFieldPanel("Breve descripción del proyecto:", new Font("Segoe UI", Font.BOLD, 14), new Color(70, 70, 70));
                JTextArea descripcionArea = createTextArea(4);
                descripcionArea.setText(rs.getString("descripcion"));
                descripcionArea.setEditable(false);
                JScrollPane descripcionScroll = new JScrollPane(descripcionArea);
                descripcionScroll.setPreferredSize(new Dimension(400, 100));
                descripcionPanel.add(descripcionScroll);
                propuestaPanel.add(descripcionPanel);
                propuestaPanel.add(Box.createRigidArea(new Dimension(0, 15)));
                
                // Tabla de actividades
                JPanel actividadesPanel = createFieldPanel("Actividades y horas:", new Font("Segoe UI", Font.BOLD, 14), new Color(70, 70, 70));
                String[] columnNames = {"Actividad", "Horas"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                
                // Cargar actividades
                String sqlActividades = "SELECT * FROM actividad WHERE propuesta_id = ?";
                PreparedStatement pstmtAct = conn.prepareStatement(sqlActividades);
                pstmtAct.setInt(1, rs.getInt("id"));
                ResultSet rsAct = pstmtAct.executeQuery();
                
                int totalHoras = 0;
                while (rsAct.next()) {
                    tableModel.addRow(new Object[]{
                        rsAct.getString("nombre_actividad"),
                        rsAct.getInt("horas")
                    });
                    totalHoras += rsAct.getInt("horas");
                }
                
                JTable actividadesTable = new JTable(tableModel);
                actividadesTable.setRowHeight(25);
                actividadesTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
                JScrollPane tableScroll = new JScrollPane(actividadesTable);
                tableScroll.setPreferredSize(new Dimension(400, 150));
                actividadesPanel.add(tableScroll);
                propuestaPanel.add(actividadesPanel);
                
                // Dentro del método cargarTodasLasPropuestas(), justo antes de agregar la pestaña:
                // Total de horas
                JPanel totalPanel = new JPanel();
                totalPanel.setOpaque(false);
                totalPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
                JLabel totalLabel = new JLabel("Total de horas: " + totalHoras);
                totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
                totalPanel.add(totalLabel);
                propuestaPanel.add(totalPanel);
                
                // Panel para comentarios
                JPanel comentarioPanel = createFieldPanel("Comentarios:", new Font("Segoe UI", Font.BOLD, 14), new Color(70, 70, 70));
                JTextArea comentarioArea = createTextArea(3);
                JScrollPane comentarioScroll = new JScrollPane(comentarioArea);
                comentarioScroll.setPreferredSize(new Dimension(400, 80));
                comentarioPanel.add(comentarioScroll);
                propuestaPanel.add(comentarioPanel);
                propuestaPanel.add(Box.createRigidArea(new Dimension(0, 15)));
                
                // Panel de botones de acción
                JPanel accionesPanel = new JPanel();
                accionesPanel.setOpaque(false);
                accionesPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
                
                JButton aceptarBtn = new JButton("Aceptar Propuesta");
                aceptarBtn.setBackground(new Color(76, 175, 80));
                aceptarBtn.setForeground(Color.WHITE);
                aceptarBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
                aceptarBtn.setFocusPainted(false);
                aceptarBtn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
                
                JButton rechazarBtn = new JButton("Rechazar Propuesta");
                rechazarBtn.setBackground(new Color(244, 67, 54));
                rechazarBtn.setForeground(Color.WHITE);
                rechazarBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
                rechazarBtn.setFocusPainted(false);
                rechazarBtn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
                
                // Agregar acciones a los botones
                final int propuestaId = rs.getInt("id");
                
                // Modificar los action listeners para usar el método actualizarPropuesta
                aceptarBtn.addActionListener(e -> {
                    if (comentarioArea.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this,
                            "Debe ingresar un comentario",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    String fechaActual = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());
                    String comentarioCompleto = "Aceptado el " + fechaActual + "\n" + comentarioArea.getText();
                    actualizarPropuesta(propuestaId, 1, comentarioCompleto);
                });
                
                rechazarBtn.addActionListener(e -> {
                    if (comentarioArea.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this,
                            "Debe ingresar un comentario",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    String fechaActual = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());
                    String comentarioCompleto = "Rechazado el " + fechaActual + "\n" + comentarioArea.getText();
                    actualizarPropuesta(propuestaId, 0, comentarioCompleto);
                });
                
                // Método actualizarPropuesta modificado
                
                
                accionesPanel.add(aceptarBtn);
                accionesPanel.add(Box.createRigidArea(new Dimension(10, 0)));
                accionesPanel.add(rechazarBtn);
                propuestaPanel.add(accionesPanel);
                
                // Agregar la propuesta como una nueva pestaña
                tabbedPane.addTab("Propuesta " + rs.getInt("id"), propuestaPanel);
            }
            
            // Agregar el panel de pestañas al contenido de la ventana
            getContentPane().add(new JScrollPane(tabbedPane), BorderLayout.CENTER);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar las propuestas: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void actualizarPropuesta(int propuestaId, int estado, String comentario) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // Obtener una nueva conexión para esta operación específica
            conn = ConnectionManager.getConnection();
            
            // Preparar y ejecutar la actualización
            stmt = conn.prepareStatement(
                "UPDATE propuesta SET aceptada = ?, comentarios = ? WHERE id = ?");
            stmt.setInt(1, estado);
            stmt.setString(2, comentario);
            stmt.setInt(3, propuestaId);
            
            int resultado = stmt.executeUpdate();
            
            if (resultado > 0) {
                JOptionPane.showMessageDialog(this,
                    estado == 1 ? "Propuesta aceptada exitosamente" : "Propuesta rechazada exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Cerrar la ventana actual
                dispose();
                
                // Crear una nueva instancia de VerPropuestas y mostrarla
                VerPropuestas nuevaVentana = new VerPropuestas(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    this.usuario
                );
                nuevaVentana.cargarTodasLasPropuestas();
                nuevaVentana.setVisible(true);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error al actualizar la propuesta: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            // Cerrar recursos en orden inverso
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

   
}
