package umu.tds.apps.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
import javax.swing.JPasswordField;
import umu.tds.apps.controller.AppMusic;

public class LoginWindow {

	private JFrame frame;
	private JTextField txtUsername;
	private JPasswordField txtPassword;

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
		gbc_lblLoginAppmusic.insets = new Insets(0, 0, 5, 0);
		gbc_lblLoginAppmusic.gridx = 2;
		gbc_lblLoginAppmusic.gridy = 0;
		panelFormularioLogin.add(lblLoginAppmusic, gbc_lblLoginAppmusic);
		lblLoginAppmusic.setHorizontalAlignment(SwingConstants.LEFT);
		lblLoginAppmusic.setFont(new Font("FreeSans", Font.BOLD, 16));
		
		JLabel lblUser = new JLabel("Username");
		GridBagConstraints gbc_lblUser = new GridBagConstraints();
		gbc_lblUser.insets = new Insets(0, 0, 5, 5);
		gbc_lblUser.anchor = GridBagConstraints.EAST;
		gbc_lblUser.gridx = 1;
		gbc_lblUser.gridy = 1;
		panelFormularioLogin.add(lblUser, gbc_lblUser);
		
		txtUsername = new JTextField();
		GridBagConstraints gbc_txtUsername = new GridBagConstraints();
		gbc_txtUsername.gridwidth = 2;
		gbc_txtUsername.insets = new Insets(0, 0, 5, 0);
		gbc_txtUsername.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUsername.gridx = 2;
		gbc_txtUsername.gridy = 1;
		panelFormularioLogin.add(txtUsername, gbc_txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 1;
		gbc_lblPassword.gridy = 2;
		panelFormularioLogin.add(lblPassword, gbc_lblPassword);
		
		final JButton btnLogin = new JButton("Login");
		btnLogin.setHorizontalAlignment(SwingConstants.LEFT);
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String username = new String(txtUsername.getName());
				String password = new String(txtPassword.getPassword());
				boolean loggedIn = AppMusic.getInstance().login(username, password);
				if(!loggedIn) {
					JOptionPane.showMessageDialog(btnLogin, "Login incorrecto");
				}
				
			}
		});
		
		txtPassword = new JPasswordField();
		GridBagConstraints gbc_txtPassword = new GridBagConstraints();
		gbc_txtPassword.insets = new Insets(0, 0, 5, 5);
		gbc_txtPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPassword.gridx = 2;
		gbc_txtPassword.gridy = 2;
		panelFormularioLogin.add(txtPassword, gbc_txtPassword);
		
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.insets = new Insets(0, 0, 0, 5);
		gbc_btnLogin.gridx = 2;
		gbc_btnLogin.gridy = 3;
		panelFormularioLogin.add(btnLogin, gbc_btnLogin);
		
		JButton btnRegister = new JButton("Registro");
		btnRegister.addActionListener(ev -> {
				JFrame registerWindow = new RegisterWindow(frame);
				registerWindow.setVisible(true);
				frame.setVisible(false);
		});	
		btnRegister.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_btnRegister = new GridBagConstraints();
		gbc_btnRegister.gridx = 3;
		gbc_btnRegister.gridy = 3;
		panelFormularioLogin.add(btnRegister, gbc_btnRegister);
	}

}
