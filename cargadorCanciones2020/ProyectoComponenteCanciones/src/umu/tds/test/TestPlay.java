package umu.tds.test;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;

import javax.swing.JTextField;

import java.awt.Insets;

import javax.swing.JButton;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.swing.JCheckBox;

import umu.tds.componente.Cancion;
import umu.tds.componente.Canciones;
import umu.tds.componente.MapperCancionesXMLtoJava;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.border.EtchedBorder;
import java.awt.Color;

public class TestPlay {

	private JFrame frmReproductorDeCanciones;
	private JTextField textURL;
	private MediaPlayer mediaPlayer;
	private String binPath;
	private String tempPath;
	private JCheckBox playXML;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestPlay window = new TestPlay();
					window.frmReproductorDeCanciones.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TestPlay() {
		initialize();
	}

	private void playCancion(String url) {
		URL uri = null;
		try {
			com.sun.javafx.application.PlatformImpl.startup(() -> {
			});

			uri = new URL(url);

			System.setProperty("java.io.tmpdir", tempPath);
			Path mp3 = Files.createTempFile("now-playing", ".mp3");

			System.out.println(mp3.getFileName());
			try (InputStream stream = uri.openStream()) {
				Files.copy(stream, mp3, StandardCopyOption.REPLACE_EXISTING);
			}
			System.out.println("finished-copy: " + mp3.getFileName());

			Media media = new Media(mp3.toFile().toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			
			mediaPlayer.play();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void stopCancion() {
		if (mediaPlayer != null) mediaPlayer.stop();
		File directorio = new File(tempPath);
		String[] files = directorio.list();
		for (String archivo : files) {
			File fichero = new File(tempPath + File.separator + archivo);
			fichero.delete();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mediaPlayer = null;
		binPath = TestPlay.class.getClassLoader().getResource(".").getPath();
		binPath = binPath.replaceFirst("/", "");
		// quitar "/" añadida al inicio del path en plataforma Windows
		tempPath = binPath.replace("/bin", "/temp");

		frmReproductorDeCanciones = new JFrame();
		frmReproductorDeCanciones.setTitle("Reproductor de Canciones");
		frmReproductorDeCanciones.setBounds(100, 100, 451, 227);
		frmReproductorDeCanciones.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frmReproductorDeCanciones.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 30, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 30, 0, 0, 0, 20, 30, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblUrl = new JLabel("(1) URL: ");
		GridBagConstraints gbc_lblUrl = new GridBagConstraints();
		gbc_lblUrl.insets = new Insets(0, 0, 5, 5);
		gbc_lblUrl.anchor = GridBagConstraints.EAST;
		gbc_lblUrl.gridx = 1;
		gbc_lblUrl.gridy = 1;
		panel.add(lblUrl, gbc_lblUrl);

		textURL = new JTextField();
		GridBagConstraints gbc_textURL = new GridBagConstraints();
		gbc_textURL.fill = GridBagConstraints.HORIZONTAL;
		gbc_textURL.insets = new Insets(0, 0, 5, 5);
		gbc_textURL.gridx = 2;
		gbc_textURL.gridy = 1;
		panel.add(textURL, gbc_textURL);
		textURL.setColumns(25);

		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 2;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 2;
		panel.add(panel_1, gbc_panel_1);

		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!playXML.isSelected()) {
					if (!textURL.getText().equals("")) {
						String url = textURL.getText();
						playCancion(url);
					}
				} else {
					Canciones canciones = MapperCancionesXMLtoJava
							.cargarCanciones("xml/canciones.xml");
					
					Random random = new Random();
					int numCancion = random.nextInt(canciones.getCancion().size());
					int posicion = 0;
					for (Cancion cancion : canciones.getCancion()) {
						if (posicion == numCancion) {
							playCancion(cancion.getURL());
							break;
						}
						posicion ++;
					}

				}
			}
		});
		
		JLabel label = new JLabel("(2)");
		panel_1.add(label);

		playXML = new JCheckBox("Random XML");
		panel_1.add(playXML);
		panel_1.add(btnPlay);

		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopCancion();
			}
		});
		panel_1.add(btnStop);
		
		JTextArea txtrReproduzcaCancionesMediante = new JTextArea();
		txtrReproduzcaCancionesMediante.setBackground(new Color(220, 220, 220));
		txtrReproduzcaCancionesMediante.setForeground(new Color(0, 0, 139));
		txtrReproduzcaCancionesMediante.setEditable(false);
		txtrReproduzcaCancionesMediante.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		txtrReproduzcaCancionesMediante.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		txtrReproduzcaCancionesMediante.setText("\n  Reproduzca canciones mediante urls de tipo HTTP(S):\n    (1) Introduciendo la URL en la entrada de texto\n    (2) Introduciendo las canciones en el fichero 'xml/canciones.xml'  \n");
		GridBagConstraints gbc_txtrReproduzcaCancionesMediante = new GridBagConstraints();
		gbc_txtrReproduzcaCancionesMediante.gridheight = 2;
		gbc_txtrReproduzcaCancionesMediante.insets = new Insets(0, 0, 5, 5);
		gbc_txtrReproduzcaCancionesMediante.fill = GridBagConstraints.BOTH;
		gbc_txtrReproduzcaCancionesMediante.gridx = 2;
		gbc_txtrReproduzcaCancionesMediante.gridy = 3;
		panel.add(txtrReproduzcaCancionesMediante, gbc_txtrReproduzcaCancionesMediante);
	}

}
