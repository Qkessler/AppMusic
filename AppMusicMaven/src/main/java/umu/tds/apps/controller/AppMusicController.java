package umu.tds.apps.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.EventObject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import umu.tds.apps.models.Discount;
import umu.tds.apps.models.EducationDiscount;
import umu.tds.apps.models.ElderDiscount;
import umu.tds.apps.models.Song;
import umu.tds.apps.models.SongRepo;
import umu.tds.apps.models.User;
import umu.tds.apps.models.UserRepo;
import umu.tds.apps.models.YouthDiscount;
import umu.tds.apps.persistence.DAOException;
import umu.tds.apps.persistence.FactoriaDAO;
import umu.tds.apps.persistence.ISongAdapterDAO;
import umu.tds.apps.persistence.IUserAdapterDAO;
import umu.tds.componente.Canciones;
import umu.tds.componente.CancionesEvent;
import umu.tds.componente.CancionesListener;
import umu.tds.componente.CargadorCanciones;

public class AppMusicController implements CancionesListener{

	private static AppMusicController instance = null;
	private MediaPlayer mediaPlayer;

	private ISongAdapterDAO songAdapter;
	private IUserAdapterDAO userAdapter;
	// private IPlayListAdapterDAO playListAdapter;

	private UserRepo userRepo;
	private SongRepo songRepo;
	// private PlayListRepo playListRepo;
	
	private User currentUser;
	private Canciones nuevasCanciones;
	private CargadorCanciones cargadorCanciones;
	
	private List<Discount> activeDiscounts;

	private AppMusicController() {
		this.currentUser= null;
		try {
			com.sun.javafx.application.PlatformImpl.startup(()->{});
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Exception: " + ex.getMessage());
		}
		cargadorCanciones = new CargadorCanciones();
		cargadorCanciones.addCancionesListener(this);
		initializeAdapters();
		initializeRepos();
		initializeDiscounts();
	}

	public static AppMusicController getInstance() {
		if (instance == null) {
			instance = new AppMusicController();
		}
		return instance;
	}
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	public boolean isRegistered(String username) {
		return userRepo.getUser(username) != null;
	}
	
	public boolean registerUser(String username, String password,
			String name, String lastName, String email, Date birthDate) {
		if (isRegistered(username)) return false;
		User user = new User(username, password, name, lastName, email, birthDate);
		userAdapter.registerUser(user);
		userRepo.addUser(user);
		return true;
	}
	
	public boolean removeUser(User user) {
		if (!isRegistered(user.getUsername())) return false;
		userAdapter.removeUser(user);
		userRepo.removeUser(user);
		return true;
	}
	
	public boolean login(String username, String password) {
		User user = userRepo.getUser(username);
		if (user != null && user.getPassword().equals(password)) {
			this.currentUser = user;
			return true;
		}
		return false;
	}
	
	public void registerSong(Song song) {
		songAdapter.registerSong(song);
		songRepo.addSong(song);
	}
	
	public ArrayList<Song> getRecentSongs() {
		return (ArrayList<Song>) currentUser.getRecentSongs();
	}

	// Get the songs that are on the "canciones" directory that
	// were not already persistent.
	public void initializeSongs() {
		ArrayList<Song> songs = (ArrayList<Song>) songRepo.initializeSongs(nuevasCanciones);
		for(Song song : songs) {
			registerSong(song);
		}
	}
	
	public List<Song> filterSongs(String artist, String title,
			String genre) {
		ArrayList<Song> filteredSongs = (ArrayList<Song>) songRepo.filterSongs(artist, title, genre);
		return filteredSongs;
	}
	
	public List<String> getGenres() {
		ArrayList<String> genres = songRepo.getGenres();
		return genres;
	}

	public void playSong(String path) {
		File f = new File(path);
		String source = f.toURI().toString();
		if (mediaPlayer == null || !mediaPlayer.getMedia().getSource().equals(source)) {
			Media hit = new Media(source);
			mediaPlayer = new MediaPlayer(hit);
		}
		mediaPlayer.play();
	}
	
	public void pauseSong() {
		mediaPlayer.pause();
	}
	
	public void addRecentSong(Song song) {
		currentUser.addRecentSong(song);
		userAdapter.updateUser(currentUser);
	}


	private void initializeAdapters() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		songAdapter = factoria.getSongDAO();
		userAdapter = factoria.getUserDAO();
	}

	private void initializeRepos() {
		songRepo = SongRepo.getInstance();
		userRepo = UserRepo.getInstance();
	}

	@Override
	public void nuevasCanciones(EventObject arg0) {
		if (arg0 instanceof CancionesEvent) {
			nuevasCanciones = ((CancionesEvent) arg0).getNuevasCanciones();
		}
	}
	
	public void setSongsFile(String path) {
		cargadorCanciones.setArchivoCanciones(path);
	}
	
	private void initializeDiscounts() {
		activeDiscounts = new ArrayList<Discount>();
		activeDiscounts.add(new EducationDiscount());
		activeDiscounts.add(new YouthDiscount());
		activeDiscounts.add(new ElderDiscount());
	}
	
	public Optional<Discount> getDiscount() {
		return activeDiscounts.stream()
				.filter(d -> d.isApplicable(currentUser))
				.sorted(Comparator.comparing(Discount::getDiscount).reversed())
				.findFirst();
	}

}
