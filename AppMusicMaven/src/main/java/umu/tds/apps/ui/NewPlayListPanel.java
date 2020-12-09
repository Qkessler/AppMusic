package umu.tds.apps.ui;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTable;

import umu.tds.apps.controller.AppMusicController;

public class NewPlayListPanel extends JPanel {
	private static final int WIDTH = 710;
	private static final int HEIGHT = 500;
	
	private JTable table;
	private AppMusicController controller;

	/**
	 * Create the panel.
	 */
	public NewPlayListPanel() {
		controller = AppMusicController.getInstance();
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

}
