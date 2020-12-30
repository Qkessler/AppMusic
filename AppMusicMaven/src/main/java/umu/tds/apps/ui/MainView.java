package umu.tds.apps.ui;


import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import pulsador.Luz;
import umu.tds.apps.controller.AppMusicController;
import umu.tds.apps.models.PlayList;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class MainView implements ListSelectionListener{

	private JFrame frmMainView;
	private String username;
	private AppMusicController controller;
	private JPanel selectedTab;
	private JFileChooser fileChooser;
	private String songFilePath;
	
	private JPanel panelLateral;
	private JScrollPane playListPane;
	private JList<String> playListslist;
	private JList<Integer> functionalityList;
	private PlayList playlist;
	
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
		frmMainView.setResizable(false);
		frmMainView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMainView.getContentPane().setLayout(new BorderLayout(0, 0));
		frmMainView.add(selectedTab, BorderLayout.CENTER);
		frmMainView.setPreferredSize(selectedTab.getPreferredSize());
		
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
		gbc_txtGreeting.gridx = 2;
		gbc_txtGreeting.gridy = 0;
		panel.add(txtGreeting, gbc_txtGreeting);
	}
	
	private void createTopRowButtons(JPanel panel) {
		fileChooser = new JFileChooser();
		Luz btnUpdate = new Luz();
		GridBagConstraints gbc_btnUpdate = new GridBagConstraints();
		gbc_btnUpdate.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnUpdate.insets = new Insets(0, 0, 5, 0);
		gbc_btnUpdate.gridx = 1;
		gbc_btnUpdate.gridy = 0;
		panel.add(btnUpdate, gbc_btnUpdate);
		btnUpdate.addEncendidoListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				int returnVal = fileChooser.showOpenDialog(frmMainView);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					try {
						songFilePath = file.getAbsolutePath();
						controller.setSongsFile(songFilePath);
						controller.initializeSongs();
					} catch (Exception e) {
						System.out.println("problem accessing file"+file.getAbsolutePath());
					}
				}
				else {
					System.out.println("File access canceled by user.");
				}
				
			}
			
		});

		Icon iconUpgrade = new ImageIcon(getClass().getResource(IMAGE_PATH + "credit.png"));
		JButton btnUpgrade;
		if (controller.getCurrentUser().isPremium())
			btnUpgrade = createSimpleButton("Premium", iconUpgrade);
		else
			btnUpgrade = createSimpleButton("Upgrade", iconUpgrade);
		GridBagConstraints gbc_btnUpgrade = new GridBagConstraints();
		gbc_btnUpgrade.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnUpgrade.insets = new Insets(0, 0, 5, 5);
		gbc_btnUpgrade.gridx = 3;
		gbc_btnUpgrade.gridy = 0;
		panel.add(btnUpgrade, gbc_btnUpgrade);
		
		Icon iconLogout= new ImageIcon(getClass().getResource(IMAGE_PATH + "log-out.png"));
		JButton btnLogout = createSimpleButton("Logout", iconLogout);
		GridBagConstraints gbc_btnLogout = new GridBagConstraints();
		gbc_btnLogout.insets = new Insets(0, 0, 5, 0);
		gbc_btnLogout.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnLogout.gridx = 4;
		gbc_btnLogout.gridy = 0;
		panel.add(btnLogout, gbc_btnLogout);
		
		logoutFunctionality(btnLogout);
		upgradeFunctionality(btnUpgrade);
	}
	
	private void createTopRow() {
		JPanel topRow = new JPanel();
		frmMainView.getContentPane().add(topRow, BorderLayout.NORTH);
		GridBagLayout gbl_topRow = new GridBagLayout();
		gbl_topRow.columnWidths = new int[]{20, 356, 99, 81, 94, 0};
		gbl_topRow.rowHeights = new int[]{25, 0, 0};
		gbl_topRow.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_topRow.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		topRow.setLayout(gbl_topRow);
		
		createGreetingLabel(topRow);
		createTopRowButtons(topRow);
	}
	
	private void createTabSwitcher() {
		panelLateral = new JPanel();
		frmMainView.getContentPane().add(panelLateral, BorderLayout.WEST);
		panelLateral.setLayout(new BorderLayout(0, 0));
		DefaultListModel<Integer> listModel = new DefaultListModel<Integer>();
		listModel.addAll(Arrays.asList(0, 1, 2, 3));
		functionalityList = new JList<Integer>(listModel);
		functionalityList.setFixedCellHeight(50);
		functionalityList.setFixedCellWidth(180);
		functionalityList.setCellRenderer(createListRenderer());
		selectTabFunctionality(functionalityList);
		JScrollPane pane = new JScrollPane(functionalityList);
		panelLateral.add(pane, BorderLayout.CENTER);
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
					if (playListPane!=null)
						playListPane.setVisible(false);
					break;
				case 1:
					selectedTab = new NewPlayListPanel(frmMainView);
					if (playListPane!=null)
						playListPane.setVisible(false);					
					break;
				case 2:
					selectedTab = new RecentSongsPanel();
					if (playListPane!=null)
						playListPane.setVisible(false);
					break;
				case 3:
					createPlaylistSwitcher();
					selectedTab = new MyPlayListsPanel(playlist);
					break;
				default:
					break;
				}
				frmMainView.add(selectedTab, BorderLayout.CENTER);
				frmMainView.setPreferredSize(selectedTab.getPreferredSize());
				frmMainView.pack();			// in order to resize the frame when playlist panels with different preferred sizes are selected
				frmMainView.revalidate();
				frmMainView.repaint();
				frmMainView.validate();
			}
		});
	}
	
	private void createPlaylistSwitcher() {
		DefaultListModel<String> list = new DefaultListModel<String>();
		for (PlayList pl : controller.getAllPlayLists()) {
			list.addElement(pl.getName());
		}
		playListslist = new JList<String>(list);
		playListslist.setBorder(new TitledBorder(null, "Playlists", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		playListslist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playListPane = new JScrollPane(playListslist);
		panelLateral.add(playListPane, BorderLayout.SOUTH);
		playListslist.addListSelectionListener(this);
		panelLateral.repaint();
		panelLateral.revalidate();

	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		frmMainView.remove(selectedTab);
		String returns = playListslist.getSelectedValue();
	    playlist = controller.existsPlaylist(returns);
	    selectedTab = new MyPlayListsPanel(playlist);
	    frmMainView.add(selectedTab, BorderLayout.CENTER);
		frmMainView.setPreferredSize(selectedTab.getPreferredSize());
		frmMainView.revalidate();
		frmMainView.repaint();
		frmMainView.validate();
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
	
	private void upgradeFunctionality(JButton btnUpgrade) {
		btnUpgrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (controller.getCurrentUser().isPremium()) {
					JOptionPane.showMessageDialog(frmMainView, "You alredy are premium.",
							"Premium", JOptionPane.INFORMATION_MESSAGE);
					int output = JOptionPane.showConfirmDialog(frmMainView, "Do you want to generate a PDF with all the playlists and songs?",
							"Premium PDF", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
						if (output == JOptionPane.YES_OPTION) {
						generatePDF();
						}		
					return;
				}
				if (controller.getDiscount().isPresent()) {
					String message = controller.getDiscount().get().getMessage();
					JOptionPane.showMessageDialog(frmMainView, "Discount applied: " + message, "Premium",
								JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(frmMainView, "No discount could be applied.", "Premium",
								JOptionPane.ERROR_MESSAGE);
				}
				controller.upgradeUser();
			}
		});
	}
	
	private void generatePDF() {
		String sep = System.getProperty("os.name").startsWith("Windows") ? "\\" : "/";
		String ruta = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
		try {
	    	FileOutputStream archivo = new FileOutputStream(ruta + sep + "AppMusicReport.pdf");
			Document documento = new Document();
			PdfWriter.getInstance(documento, archivo);
			documento.open();
			documento.add(new Paragraph("Hola Mundo!"));
			documento.add(new Paragraph("SoloInformaticaYAlgoMas.blogspot.com"));
			documento.close();
			JOptionPane.showMessageDialog(frmMainView, "The PDF was succesfully generated at \"" + ruta + "\"" , "PDF generated!", JOptionPane.INFORMATION_MESSAGE);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(frmMainView, "Error: The PDF could not be created", "PDF: Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (DocumentException e) {
			JOptionPane.showMessageDialog(frmMainView, "Error: The PDF could not be created", "PDF: Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

}