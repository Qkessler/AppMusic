package umu.tds.apps.models;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import umu.tds.apps.persistence.DAOException;
import umu.tds.apps.persistence.FactoriaDAO;

public class PlayListRepo {
	private static PlayListRepo instance;
	private FactoriaDAO dao;

	private HashMap<Integer, PlayList> playlistsId;
	private HashMap<String, PlayList> playlistsName;
	
	
	public static PlayListRepo getInstance() {
		if (instance == null) instance = new PlayListRepo();
		return instance;
	}
	
	private PlayListRepo() {
		playlistsId = new HashMap<Integer, PlayList>();
		playlistsName = new HashMap<String, PlayList>();
		try {
			dao = FactoriaDAO.getInstancia();
			
			List<PlayList> playlists = dao.getPlayListDAO().getAllPlaylists();
			for (PlayList pl : playlists) {
				playlistsId.put(pl.getId(), pl);
				playlistsName.put(pl.getName(), pl);
			}
		} catch (DAOException eDAO) {
			   eDAO.printStackTrace();
		}
	}
	
	public void addPlayList(PlayList playlist) {
		playlistsId.put(playlist.getId(), playlist);
		playlistsName.put(playlist.getName(), playlist);
	}
	
	public void removePlayList(PlayList playlist) {
		playlistsId.remove(playlist.getId());
		playlistsName.remove(playlist.getName());
	}
	
	public PlayList getPlayList(String name) {
		return playlistsName.get(name);
	}
	
	public PlayList getPlayList(int id) {
		return playlistsId.get(id);
	}
	
	public List<PlayList> getAllPlayLists(){
		return new LinkedList<PlayList>(playlistsName.values());
	}
}
