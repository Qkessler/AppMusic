package umu.tds.apps.ui;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import umu.tds.apps.models.Song;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class RecentSongsPanel extends JPanel {
	private static final String IMAGE_PATH = "/umu/tds/apps/images/";
	private static final Color DEFAULT_BACKGROUND = new Color(10, 37, 64, 255);
	private JTable table;

	public RecentSongsPanel() {
		setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		setPreferredSize(new Dimension(780, 550));
		add(panel, BorderLayout.SOUTH);
		createSongsTable();
		createMediaButtons(panel);

	}
	
	private void createSongsTable() {
		DefaultTableModel tableModel = new DefaultTableModel();
		tableModel.addColumn("Title");
		tableModel.addColumn("Artist");
		
		// Dummy data.
		for(int i = 0; i < 100; i++) {
			Object[] array = new Object[2];
			array[0] = "cancion";
			array[1] = "interprete";
			tableModel.addRow(array);
		}
				
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.NORTH);
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
		JButton btnPause = createIconButton(IMAGE_PATH + "controller-play.png");
		JButton btnForward = createIconButton(IMAGE_PATH + "controller-next.png");
		panel.add(btnBack);
		panel.add(btnPause);
		panel.add(btnForward);
	}

}
