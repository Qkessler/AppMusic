package umu.tds.apps.persistence;

import java.util.List;
import java.util.Optional;

import umu.tds.apps.models.Song;

public interface ISongAdapterDAO {
	public void registerSong(Song song);
	public void registerSong(String path);
	public void removeSong(Song song);
	public Song getSong(int id);
	public Optional<Song> getSong(String path);
	public List<Song> getAllSongs();
	public void cleanDB();
}
