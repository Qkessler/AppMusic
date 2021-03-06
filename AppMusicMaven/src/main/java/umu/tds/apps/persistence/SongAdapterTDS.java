package umu.tds.apps.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.apps.models.Artist;
import umu.tds.apps.models.Song;

public class SongAdapterTDS implements ISongAdapterDAO {

	private static SongAdapterTDS instance;
	private ServicioPersistencia servicioPersistencia = FactoriaServicioPersistencia.getInstance()
			.getServicioPersistencia();

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
		if (isRegistered(song.getId()) || isRegistered(song.getPath()))
			return;
		Entidad eSong = new Entidad();
		eSong.setNombre(SONG);
		eSong.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad(TITLE, song.getTitle()),
				new Propiedad(ARTISTS, song.getArtists()), new Propiedad(GENRE, song.getGenre()),
				new Propiedad(PLAY_COUNT, Long.toString(song.getPlayCount())), new Propiedad(PATH, song.getPath()))));
		eSong = servicioPersistencia.registrarEntidad(eSong);
		song.setId(eSong.getId());
	}

	public void addPlayCount(Song song) {
		Entidad eSong = servicioPersistencia.recuperarEntidad(song.getId());
		for (Propiedad prop : eSong.getPropiedades())
			if (prop.getNombre().equals(PLAY_COUNT)) {
				prop.setValor(String.valueOf(song.getPlayCount()));
				servicioPersistencia.modificarPropiedad(prop);
			}
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
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}
	
	private boolean isRegistered(String path) {
		return getSong(path).isPresent();
	}

	public Song getSong(int id) {
		Entidad eSong = servicioPersistencia.recuperarEntidad(id);
		String title = servicioPersistencia.recuperarPropiedadEntidad(eSong, TITLE);
		String artistNames = servicioPersistencia.recuperarPropiedadEntidad(eSong, ARTISTS);
		String genre = servicioPersistencia.recuperarPropiedadEntidad(eSong, GENRE);
		String playCount = servicioPersistencia.recuperarPropiedadEntidad(eSong, PLAY_COUNT);
		String path = servicioPersistencia.recuperarPropiedadEntidad(eSong, PATH);

		ArrayList<Artist> artists = (ArrayList<Artist>) Song.parseArtists(artistNames);
		Song song = new Song(title, artists, genre, path, Long.parseLong(playCount));
		song.setId(id);
		return song;
	}

	public List<Song> getAllSongs() {
		ArrayList<Entidad> eSongs = servicioPersistencia.recuperarEntidades(SONG);
		List<Song> songs = eSongs.stream().map(e -> getSong(e.getId())).collect(Collectors.toList());
		return songs;
	}

	public Optional<Song> getSong(String path) {
		ArrayList<Song> allSongs = (ArrayList<Song>) getAllSongs();
		return allSongs.stream().filter(s -> s.getPath().equals(path)).findAny();
	}

	public void cleanDB() {
		ArrayList<Entidad> entities = servicioPersistencia.recuperarEntidades();
		for (Entidad e : entities) {
			servicioPersistencia.borrarEntidad(e);
		}
	}
}
