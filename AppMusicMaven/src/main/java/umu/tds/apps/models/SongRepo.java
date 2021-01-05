package umu.tds.apps.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import umu.tds.apps.persistence.DAOException;
import umu.tds.apps.persistence.FactoriaDAO;
import umu.tds.apps.persistence.ISongAdapterDAO;
import umu.tds.componente.Cancion;
import umu.tds.componente.Canciones;

public class SongRepo {

	private String songsPath;
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
	
	public String getSongsPath() {
		return songsPath;
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
	
	public List<Song> getSongsFromIds(List<String> ids) {
		if (ids.isEmpty()) return new LinkedList<Song>();
		List<Song> songs = new LinkedList<Song>();
		ids.stream()
			.map(s -> Integer.parseInt(s))
			.forEach(i -> songs.add(getSong(i)));
		return songs;
	}
	
	public List<Song> initializeSongs(Canciones canciones) {
		ArrayList<Song> songs = new ArrayList<Song>();
		for(Cancion cancion : canciones.getCancion()) {
			String path = cancion.getURL();
			String title = cancion.getTitulo();
			String genre = cancion.getEstilo();
			ArrayList<Artist> artists = (ArrayList<Artist>) Song.parseArtists(cancion.getInterprete()); 
			Song song = new Song(title, artists, genre, path);
			songs.add(song);
		}
		return songs;
	}
	
	public List<Song> filterSongs(String artist, String title, String genre) {
		ArrayList<Song> songs = (ArrayList<Song>) getAllSongs();
		if (!artist.isEmpty()) {
			songs = (ArrayList<Song>) songs.stream()
					.filter(s -> {
						String artistsLower = s.getArtists().toLowerCase();
					return artistsLower.contains(artist.toLowerCase());	
					})
			.collect(Collectors.toList());
		}
		if (!title.isEmpty()) {
			songs = (ArrayList<Song>) songs.stream()
					.filter(s -> {
						String titleLower = s.getTitle().toLowerCase();
						return titleLower.contains(title.toLowerCase());
					})
					.collect(Collectors.toList());
		}
		if (!genre.isEmpty()) {
			songs = (ArrayList<Song>) songs.stream()
					.filter(s -> {
						String genreLower = s.getGenre().toLowerCase();
						return genreLower.contains(genre.toLowerCase());
					})
					.collect(Collectors.toList());
		}
		return songs;
		
	}
	
	private void loadRepo() throws DAOException {
		List<Song> songsDB = songAdapter.getAllSongs();
		for (Song song: songsDB)
			songs.put(song.getId(), song);
	 }

	public ArrayList<String> getGenres() {
		ArrayList<Song> songs = (ArrayList<Song>) getAllSongs();
		return (ArrayList<String>) songs.stream()
				.map(s -> s.getGenre())
				.distinct()
				.collect(Collectors.toList());
	}
}
