package umu.tds.apps.models;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import umu.tds.apps.persistence.DAOException;
import umu.tds.apps.persistence.FactoriaDAO;
import umu.tds.apps.persistence.ISongAdapterDAO;

public class SongRepo {
	private static final String SONGS_PATH = System.getProperty("user.dir") + "/canciones"; 
	
	private Map<Integer, Song> songs;
	private static SongRepo instance = new SongRepo();
	
	private FactoriaDAO dao;
	private ISongAdapterDAO songAdapter;
	
	private SongRepo() {
		try {
			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
			songAdapter = dao.getSongDAO();
			songs = new HashMap<Integer, Song>();
			this.loadRepo();
		} catch (DAOException eDAO) {
			eDAO.printStackTrace();
		}
	}
	
	public static SongRepo getInstance() {
		return instance;
	}
	
	public void addSong(Song song) {
		songs.put(song.getId(), song);
	}
	
	public void removeSong(Song song) {
		songs.remove(song.getId());
	}
	
	public Song getSong(int id) {
		return songs.get(id);
	}
	
	public Optional<Song> getSong(String path) {
		ArrayList<Song> allSongs = (ArrayList<Song>) getAllSongs();
		return allSongs.stream()
		.filter(s -> s.getPath().equals(path))
		.findAny();
	}
	
	public List<Song> getAllSongs() {
		ArrayList<Song> allSongs = new ArrayList<Song>();
		for(Song song : songs.values()) {
			allSongs.add(song);
		}
		return allSongs;
	}
	
	public void initializeSongs() {
		File songsFolder = new File(SONGS_PATH);
		for(File file : songsFolder.listFiles()) {
			
		}
	}
	
	
	public List<Song> filterSongs(String artist, String title, String genre) {
		// TODO: Check again how to pass null filters.
		ArrayList<Song> songs = (ArrayList<Song>) getAllSongs();
		return songs.stream()
		.filter(song -> song.getArtists().contains(artist))
		.filter(song -> song.getTitle().contains(title))
		.filter(song -> song.getGenre().equals(genre))
		.collect(Collectors.toList());
		
	}
	
	private void loadRepo() throws DAOException {
		List<Song> songsDB = songAdapter.getAllSongs();
		for (Song song: songsDB)
			songs.put(song.getId(), song);
	 }

	// TODO: Complete functionality.
	public List<Song> getRecentSongs() {
		// ArrayList<Song> songs = (ArrayList<Song>) getAllSongs();
		System.out.println(System.getProperty("user.dir"));
		ArrayList<Song> songs = new ArrayList<Song>();
		ArrayList<Artist> artists = new ArrayList<>();
		artists.add(new Artist("Javi"));
		artists.add(new Artist("Quique"));
		songs.add(new Song("title1", artists, "POP"));
		songs.add(new Song("title2", artists, "JAZZ"));
		songs.add(new Song("title3", artists, "POP"));
		songs.add(new Song("death title", artists, "METAL"));
		return songs;
	}
}
