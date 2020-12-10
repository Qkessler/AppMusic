package umu.tds.apps.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.apps.models.PlayList;

public class PlayListAdapterTDS implements IPlayListAdapterDAO {

	private static PlayListAdapterTDS instance;
	private ServicioPersistencia servicioPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	
	private static final String PLAYLIST = "playlist";
	private static final String NAME = "name";
	private static final String SONGS = "songs";
	
	public static PlayListAdapterTDS getInstance() {
		if (instance == null) 
			instance = new PlayListAdapterTDS();
		return instance;
	}
	
	@Override
	public void registerPlayList(PlayList playlist) {
		if (isRegistered(playlist.getId())) return;
		Entidad ePlayList = new Entidad();
		ePlayList.setNombre(PLAYLIST);
		ePlayList.setPropiedades(
				new ArrayList<Propiedad>(Arrays.asList(
						new Propiedad(NAME, playlist.getName()),
						new Propiedad(SONGS, playlist.songsToString())
				)));
		ePlayList = servicioPersistencia.registrarEntidad(ePlayList);
		playlist.setId(ePlayList.getId());
	}

	private boolean isRegistered(int id) {
		try {
			servicioPersistencia.recuperarEntidad(id); 
		} catch(NullPointerException e) {
			return false;
		}
		return true;
	}
	
	@Override
	public void removePlayList(PlayList playlist) {
		if (isRegistered(playlist.getId())) {
			Entidad ePlaylist = servicioPersistencia.recuperarEntidad(playlist.getId());
			servicioPersistencia.borrarEntidad(ePlaylist);
		}
	}

	@Override
	public PlayList getPlayList(int id) {
		Entidad ePlayList = servicioPersistencia.recuperarEntidad(id);
		String name = servicioPersistencia.recuperarPropiedadEntidad(ePlayList, NAME);
		String songsIDs = servicioPersistencia.recuperarPropiedadEntidad(ePlayList, SONGS);
		
		PlayList playlist = new PlayList(name);
		playlist.setId(id);
		String[] songs = songsIDs.split(" ");
		//TODO devuelve la lista vacía, hay que llenarla con sus canciones
		for (String songID : songs) {
			playlist.addSong(SongAdapterTDS.getInstance().getSong(Integer.parseInt(songID)));
		}
		
		return playlist;
	}

	@Override
	public void updatePlayList(PlayList playlist) {		// tanto para cuando se añade o se quita una cancion de la playlist
		Entidad ePlayList = servicioPersistencia.recuperarEntidad(playlist.getId());
		//ePlayList.
	}

	@Override
	public List<PlayList> getAllPlaylists() {
		// TODO Auto-generated method stub
		return null;
	}


}
