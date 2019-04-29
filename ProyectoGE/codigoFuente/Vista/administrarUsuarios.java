package Vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Modelo.hash;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class administrarUsuarios extends JFrame {

	private JPanel contentPane;
	private JTextField txtCodUser;
	private JTextField txtNombreUser;
	private JTextField txtPass;
	private JTextField txtNombre;
	private JTextField txtCorreo;

	private final String base = "empleados";
	private final String user = "root";
	private final String password = "manolo";
	private final String timeZone = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private final String url = "jdbc:mysql://localhost:3308/" + base + timeZone;
	private Connection con = null;

	PreparedStatement ps;
	ResultSet rs;
	private JTextField txtTipoUser;

	/**
	 * Funci�n que genera la conexi�n con la base de datos
	 */
	public Connection getConexion() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}

	private void limpiarCajas() {
		txtCodUser.setText(null);
		txtNombreUser.setText(null);
		txtPass.setText(null);
		txtNombre.setText(null);
		txtCorreo.setText(null);
		txtTipoUser.setText(null);
	}

	/**
	 * Create the frame.
	 */
	public administrarUsuarios() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 546, 348);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("C\u00F3digo Usuario:");
		lblNewLabel.setBounds(73, 37, 111, 23);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Nombre usuario:");
		lblNewLabel_1.setBounds(73, 85, 85, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblApellido = new JLabel("Contrase\u00F1a:");
		lblApellido.setBounds(90, 116, 85, 14);
		contentPane.add(lblApellido);

		JLabel lblPuesto = new JLabel("Nombre:");
		lblPuesto.setBounds(112, 147, 46, 14);
		contentPane.add(lblPuesto);

		JLabel lblNewLabel_2 = new JLabel("Correo:");
		lblNewLabel_2.setBounds(112, 178, 46, 14);
		contentPane.add(lblNewLabel_2);

		txtCodUser = new JTextField();
		txtCodUser.setBounds(178, 38, 86, 20);
		contentPane.add(txtCodUser);
		txtCodUser.setColumns(10);

		txtNombreUser = new JTextField();
		txtNombreUser.setColumns(10);
		txtNombreUser.setBounds(178, 82, 195, 20);
		contentPane.add(txtNombreUser);

		txtPass = new JTextField();
		txtPass.setColumns(10);
		txtPass.setBounds(178, 113, 195, 20);
		contentPane.add(txtPass);

		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(178, 144, 195, 20);
		contentPane.add(txtNombre);

		txtCorreo = new JTextField();
		txtCorreo.setColumns(10);
		txtCorreo.setBounds(178, 175, 195, 20);
		contentPane.add(txtCorreo);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con = null;

				try {				
					con = getConexion();
					ps = con.prepareStatement("UPDATE usuarios SET usuario=?, password=?, nombre=?, correo=?, id_tipo=? WHERE id=?");
					String nuevoPass = hash.sha1(txtPass.getText());
					ps.setString(1, txtNombreUser.getText());
					ps.setString(2, nuevoPass);
					ps.setString(3, txtNombre.getText());
					ps.setString(4, txtCorreo.getText());
					ps.setInt(5, Integer.parseInt(txtTipoUser.getText()));
					ps.setInt(6, Integer.parseInt(txtCodUser.getText()));

					int res = ps.executeUpdate();

					if (res > 0) {
						JOptionPane.showMessageDialog(null, "Usuario Modificado");
						limpiarCajas();
					} else {
						JOptionPane.showMessageDialog(null, "Error al Modificar Usuario");
						limpiarCajas();
					}
					con.close();
				} catch (Exception err) {
					System.err.println(err);
				}
			}
		});
		btnModificar.setBounds(112, 248, 89, 23);
		contentPane.add(btnModificar);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con = null;

				try {
					con = getConexion();
					ps = con.prepareStatement("DELETE FROM usuarios WHERE id=?");
					ps.setInt(1, Integer.parseInt(txtCodUser.getText()));

					int res = ps.executeUpdate();

					if (res > 0) {
						JOptionPane.showMessageDialog(null, "Usuario Eliminado");
						limpiarCajas();
					} else {
						JOptionPane.showMessageDialog(null, "Error al Eliminar Usuario");
						limpiarCajas();
					}
					con.close();
				} catch (Exception err) {
					System.err.println(err);
				}
			}
		});
		btnEliminar.setBounds(221, 248, 89, 23);
		contentPane.add(btnEliminar);

		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCajas();
			}
		});
		btnLimpiar.setBounds(327, 248, 89, 23);
		contentPane.add(btnLimpiar);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con = null;

				try {
					con = getConexion();
					ps = con.prepareStatement("SELECT usuario,password,nombre,correo,id_tipo FROM usuarios WHERE id=?");
					ps.setString(1, txtCodUser.getText());
					
					rs = ps.executeQuery();
					
					if (rs.next()) {
						txtNombreUser.setText(rs.getString("usuario"));
						txtPass.setText(rs.getString("password"));
						txtNombre.setText(rs.getString("nombre"));
						txtCorreo.setText(rs.getString("correo"));
						txtTipoUser.setText(rs.getString("id_tipo"));
					}else {
						JOptionPane.showMessageDialog(null, "No existe un usuario con ese c�digo, porfavor introduce uno v�lido");
					}
				} catch (Exception err) {
					System.err.println(err);
				}
			}
		});
		btnBuscar.setBounds(284, 37, 89, 23);
		contentPane.add(btnBuscar);
		
		txtTipoUser = new JTextField();
		txtTipoUser.setBounds(178, 206, 195, 20);
		contentPane.add(txtTipoUser);
		txtTipoUser.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Tipo Usuario:");
		lblNewLabel_3.setBounds(90, 209, 68, 14);
		contentPane.add(lblNewLabel_3);
	}
}

