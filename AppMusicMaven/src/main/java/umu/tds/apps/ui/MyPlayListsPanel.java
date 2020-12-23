package umu.tds.apps.ui;

import java.awt.Dimension;

import javax.swing.DefaultListSelectionModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import umu.tds.apps.controller.AppMusicController;
import umu.tds.apps.models.PlayList;
import umu.tds.apps.models.Song;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class MyPlayListsPanel extends JPanel {
	private static final int WIDTH = 710;
	private static final int HEIGHT = 500;
	private static final Color DEFAULT_BACKGROUND = new Color(10, 37, 64, 255);
	private static final String IMAGE_PATH = "/umu/tds/apps/images/";
	private JPanel panel;
	private JTable table;
	private JPanel mediaButtons;
	private JScrollPane tablePanel;
	private JButton btnPlay;
	private JButton btnPause;
	private int songPlaying;
	private AppMusicController controller;
	private PlayList playlist;

	/**
	 * Create the panel.
	 */
	public MyPlayListsPanel(PlayList playlist) {
		controller = AppMusicController.getInstance();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLayout(new BorderLayout(0, 0));
		this.playlist = playlist;
		if (playlist!=null)
			initialize();
	}


	private void initialize() {
		panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		mediaButtons = new JPanel();
		panel.add(mediaButtons, BorderLayout.SOUTH);
		createMediaButtons(mediaButtons);
		createPlaylistPanel();
		tablePanel = new JScrollPane(table);
		tablePanel.setBorder(new TitledBorder(null, playlist.getName(), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(tablePanel, BorderLayout.CENTER);
		
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
			Song song = playlist.getSongs().get(selectedSong);
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
			Song song = playlist.getSongs().get(songPlaying);
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
			Song song = playlist.getSongs().get(songPlaying);
			playSong(song);
		});
	}
}
