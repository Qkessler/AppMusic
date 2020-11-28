package umu.tds.apps.ui;


import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.ListCellRenderer;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import umu.tds.apps.controller.AppMusicController;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JList;

public class MainView {

	private JFrame frmMainView;
	private String username;
	private AppMusicController controller;
	private JPanel selectedTab;
	
	private static final Color DEFAULT_BACKGROUND = new Color(10, 37, 64, 255);
	private static final String IMAGE_PATH = "/umu/tds/apps/images/";

	public MainView() {
		controller = AppMusicController.getInstance();
		selectedTab = new RecentSongsPanel();
		this.username = controller.getCurrentUser().getUsername(); 
		initialize();
	}
	
	public void show() {
		frmMainView.setLocationRelativeTo(null);
		frmMainView.setVisible(true);
	}
	
	private void initialize() {
		frmMainView = new JFrame();
		frmMainView.setTitle("AppMusic");
		frmMainView.setBounds(100, 100, 682, 460);
		frmMainView.setResizable(false);
		frmMainView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMainView.getContentPane().setLayout(new BorderLayout(0, 0));
		
		createTopRow();
		createTabSwitcher();
		
		frmMainView.pack();
	}
	
	private static JButton createSimpleButton(String text, Icon icon) {
		JButton button = new JButton(icon);
		button.setText(text);
		button.setForeground(Color.WHITE);
		button.setBackground(DEFAULT_BACKGROUND);
		Border line = new LineBorder(Color.BLACK);
		Border margin = new EmptyBorder(5, 15, 5, 15);
		Border compound = new CompoundBorder(line, margin);
		button.setBorder(compound);
		return button;
	}
	
	private void createGreetingLabel(JPanel panel) {
		JLabel txtGreeting = new JLabel();
		txtGreeting.setText("Hey " + username + "!");
		GridBagConstraints gbc_txtGreeting = new GridBagConstraints();
		gbc_txtGreeting.anchor = GridBagConstraints.WEST;
		gbc_txtGreeting.insets = new Insets(0, 0, 5, 5);
		gbc_txtGreeting.gridx = 1;
		gbc_txtGreeting.gridy = 0;
		panel.add(txtGreeting, gbc_txtGreeting);
	}
	
	private void createTopRowButtons(JPanel panel) {
		Icon iconUpgrade = new ImageIcon(getClass().getResource("/umu/tds/apps/images/credit.png"));
		JButton btnUpgrade = createSimpleButton("Upgrade", iconUpgrade);
		GridBagConstraints gbc_btnUpgrade = new GridBagConstraints();
		gbc_btnUpgrade.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnUpgrade.insets = new Insets(0, 0, 5, 5);
		gbc_btnUpgrade.gridx = 2;
		gbc_btnUpgrade.gridy = 0;
		panel.add(btnUpgrade, gbc_btnUpgrade);
		
		Icon iconLogout= new ImageIcon(getClass().getResource("/umu/tds/apps/images/log-out.png"));
		JButton btnLogout = createSimpleButton("Logout", iconLogout);
		GridBagConstraints gbc_btnLogout = new GridBagConstraints();
		gbc_btnLogout.insets = new Insets(0, 0, 5, 0);
		gbc_btnLogout.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnLogout.gridx = 3;
		gbc_btnLogout.gridy = 0;
		panel.add(btnLogout, gbc_btnLogout);
		
		logoutFunctionality(btnLogout);
	}
	
	private void createTopRow() {
		JPanel topRow = new JPanel();
		frmMainView.getContentPane().add(topRow, BorderLayout.NORTH);
		GridBagLayout gbl_topRow = new GridBagLayout();
		gbl_topRow.columnWidths = new int[]{376, 99, 81, 94, 0};
		gbl_topRow.rowHeights = new int[]{25, 0, 0};
		gbl_topRow.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_topRow.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		topRow.setLayout(gbl_topRow);
		
		createGreetingLabel(topRow);
		createTopRowButtons(topRow);
	}
	
	private void createTabSwitcher() {
		JPanel panel = new JPanel();
		frmMainView.getContentPane().add(panel, BorderLayout.WEST);
		DefaultListModel<Integer> listModel = new DefaultListModel<Integer>();
		listModel.addAll(Arrays.asList(0, 1, 2, 3));
		JList<Integer> functionalityList = new JList<Integer>(listModel);
		functionalityList.setFixedCellHeight(50);
		functionalityList.setFixedCellWidth(180);
		functionalityList.setCellRenderer(createListRenderer());
		selectTabFunctionality(functionalityList);
		JScrollPane pane = new JScrollPane(functionalityList);
		panel.add(pane);
	}
	
	@SuppressWarnings("serial")
	private static ListCellRenderer<? super Integer> createListRenderer() {
		DefaultListCellRenderer renderer = new DefaultListCellRenderer() {
			private Color selectedBackground = new Color(10, 37, 64, 220);
 
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value,
					int index, boolean isSelected, boolean cellHasFocus) {
				Component c = super.getListCellRendererComponent(
						list, value, index, isSelected, cellHasFocus);
				if (c instanceof JLabel) {
					JLabel label = (JLabel) c;
					switch(index) {
					case 0:
						label.setIcon(new ImageIcon(getClass().getResource(IMAGE_PATH + "magnifying-glass.png")));
						label.setText("Explore");
						label.setForeground(Color.WHITE);
						break;
					case 1:											
						label.setIcon(new ImageIcon(getClass().getResource(IMAGE_PATH + "add-to-list.png")));
						label.setText("New PlayList");
						label.setForeground(Color.WHITE);
						break;
					case 2:				
						label.setIcon(new ImageIcon(getClass().getResource(IMAGE_PATH + "back-in-time.png")));
						label.setText("Recent");
						label.setForeground(Color.WHITE);
						break;
					case 3:											
						label.setIcon(new ImageIcon(getClass().getResource(IMAGE_PATH + "list.png")));
						label.setText("My Lists");
						label.setForeground(Color.WHITE);
						break;
					default:
						break;
					}
					label.setBackground(isSelected ? selectedBackground : DEFAULT_BACKGROUND);
				}
				return c;
			}
		};
		renderer.setHorizontalAlignment(JLabel.CENTER);
		return renderer;
	}
	
	private void selectTabFunctionality(JList<Integer> functionalityList) {
		functionalityList.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				int value = functionalityList.getSelectedValue();
				frmMainView.remove(selectedTab);
				switch(value) {
				case 0:
					selectedTab = new SearchSongsPanel();
					break;
				case 1:
					selectedTab = new NewPlayListPanel();
					break;
				case 2:
					selectedTab = new RecentSongsPanel();
					break;
				case 3:
					selectedTab = new MyPlayListsPanel();
					break;
				default:
					break;
				}
				frmMainView.add(selectedTab, BorderLayout.CENTER);
				frmMainView.revalidate();
				frmMainView.repaint();
				frmMainView.validate();
			}
		});
	}
	
	private void logoutFunctionality(JButton btnLogout) {
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginView loginView = new LoginView();
				loginView.mostrarVentana();
				frmMainView.dispose();
			}
		});
	}
}
