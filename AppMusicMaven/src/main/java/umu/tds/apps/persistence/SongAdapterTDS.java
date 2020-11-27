package umu.tds.apps.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.apps.models.Artist;
import umu.tds.apps.models.Song;

public class SongAdapterTDS implements ISongAdapterDAO{
	
	private static SongAdapterTDS instance;
	private ServicioPersistencia servicioPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	
	private static final String SONG = "song";
	private static final String TITLE = "title";
	private static final String ARTISTS = "artists";
	private static final String GENRE = "genre";
	private static final String PLAY_COUNT = "play_count";
	
	public static SongAdapterTDS getInstance() {
		if (instance == null) {
			return new SongAdapterTDS();
		}
		return instance;
	}
	
	public void registerSong(Song song) {
		boolean registered = false;
		try {
			servicioPersistencia.recuperarEntidad(song.getId()); 
		} catch(NullPointerException e) {
			registered = true;
		}
		if (registered) return;
		Entidad eSong = new Entidad();
		eSong.setNombre(SONG);
		eSong.setPropiedades(
				new ArrayList<Propiedad>(Arrays.asList(
						new Propiedad(TITLE, song.getTitle()),
						new Propiedad(ARTISTS, getArtists(song.getArtists())),
						new Propiedad(GENRE, song.getGenre()),
						new Propiedad(PLAY_COUNT, Long.toString(song.getPlayCount()))
				)));
		eSong = servicioPersistencia.registrarEntidad(eSong);
		song.setId(eSong.getId());
		
	}
	
	public void removeSong(Song song) {
		boolean removable = false;
		Entidad eSong = null;
		try {
			eSong = servicioPersistencia.recuperarEntidad(song.getId());
		} catch (NullPointerException e) {
			removable = true;
		}
		if (!removable) return;
		servicioPersistencia.borrarEntidad(eSong);
	}
	
	
	public Song getSong(int id) {
		Entidad eSong = servicioPersistencia.recuperarEntidad(id);
		String title = servicioPersistencia.recuperarPropiedadEntidad(eSong, TITLE);
		String artistNames = servicioPersistencia.recuperarPropiedadEntidad(eSong, ARTISTS);
		String genre = servicioPersistencia.recuperarPropiedadEntidad(eSong, GENRE);
		String playCount = servicioPersistencia.recuperarPropiedadEntidad(eSong, PLAY_COUNT);
		
		LinkedList<Artist> artists = new LinkedList<>();
		for (String name : artistNames.split(",")) {
			artists.add(new Artist(name));
		}
		return new Song(title, artists, genre, Long.parseLong(playCount));
	}
	
	public List<Song> getAllSongs() {
		ArrayList<Entidad> eSongs = servicioPersistencia.recuperarEntidades(SONG);
		List<Song> songs = eSongs.stream()
				.map(e -> getSong(e.getId()))
				.collect(Collectors.toList());
		return songs;
	}
	private String getArtists(List<Artist> artists) {
		String names = "";
		for(Artist artist : artists) {
			names += artist.getName() + ",";
		}
		return names;
	}
}
