package Vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Modelo.SqlUsuarios;
import Modelo.hash;
import Modelo.usuarios;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUsuario.setBounds(127, 80, 103, 17);
		contentPane.add(lblUsuario);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(198, 80, 129, 20);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblContrasea.setBounds(101, 126, 103, 17);
		contentPane.add(lblContrasea);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(198, 126, 129, 20);
		contentPane.add(txtPassword);
		
		JButton btnIngresar = new JButton("Iniciar Sesi\u00F3n");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SqlUsuarios modSql = new SqlUsuarios();
				usuarios mod = new usuarios();
				
				String pass = new String(txtPassword.getPassword());
				
				if(!txtUsuario.getText().equals("") && !pass.equals("")) {
					String nuevoPass = hash.sha1(pass);
					
					mod.setUsuario(txtUsuario.getText());
					mod.setPassword(nuevoPass);
					
					if(modSql.login(mod)) {
						
						
					}else {
						JOptionPane.showMessageDialog(null, "Datos incorrectos");
					}
				}else {
					JOptionPane.showMessageDialog(null, "Debe ingresar sus datos");
				}
			}
		});
		btnIngresar.setBounds(165, 196, 103, 23);
		contentPane.add(btnIngresar);
	}
}