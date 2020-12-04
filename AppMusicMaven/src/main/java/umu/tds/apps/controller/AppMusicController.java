package umu.tds.apps.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import umu.tds.apps.models.Song;
import umu.tds.apps.models.SongRepo;
import umu.tds.apps.models.User;
import umu.tds.apps.models.UserRepo;
import umu.tds.apps.persistence.DAOException;
import umu.tds.apps.persistence.FactoriaDAO;
import umu.tds.apps.persistence.ISongAdapterDAO;
import umu.tds.apps.persistence.IUserAdapterDAO;

public class AppMusicController {

	private static AppMusicController instance = null;
	private MediaPlayer mediaPlayer;

	private ISongAdapterDAO songAdapter;
	private IUserAdapterDAO userAdapter;
	// private IPlayListAdapterDAO playListAdapter;

	private UserRepo userRepo;
	private SongRepo songRepo;
	// private PlayListRepo playListRepo;
	
	private User currentUser;

	private AppMusicController() {
		this.currentUser= null;
		try {
			com.sun.javafx.application.PlatformImpl.startup(()->{});
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Exception: " + ex.getMessage());
		}
		initializeAdapters();
		initializeRepos();
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
	
	public void registerSong(String path) {
		songAdapter.registerSong(path);
		songRepo.addSong(new Song(path));
	}
	
	public ArrayList<Song> getRecentSongs() {
		return (ArrayList<Song>) songRepo.getRecentSongs();
	}

	// Get the songs that are on the "canciones" directory that
	// were not already persistent.
	public void initializeSongs() {
		ArrayList<Song> songs = songRepo.initializeSongs();
		for(Song song : songs) {
			registerSong(song.getPath());
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
//		path = SongRepo.SONGS_PATH + "/" + path;
		path = SongRepo.SONGS_PATH + "/" + "Los Secretos - La Chica De Ayer.wav";
		if (mediaPlayer == null) {
			File f = new File(path);
//		System.out.println(path + ": " + f.exists());
			Media hit = new Media(f.toURI().toString());
			mediaPlayer = new MediaPlayer(hit);
		}
		mediaPlayer.play();
	}
	
	public void pauseSong() {
//		path = SongRepo.SONGS_PATH + "/" + path;
//		File f = new File(path);
//		Media hit = new Media(f.toURI().toString());
//		mediaPlayer = new MediaPlayer(hit);
		mediaPlayer.pause();
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

}
