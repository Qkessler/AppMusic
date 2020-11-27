package umu.tds.apps.persistence;

import java.util.List;

import umu.tds.apps.models.Song;

public interface ISongAdapterDAO {
	public void registerSong(Song song);
	public void removeSong(Song song);
	public Song getSong(int id);
	public List<Song> getAllSongs();
}
