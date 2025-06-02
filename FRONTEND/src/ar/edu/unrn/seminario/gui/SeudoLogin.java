package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.UsuarioSimplificadoDTO;

public class SeudoLogin extends JFrame {
	private JComboBox<UsuarioSimplificadoDTO> usuariosComboBox;
	private JButton ingresarButton;
	private IApi api;

	public SeudoLogin(IApi api) {
		this.api = api;
		setTitle("Login de Usuario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 200);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(10, 10));

		JLabel label = new JLabel("Seleccione un usuario para ingresar:", SwingConstants.CENTER);
		add(label, BorderLayout.NORTH);

		usuariosComboBox = new JComboBox<>();
		cargarUsuarios();
		add(usuariosComboBox, BorderLayout.CENTER);

		ingresarButton = new JButton("Ingresar");
		ingresarButton.addActionListener(e -> {
			UsuarioSimplificadoDTO usuarioSeleccionado = (UsuarioSimplificadoDTO) usuariosComboBox.getSelectedItem();
			if (usuarioSeleccionado != null) {
				new VentanaPrincipal(usuarioSeleccionado, api).setVisible(true);
				dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Debe seleccionar un usuario", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		add(ingresarButton, BorderLayout.SOUTH);
	}

	private void cargarUsuarios() {
		try {
			List<UsuarioSimplificadoDTO> usuarios = api.obtenerUsuariosSimplificados(); // Debe existir este método en
																						// la API
			for (UsuarioSimplificadoDTO usuario : usuarios) {
				usuariosComboBox.addItem(usuario);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}