package umu.tds.apps.ui;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import umu.tds.apps.controller.AppMusicController;
import umu.tds.apps.models.PlayList;
import umu.tds.apps.models.Song;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.border.TitledBorder;

public class NewPlayListPanel extends JPanel {
	//TODO find a way to change the size not when the panel is initialized, but when a playlist is being created.
	private static final int WIDTH = 710;
	private static final int HEIGHT = 500;
	
	private AppMusicController controller;
	private JPanel mainPanel;
	private JPanel topButtons;
	private JTextField createTextField;
	private PlayList playlist;
	private JFrame frmMainView;

	
	private JTextField artistTextField;
	private JTextField titleTextField;
	private String genreValue;
	private Map<Integer, Song> filteredSongs;
	private JTable table;
	private JTable tablePl;
	private JScrollPane tablePanel;
	private JScrollPane tablePlPanel; 
	private JPanel centerPanel;
	private Box addRmvButtonsBox;
	private JPanel acceptCancelPanel;
	private JButton btnDelete;



	/**
	 * Create the panel.
	 */
	public NewPlayListPanel(JFrame frmMainView) {
		controller = AppMusicController.getInstance();
		this.frmMainView = frmMainView;
		this.filteredSongs = new HashMap<Integer, Song>();		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLayout(new BorderLayout(0, 0));
		
		initialize();
	}

	private void initialize() {
		createTopPanel();
//		createMainPanel();	//esto tiene que estar siempre comentado, solo se descomenta para usar el windowbuilder
	}

	private void createTopPanel() {
		JPanel topPanel = new JPanel();
		add(topPanel, BorderLayout.NORTH);
		
		createTextField = new JTextField();
		topPanel.add(createTextField);
		createTextField.setColumns(10);
		
		JButton btnCreate = new JButton("Create");
		createPlayListFunctionality(btnCreate);
		topPanel.add(btnCreate);
		
		btnDelete = new JButton("Delete");
		deletePlayListFunctionality();
		btnDelete.setVisible(false);
		topPanel.add(btnDelete);
	}

