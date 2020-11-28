package umu.tds.apps.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import umu.tds.apps.controller.AppMusicController;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

public class MainView {

	private JFrame frmMainView;
	private String username;
	private AppMusicController controller;

	public MainView() {
		controller = AppMusicController.getInstance();
		this.username = controller.getCurrentUser().getUsername(); 
		initialize();
	}
	
	public void show() {
		frmMainView.setLocationRelativeTo(null);
		frmMainView.setVisible(true);
	}
	
	private void initialize() {
		frmMainView = new JFrame();
		frmMainView.setBounds(100, 100, 682, 460);
		frmMainView.setResizable(false);
		frmMainView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMainView.getContentPane().setLayout(new BorderLayout(0, 0));
		
		createTopRow();
		
		frmMainView.pack();
	}
	
	private static JButton createSimpleButton(String text) {
		JButton button = new JButton(text);
		button.setForeground(Color.BLACK);
		button.setBackground(Color.WHITE);
		Border line = new LineBorder(Color.BLACK);
		Border margin = new EmptyBorder(5, 15, 5, 15);
		Border compound = new CompoundBorder(line, margin);
		button.setBorder(compound);
		return button;
	}
	
	public void createGreetingLabel(JPanel panel) {
		JLabel txtGreeting = new JLabel();
		txtGreeting.setText("Hey " + username + "!");
		GridBagConstraints gbc_txtGreeting = new GridBagConstraints();
		gbc_txtGreeting.anchor = GridBagConstraints.WEST;
		gbc_txtGreeting.insets = new Insets(0, 0, 5, 5);
		gbc_txtGreeting.gridx = 1;
		gbc_txtGreeting.gridy = 0;
		panel.add(txtGreeting, gbc_txtGreeting);
	}
	
	public void createTopRowButtons(JPanel panel) {
		JButton btnUpgrade = createSimpleButton("Upgrade");
		GridBagConstraints gbc_btnUpgrade = new GridBagConstraints();
		gbc_btnUpgrade.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnUpgrade.insets = new Insets(0, 0, 5, 5);
		gbc_btnUpgrade.gridx = 2;
		gbc_btnUpgrade.gridy = 0;
		panel.add(btnUpgrade, gbc_btnUpgrade);
		
		JButton btnLogout = createSimpleButton("Logout");
		GridBagConstraints gbc_btnLogout = new GridBagConstraints();
		gbc_btnLogout.insets = new Insets(0, 0, 5, 0);
		gbc_btnLogout.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnLogout.gridx = 3;
		gbc_btnLogout.gridy = 0;
		panel.add(btnLogout, gbc_btnLogout);
		
		addLogoutFunctionality(btnLogout);
	}
	
	public void createTopRow() {
		JPanel panel = new JPanel();
		frmMainView.getContentPane().add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{376, 99, 81, 94, 0};
		gbl_panel.rowHeights = new int[]{25, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		createGreetingLabel(panel);
		createTopRowButtons(panel);
	}
	
	private void addLogoutFunctionality(JButton btnLogout) {
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginView loginView = new LoginView();
				loginView.mostrarVentana();
				frmMainView.dispose();
			}
		});
	}
}
