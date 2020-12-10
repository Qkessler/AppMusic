package umu.tds.apps.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTable;

import umu.tds.apps.controller.AppMusicController;

@SuppressWarnings("serial")
public class MostReproducedPanel extends JPanel {
//	private static final String IMAGE_PATH = "/umu/tds/apps/images/";
//	private static final Color DEFAULT_BACKGROUND = new Color(10, 37, 64, 255);
	private static final int WIDTH = 710;
	private static final int HEIGHT = 550;
	
	private JTable table;
	private AppMusicController controller;

	/**
	 * Create the panel.
	 */
	public MostReproducedPanel() {
		this.controller = AppMusicController.getInstance();
		setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		//add(panel, );
	}

}
