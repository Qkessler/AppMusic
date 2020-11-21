package umu.tds.apps.persistence;

import java.util.List;

import umu.tds.apps.models.Song;

public interface ISongAdapterDAO {
	public void registerSong(Song song);
	public void removeSong(Song song);
	public void modifySong(Song song);
	public void getSong(Song song);
	public List<Song> getAllSongs();
}
