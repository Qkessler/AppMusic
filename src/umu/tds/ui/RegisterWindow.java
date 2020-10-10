package umu.tds.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JSpinnerDateEditor;

import java.awt.GridBagLayout;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JButton;

public class RegisterWindow extends JFrame {
	private JTextField nameTextField;
	private JTextField lastNameTextField;
	private JTextField emailTextField;
	private JTextField usernameTextField;
	private JTextField passwordTextField;
	private JTextField rPasswordTextField;
	private JPanel panel;
	private JButton btnRegister;
	private JButton btnCancel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterWindow frame = new RegisterWindow(null);
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
	public RegisterWindow(JFrame invoker) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 650, 292);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 100, 216, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		((JComponent) getContentPane()).setBorder(new EmptyBorder(20, 20, 20, 20));
		
		JLabel lblNombre = new JLabel("First name:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 1;
		getContentPane().add(lblNombre, gbc_lblNombre);
		
		nameTextField = new JTextField();
		GridBagConstraints gbc_nameTextField = new GridBagConstraints();
		gbc_nameTextField.gridwidth = 3;
		gbc_nameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_nameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameTextField.gridx = 2;
		gbc_nameTextField.gridy = 1;
		getContentPane().add(nameTextField, gbc_nameTextField);
		nameTextField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Last name(s):");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 2;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		lastNameTextField = new JTextField();
		GridBagConstraints gbc_lastNameTextField = new GridBagConstraints();
		gbc_lastNameTextField.gridwidth = 3;
		gbc_lastNameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_lastNameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_lastNameTextField.gridx = 2;
		gbc_lastNameTextField.gridy = 2;
		getContentPane().add(lastNameTextField, gbc_lastNameTextField);
		lastNameTextField.setColumns(10);
		
		JLabel lblBirthDate = new JLabel("Birth Date:");
		GridBagConstraints gbc_lblBirthDate = new GridBagConstraints();
		gbc_lblBirthDate.anchor = GridBagConstraints.EAST;
		gbc_lblBirthDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblBirthDate.gridx = 1;
		gbc_lblBirthDate.gridy = 3;
		getContentPane().add(lblBirthDate, gbc_lblBirthDate);
		
		JDateChooser birthDateChooser = new JDateChooser(null, null, null, new JSpinnerDateEditor());
		GridBagConstraints gbc_birthDateChooser = new GridBagConstraints();
		gbc_birthDateChooser.gridwidth = 3;
		gbc_birthDateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_birthDateChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_birthDateChooser.gridx = 2;
		gbc_birthDateChooser.gridy = 3;
		getContentPane().add(birthDateChooser, gbc_birthDateChooser);
		
		
		JLabel lblEmail = new JLabel("Email:");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.EAST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 1;
		gbc_lblEmail.gridy = 4;
		getContentPane().add(lblEmail, gbc_lblEmail);
		
		emailTextField = new JTextField();
		GridBagConstraints gbc_emailTextField = new GridBagConstraints();
		gbc_emailTextField.gridwidth = 3;
		gbc_emailTextField.insets = new Insets(0, 0, 5, 5);
		gbc_emailTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_emailTextField.gridx = 2;
		gbc_emailTextField.gridy = 4;
		getContentPane().add(emailTextField, gbc_emailTextField);
		emailTextField.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.EAST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 5;
		getContentPane().add(lblUsername, gbc_lblUsername);
		
		usernameTextField = new JTextField();
		GridBagConstraints gbc_usernameTextField = new GridBagConstraints();
		gbc_usernameTextField.gridwidth = 3;
		gbc_usernameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_usernameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_usernameTextField.gridx = 2;
		gbc_usernameTextField.gridy = 5;
		getContentPane().add(usernameTextField, gbc_usernameTextField);
		usernameTextField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 1;
		gbc_lblPassword.gridy = 6;
		getContentPane().add(lblPassword, gbc_lblPassword);
		
		passwordTextField = new JTextField();
		GridBagConstraints gbc_passwordTextField = new GridBagConstraints();
		gbc_passwordTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordTextField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordTextField.gridx = 2;
		gbc_passwordTextField.gridy = 6;
		getContentPane().add(passwordTextField, gbc_passwordTextField);
		passwordTextField.setColumns(10);
		
		JLabel lblRepeatPassword = new JLabel("Repeat password:");
		GridBagConstraints gbc_lblRepeatPassword = new GridBagConstraints();
		gbc_lblRepeatPassword.anchor = GridBagConstraints.EAST;
		gbc_lblRepeatPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblRepeatPassword.gridx = 3;
		gbc_lblRepeatPassword.gridy = 6;
		getContentPane().add(lblRepeatPassword, gbc_lblRepeatPassword);
		
		rPasswordTextField = new JTextField();
		GridBagConstraints gbc_rPasswordTextField = new GridBagConstraints();
		gbc_rPasswordTextField.insets = new Insets(0, 0, 5, 5);
		gbc_rPasswordTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_rPasswordTextField.gridx = 4;
		gbc_rPasswordTextField.gridy = 6;
		getContentPane().add(rPasswordTextField, gbc_rPasswordTextField);
		rPasswordTextField.setColumns(10);
		
		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 4;
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 9;
		getContentPane().add(panel, gbc_panel);
		
		btnRegister = new JButton("Register");
		panel.add(btnRegister);
		
		btnCancel = new JButton("Cancel");
		panel.add(btnCancel);
	}
	
	
}
