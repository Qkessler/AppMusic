package umu.tds.apps.ui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import umu.tds.apps.controller.AppMusicController;
import umu.tds.apps.models.Song;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class SearchSongsPanel extends JPanel {
	private static final int WIDTH = 710;
	private static final int HEIGHT = 500;
	private static final String EMPTY = null;
	private static final Color DEFAULT_BACKGROUND = new Color(10, 37, 64, 255);
	private static final String IMAGE_PATH = "/umu/tds/apps/images/";
	
	private JTextField artistTextField;
	private JTextField titleTextField;
	private String genreValue;
	private JTable table;
	private JPanel mediaButtons;
	private JScrollPane tablePanel;
	private JPanel centerPanel;
	private Map<Integer, Song> filteredSongs;
	private AppMusicController controller;
	private int songPlaying;
	
	private JButton btnPlay;
	private JButton btnPause;
		
	public SearchSongsPanel() {
		this.songPlaying = 0;
		this.controller = AppMusicController.getInstance();
		this.filteredSongs = new HashMap<Integer, Song>();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLayout(new BorderLayout(0, 0));
		
		createFilterFields();
		createCenter();
	}
	
	private JButton createIconButton(String path) {
		Icon icon = new ImageIcon(getClass().getResource(path));
		JButton button = new JButton(icon);
		button.setBackground(DEFAULT_BACKGROUND);
		Border line = new LineBorder(Color.BLACK);
		Border margin = new EmptyBorder(5, 15, 5, 15);
		Border compound = new CompoundBorder(line, margin);
		button.setBorder(compound);
		return button;
	}
	
	private void createFilterFields() {
		JPanel filters = new JPanel();
		add(filters, BorderLayout.NORTH);
		
		JLabel lblArtist = new JLabel("Artist");
		filters.add(lblArtist);
		artistTextField = new JTextField();
		filters.add(artistTextField);
		artistTextField.setColumns(10);
		JLabel lblTitle = new JLabel("Title");
		filters.add(lblTitle);
		titleTextField = new JTextField();
		filters.add(titleTextField);
		titleTextField.setColumns(10);
		JLabel lblGenre = new JLabel("Genre");
		filters.add(lblGenre);
		
		JComboBox<String> genreComboBox = new JComboBox<String>();
		for(String genre : controller.getGenres()) {
			genreComboBox.addItem(genre);
		}
		genreComboBox.addItem("");
		updateSelectedItem(genreComboBox);
		
		genreComboBox.setSelectedItem("");
		filters.add(genreComboBox);
	}
	
	private void createMediaButtons(JPanel panel) {
		JButton btnBack = createIconButton(IMAGE_PATH + "controller-jump-to-start.png");
		btnPlay = createIconButton(IMAGE_PATH + "controller-play.png");
		btnPause = createIconButton(IMAGE_PATH + "controller-paus.png");
		JButton btnForward = createIconButton(IMAGE_PATH + "controller-next.png");
		
		btnPause.setVisible(false);
		playFunctionality();
		pauseFunctionality();
		backFunctionality(btnBack);
		forwardFunctionality(btnForward);
		
		panel.add(btnBack);
		panel.add(btnPlay);
		panel.add(btnPause);
		panel.add(btnForward);
	}

	private void createCenter() {
		centerPanel = new JPanel();
		add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));
		mediaButtons = new JPanel();
		mediaButtons.setVisible(false);
		centerPanel.add(mediaButtons, BorderLayout.SOUTH);
		
		createTopButtons(centerPanel);
		createMediaButtons(mediaButtons);
		
		createSongsTable(artistTextField.getText(),
				artistTextField.getText(), genreValue);
		tablePanel = new JScrollPane(table);
		tablePanel.setVisible(false);
		centerPanel.add(tablePanel, BorderLayout.CENTER);
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
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				JTable table =(JTable) mouseEvent.getSource();
				Point point = mouseEvent.getPoint();
				int row = table.rowAtPoint(point);
				if (mouseEvent.getClickCount() == 2 && row != -1) {
					songPlaying = row;
					Song song = filteredSongs.get(songPlaying);
					playSong(song);
				}
			}
		});
	}
	
	private void createTopButtons(JPanel centerPanel) {
		JPanel topButtons = new JPanel();
		centerPanel.add(topButtons, BorderLayout.NORTH);
		
		JButton btnSearch = new JButton("Search");
		topButtons.add(btnSearch);
		searchFunctionality(btnSearch);
		
		JButton btnCancel = new JButton("Cancel");
		topButtons.add(btnCancel);
		cancelFunctionality(btnCancel);
	}
	
	private void searchFunctionality(JButton btnSearch) {
		btnSearch.addActionListener(e -> {
			mediaButtons.setVisible(true);
			createSongsTable(artistTextField.getText(), titleTextField.getText(), genreValue);
			centerPanel.remove(tablePanel);
			tablePanel = new JScrollPane(table);
			tablePanel.setVisible(true);
			centerPanel.add(tablePanel);
			centerPanel.revalidate();
			centerPanel.repaint();
			this.validate();
		});
	}
	
	private void cancelFunctionality(JButton btnCancel) {
		btnCancel.addActionListener(e -> {
			mediaButtons.setVisible(false);
			tablePanel.setVisible(false);
		});
	}
	
	private void updateSelectedItem(JComboBox<String> combo) {
		combo.addActionListener(e -> {
			genreValue = combo.getSelectedItem().toString();			
		});
	}
	
	private void playSong(Song song) {
		controller.playSong(song.getPath());
		controller.addRecentSong(song);
		btnPlay.setVisible(false);
		btnPause.setVisible(true);
		this.revalidate();
		this.repaint();
		this.validate();
	}
	
	private void playFunctionality() {
		btnPlay.addActionListener(e -> {
			if (table.getRowCount() == 0) return;
			int selectedSong = table.getSelectedRow();
			if (selectedSong < 0) {
				selectedSong = 0;
			}
			Song song = filteredSongs.get(selectedSong);
			songPlaying = selectedSong;
			playSong(song);
		});
	}
	
	private void pauseFunctionality() {
		btnPause.addActionListener(e -> {
				// Pauses the song, doesn't really need to check for the selectedSong.
				controller.pauseSong();
				btnPause.setVisible(false);
				btnPlay.setVisible(true);
				this.revalidate();
				this.repaint();
				this.validate();
		});
	}
	
		
	private void forwardFunctionality(JButton btnForward) {
		btnForward.addActionListener(e -> {
			int rowCount = table.getRowCount();
			if (rowCount == 0) return;
			if (songPlaying + 1 < rowCount) {
				++songPlaying;
			}
			else songPlaying = 0; 
			Song song = filteredSongs.get(songPlaying);
			playSong(song);
		});
	}

	private void backFunctionality(JButton btnBack) {
		btnBack.addActionListener(e -> {
			int rowCount = table.getRowCount();
			if (rowCount == 0) return;
			if (songPlaying - 1 < 0) {
				songPlaying = rowCount - 1;
			}
			else songPlaying--; 
			Song song = filteredSongs.get(songPlaying);
			playSong(song);
		});
	}
}
