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
	
	public List<Song> getAllSongs() {
		ArrayList<Song> allSongs = new ArrayList<Song>();
		for(Song song : songs.values()) {
			allSongs.add(song);
		}
		return allSongs;
	}
	
	public ArrayList<Song> initializeSongs() {
		File songsFolder = new File(SONGS_PATH);
		ArrayList<Song> songs = new ArrayList<Song>();
		for(File genre : songsFolder.listFiles()) {
			for(File curSong : genre.listFiles()) {
				String path = curSong.getPath().replaceAll(SONGS_PATH + "/", "");
				Song song = new Song(path);
				songs.add(song);
			}
		}
		return songs;
	}
	
	public List<Song> filterSongs(String artist, String title, String genre) {
		ArrayList<Song> songs = (ArrayList<Song>) getAllSongs();
		if (!artist.isEmpty()) {
			songs = (ArrayList<Song>) songs.stream()
					.filter(s -> s.getArtists().contains(artist))
			.collect(Collectors.toList());
		}
		if (!title.isEmpty()) {
			songs = (ArrayList<Song>) songs.stream()
					.filter(s -> s.getTitle().contains(title))
					.collect(Collectors.toList());
		}
		if (!genre.isEmpty()) {
			songs = (ArrayList<Song>) songs.stream()
					.filter(s -> s.getGenre().equals(genre))
					.collect(Collectors.toList());
		}
		return songs;
		
	}
	
	private void loadRepo() throws DAOException {
		List<Song> songsDB = songAdapter.getAllSongs();
		for (Song song: songsDB)
			songs.put(song.getId(), song);
	 }

	// TODO: Complete functionality.
	public List<Song> getRecentSongs() {
		// ArrayList<Song> songs = (ArrayList<Song>) getAllSongs();
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

	public ArrayList<String> getGenres() {
		ArrayList<Song> songs = (ArrayList<Song>) getAllSongs();
		return (ArrayList<String>) songs.stream()
				.map(s -> s.getGenre())
				.distinct()
				.collect(Collectors.toList());
	}
}
