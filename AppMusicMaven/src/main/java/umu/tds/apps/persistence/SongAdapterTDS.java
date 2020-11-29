package umu.tds.apps.persistence;

import java.util.ArrayList;
import java.util.Arrays;
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
	private static final String PATH = "path";
	
	public static SongAdapterTDS getInstance() {
		if (instance == null) {
			return new SongAdapterTDS();
		}
		return instance;
	}
	
	public void registerSong(Song song) {
		if (isRegistered(song.getId())) return;
		Entidad eSong = new Entidad();
		eSong.setNombre(SONG);
		eSong.setPropiedades(
				new ArrayList<Propiedad>(Arrays.asList(
						new Propiedad(TITLE, song.getTitle()),
						new Propiedad(ARTISTS, song.getArtists()),
						new Propiedad(GENRE, song.getGenre()),
						new Propiedad(PLAY_COUNT, Long.toString(song.getPlayCount()))
				)));
		eSong = servicioPersistencia.registrarEntidad(eSong);
		song.setId(eSong.getId());
		
	}
	
	public void removeSong(Song song) {
		if (isRegistered(song.getId())) {
			Entidad eSong = servicioPersistencia.recuperarEntidad(song.getId());
			servicioPersistencia.borrarEntidad(eSong);
		}
	}
	
	private boolean isRegistered(int id) {
		try {
			servicioPersistencia.recuperarEntidad(id); 
		} catch(NullPointerException e) {
			return false;
		}
		return true;
	}
	
	public Song getSong(int id) {
		Entidad eSong = servicioPersistencia.recuperarEntidad(id);
		String title = servicioPersistencia.recuperarPropiedadEntidad(eSong, TITLE);
		String artistNames = servicioPersistencia.recuperarPropiedadEntidad(eSong, ARTISTS);
		String genre = servicioPersistencia.recuperarPropiedadEntidad(eSong, GENRE);
		String playCount = servicioPersistencia.recuperarPropiedadEntidad(eSong, PLAY_COUNT);
		
		ArrayList<Artist> artists = (ArrayList<Artist>) Song.parseArtists(artistNames);
		return new Song(title, artists, genre, Long.parseLong(playCount));
	}
	
	public List<Song> getAllSongs() {
		ArrayList<Entidad> eSongs = servicioPersistencia.recuperarEntidades(SONG);
		List<Song> songs = eSongs.stream()
				.map(e -> getSong(e.getId()))
				.collect(Collectors.toList());
		return songs;
	}
}
