package umu.tds.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginWindow {

	private JFrame frame;
	private JTextField textField;
	private JTextField txtSalir;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow window = new LoginWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 523, 301);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panelFormularioLogin = new JPanel();
		panelFormularioLogin.setPreferredSize(new Dimension(300, 200));
		panel.add(panelFormularioLogin);
		GridBagLayout gbl_panelFormularioLogin = new GridBagLayout();
		gbl_panelFormularioLogin.columnWidths = new int[]{20, 20, 182, 0, 0};
		gbl_panelFormularioLogin.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panelFormularioLogin.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panelFormularioLogin.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelFormularioLogin.setLayout(gbl_panelFormularioLogin);
		
		JLabel lblLoginAppmusic = new JLabel("Login AppMusic");
		GridBagConstraints gbc_lblLoginAppmusic = new GridBagConstraints();
		gbc_lblLoginAppmusic.gridwidth = 2;
		gbc_lblLoginAppmusic.insets = new Insets(0, 0, 5, 5);
		gbc_lblLoginAppmusic.gridx = 2;
		gbc_lblLoginAppmusic.gridy = 0;
		panelFormularioLogin.add(lblLoginAppmusic, gbc_lblLoginAppmusic);
		lblLoginAppmusic.setHorizontalAlignment(SwingConstants.LEFT);
		lblLoginAppmusic.setFont(new Font("FreeSans", Font.BOLD, 16));
		
		JLabel lblUsuario = new JLabel("Usuario");
		GridBagConstraints gbc_lblUsuario = new GridBagConstraints();
		gbc_lblUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuario.anchor = GridBagConstraints.EAST;
		gbc_lblUsuario.gridx = 1;
		gbc_lblUsuario.gridy = 1;
		panelFormularioLogin.add(lblUsuario, gbc_lblUsuario);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 1;
		panelFormularioLogin.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblClave = new JLabel("Clave");
		GridBagConstraints gbc_lblClave = new GridBagConstraints();
		gbc_lblClave.anchor = GridBagConstraints.EAST;
		gbc_lblClave.insets = new Insets(0, 0, 5, 5);
		gbc_lblClave.gridx = 1;
		gbc_lblClave.gridy = 2;
		panelFormularioLogin.add(lblClave, gbc_lblClave);
		
		txtSalir = new JTextField();
		GridBagConstraints gbc_txtSalir = new GridBagConstraints();
		gbc_txtSalir.insets = new Insets(0, 0, 5, 0);
		gbc_txtSalir.gridwidth = 2;
		gbc_txtSalir.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSalir.gridx = 2;
		gbc_txtSalir.gridy = 2;
		panelFormularioLogin.add(txtSalir, gbc_txtSalir);
		txtSalir.setColumns(10);
		
		JButton BotonLogin = new JButton("Login");
		BotonLogin.setHorizontalAlignment(SwingConstants.LEFT);
		BotonLogin.setActionCommand("");
		GridBagConstraints gbc_BotonLogin = new GridBagConstraints();
		gbc_BotonLogin.insets = new Insets(0, 0, 0, 5);
		gbc_BotonLogin.gridx = 2;
		gbc_BotonLogin.gridy = 3;
		panelFormularioLogin.add(BotonLogin, gbc_BotonLogin);
		
		JButton btnNewButton_1 = new JButton("Registro");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame registerWindow = new RegisterWindow(frame);
				registerWindow.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnNewButton_1.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.gridx = 3;
		gbc_btnNewButton_1.gridy = 3;
		panelFormularioLogin.add(btnNewButton_1, gbc_btnNewButton_1);
	}

}