	private void deletePlayListFunctionality() {
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int output = JOptionPane.showConfirmDialog(table, "Are you sure that you want to delete the playlist?",
						"DELETE PLAYLIST?", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
				if (output == JOptionPane.YES_OPTION) {
					controller.deletePlaylist(playlist);
					changeVisibility(false);
					btnDelete.setVisible(false);
				}				
			}
		});
	}

	private void createPlayListFunctionality(JButton btnCreate) {
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlayList pl = controller.existsPlaylist(createTextField.getText());
				if (pl!=null) {
					int output = JOptionPane.showConfirmDialog(table, "That playlist alredy exists. Do you want to edit it?",
																"Edit playlist?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (output == JOptionPane.YES_OPTION) {
						playlist = pl;
						btnDelete.setVisible(true);
						songAdditionPanel();
					}		
				}
				else {
					int output = JOptionPane.showConfirmDialog(table, "Do you want to create a new playlist?",
																"Create playlist?", JOptionPane.YES_NO_OPTION);
					if (output == JOptionPane.YES_OPTION) {
						playlist = new PlayList(createTextField.getText());	//crea la playlist con el nombre del campo de texto
						songAdditionPanel();
					}
				}		
			}
		});
	}
	
	private void songAdditionPanel() {
		setPreferredSize(new Dimension(1200, 620));
		frmMainView.setPreferredSize(getPreferredSize());
		frmMainView.pack();
		frmMainView.revalidate();
		frmMainView.repaint();
		frmMainView.validate();

		createMainPanel();
		changeVisibility(true);

	}
	
	private void createMainPanel() {
		mainPanel = new JPanel();
		add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout(0, 0));
		createTopButtons();
		createCenterPanel();
		createAcceptCancelPanel();
		changeVisibility(false);	
	}

	private void createTopButtons() {
		topButtons = new JPanel();
		mainPanel.add(topButtons, BorderLayout.NORTH);
		JLabel lblArtist = new JLabel("Artist");
		topButtons.add(lblArtist);
		artistTextField = new JTextField();
		topButtons.add(artistTextField);
		artistTextField.setColumns(10);
		JLabel lblTitle = new JLabel("Title");
		topButtons.add(lblTitle);
		titleTextField = new JTextField();
		topButtons.add(titleTextField);
		titleTextField.setColumns(10);
		JLabel lblGenre = new JLabel("Genre");
		topButtons.add(lblGenre);
		JComboBox<String> genreComboBox = new JComboBox<String>();
		for(String genre : controller.getGenres()) {
			genreComboBox.addItem(genre);
		}
		genreComboBox.addItem("");
		updateSelectedItem(genreComboBox);
		genreComboBox.setSelectedItem("");
		topButtons.add(genreComboBox);
		
		JButton btnSearch = new JButton("SEARCH");
		topButtons.add(btnSearch);
		searchFunctionality(btnSearch);
		
	}

	private void createCenterPanel() {
		centerPanel = new JPanel();
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		createSongsTable(artistTextField.getText(),
				artistTextField.getText(), genreValue);
		tablePanel = new JScrollPane(table);
		centerPanel.add(tablePanel);
		
		addRmvButtonsBox = Box.createVerticalBox();
		centerPanel.add(addRmvButtonsBox);
		JButton addSongButton = new JButton(">>");
		addSongFunctionality(addSongButton);
		addRmvButtonsBox.add(addSongButton);
		JButton removeSongButton = new JButton("<<");
		removeSongFunctionality(removeSongButton);
		addRmvButtonsBox.add(removeSongButton);
		
		createPlaylistPanel();
		tablePlPanel = new JScrollPane(tablePl);
		tablePlPanel.setBorder(new TitledBorder(null, "PlayList", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		centerPanel.add(tablePlPanel);
	}
	
	private void addSongFunctionality(JButton addSongButton) {
		addSongButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				centerPanel.remove(tablePlPanel);
				if (table.getRowCount() == 0) return;
				int selectedSong = table.getSelectedRow();
				if (selectedSong < 0) 
					selectedSong = 0;
				playlist.addSong(filteredSongs.get(selectedSong));
				createPlaylistPanel();
				tablePlPanel = new JScrollPane(tablePl);
				tablePlPanel.setBorder(new TitledBorder(null, "PlayList", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				tablePlPanel.setVisible(true);
				centerPanel.add(tablePlPanel);
				validate();
			}
		});
	}

	private void removeSongFunctionality(JButton removeSongButton) {
		removeSongButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				centerPanel.remove(tablePlPanel);
				if (tablePl.getRowCount() == 0) return;
				int selectedSong = tablePl.getSelectedRow();
				if (selectedSong >= 0) 
					playlist.removeSong(selectedSong);
				createPlaylistPanel();
				tablePlPanel = new JScrollPane(tablePl);
				tablePlPanel.setBorder(new TitledBorder(null, "PlayList", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				tablePlPanel.setVisible(true);
				centerPanel.add(tablePlPanel);
				validate();
			}
		});
	}


	private void createPlaylistPanel() {
		DefaultTableModel tableModel = new DefaultTableModel();
		tableModel.addColumn("Title");
		tableModel.addColumn("Artist");
		if (!playlist.getSongs().isEmpty())
			for(Song song : playlist.getSongs()) {
				Object[] array = new Object[2];
				array[0] = song.getTitle();
				array[1] = song.getArtists();
				tableModel.addRow(array);
			}
		tablePl = new JTable(tableModel) {
			// Removing cell editing in tables.
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		ListSelectionModel selectionModel = new DefaultListSelectionModel();
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablePl.setSelectionModel(selectionModel);		
	}

	private void createSongsTable(String artist, String title,
			String genre) {
		DefaultTableModel tableModel = new DefaultTableModel();
		tableModel.addColumn("Title");
		tableModel.addColumn("Artist");
		
		int index = 0;
		filteredSongs.clear();
		for(Song song : controller.filterSongs(artist, title, genre)) {
			Object[] array = new Object[2];
			array[0] = song.getTitle();
			array[1] = song.getArtists();
			tableModel.addRow(array);
			filteredSongs.put(index, song);
			index++;
		}
				
		table = new JTable(tableModel) {
			// Removing cell editing in tables.
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		ListSelectionModel selectionModel = new DefaultListSelectionModel();
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setSelectionModel(selectionModel);
	}
	

	private void createAcceptCancelPanel() {
		acceptCancelPanel = new JPanel();
		mainPanel.add(acceptCancelPanel, BorderLayout.SOUTH);
		JButton btnAccept = new JButton("Accept");
		acceptButtonFunctionality(btnAccept);
		acceptCancelPanel.add(btnAccept);
		JButton btnCancel = new JButton("Cancel");
		cancelButtonFunctionality(btnCancel);
		acceptCancelPanel.add(btnCancel);
	}

	private void acceptButtonFunctionality(JButton btnAccept) {
		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (controller.existsPlaylist(playlist.getName())!=null)		// si la playlist ya existe
					controller.updatePlayList(playlist);
				else 
					controller.registerPlayList(playlist);
				tablePlPanel.setVisible(false);
				tablePanel.setVisible(false);
				changeVisibility(false);
				setPreferredSize(new Dimension(WIDTH, HEIGHT));
				frmMainView.setPreferredSize(getPreferredSize());
				frmMainView.pack();
				frmMainView.revalidate();
				frmMainView.repaint();
				frmMainView.validate();
			}
		});
	}

	private void cancelButtonFunctionality(JButton btnCancel) {
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeVisibility(false);
				setPreferredSize(new Dimension(WIDTH, HEIGHT));
				frmMainView.setPreferredSize(getPreferredSize());
				frmMainView.pack();
				frmMainView.revalidate();
				frmMainView.repaint();
				frmMainView.validate();
			}
		});
	}

	private void searchFunctionality(JButton btnSearch) {
		btnSearch.addActionListener(e -> {
			createSongsTable(artistTextField.getText(), titleTextField.getText(), genreValue);
			centerPanel.remove(tablePanel);
			centerPanel.remove(addRmvButtonsBox);
			centerPanel.remove(tablePlPanel);
			tablePanel = new JScrollPane(table);
			tablePanel.setVisible(true);
			centerPanel.add(tablePanel);
			centerPanel.add(addRmvButtonsBox);
			centerPanel.add(tablePlPanel);
			centerPanel.revalidate();
			centerPanel.repaint();
			this.validate();
		});
	}
	
	
	private void updateSelectedItem(JComboBox<String> combo) {
		combo.addActionListener(e -> {
			genreValue = combo.getSelectedItem().toString();			
		});
	}
	
	private void changeVisibility(Boolean bool) {
		topButtons.setVisible(bool);
		addRmvButtonsBox.setVisible(bool);
		acceptCancelPanel.setVisible(bool);
		tablePlPanel.setVisible(bool);
		tablePanel.setVisible(bool);
	}
}
