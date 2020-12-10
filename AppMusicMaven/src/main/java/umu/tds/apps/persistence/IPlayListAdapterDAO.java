package umu.tds.apps.persistence;

import java.util.List;
import umu.tds.apps.models.PlayList;

public interface IPlayListAdapterDAO {
	public void registerPlayList(PlayList playlist);
	public void removePlayList(PlayList playlist);
	public PlayList getPlayList(int id);
	public void updatePlayList(PlayList playlist);
	public List<PlayList> getAllPlaylists();
	
}
