package umu.tds.apps.controller;

import java.time.LocalDate;
import java.util.LinkedList;

import umu.tds.apps.models.Artist;
import umu.tds.apps.models.PlayListRepo;
import umu.tds.apps.models.Song;
import umu.tds.apps.models.SongRepo;
import umu.tds.apps.models.User;
import umu.tds.apps.models.UserRepo;
import umu.tds.apps.persistence.DAOException;
import umu.tds.apps.persistence.FactoriaDAO;
import umu.tds.apps.persistence.ISongAdapterDAO;

public class AppMusicController {

	private static AppMusicController instance = null;

	private ISongAdapterDAO songAdapter;
	// private IPlayListAdapterDAO playListAdapter;
	// private IUserAdapterDAO userAdapter;

	private UserRepo userRepo;
	private SongRepo songRepo;
	private PlayListRepo playListRepo;

	private AppMusicController() {
		initializeAdapters();
		initializeRepos();
	}

	public static AppMusicController getInstance() {
		return instance == null ? new AppMusicController() : instance;
	}

	public void registerUser(String username, String password, String firstName, String lastName, String email,
			LocalDate birthDate) {
		User user = new User(username, password, firstName, lastName, email, birthDate);
		// register user on the userRepo, shouldn't really return it.
	}

	public void registerSong(String title, LinkedList<Artist> artists, String genre) {
		// Maybe check whether we have the same already in. Checking the title.
		Song song = new Song(title, artists, genre);
		songAdapter.registerSong(song);
		songRepo.addSong(song);
	}

	public void play() {
		// TODO: Create the play function.
	}

	// public PlayList createList() {
	// TODO: createList();
	// }

	public boolean login(String username, String password) {
		// UserDAO.find(username);
		/*
		 * Search for username in DB. If the user exists, we check if username.login =
		 * password.
		 */
		return false;
	}

	private void initializeAdapters() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		songAdapter = factoria.getSongDAO();
	}

	private void initializeRepos() {
		songRepo = SongRepo.getInstance();
	}
}
