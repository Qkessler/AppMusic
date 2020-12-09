package umu.tds.apps.persistence;

import java.util.List;
import umu.tds.apps.models.PlayList;
import umu.tds.apps.models.Song;

public interface IPlayListAdapterDAO {
	public void registerPlayList(PlayList playlist);
	public void removePlayList(PlayList playlist);
	public PlayList getPlayList(int id);
	public void addSongPlayList(PlayList playlist, Song song);
	public void removeSongPlayList();
	public List<PlayList> getAllPlaylists();
	
}
