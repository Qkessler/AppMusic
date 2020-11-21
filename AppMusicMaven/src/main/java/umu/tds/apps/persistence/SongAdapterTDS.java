package umu.tds.apps.persistence;

import java.util.ArrayList;
import java.util.List;

import umu.tds.apps.models.Song;

public class SongAdapterTDS implements ISongAdapterDAO{
	
	private static SongAdapterTDS instance;
	
	public static SongAdapterTDS getInstance() {
		if (instance == null) {
			return new SongAdapterTDS();
		}
		return instance;
	}
	
	public void registerSong(Song song) {}
	public void removeSong(Song song) {}
	public void modifySong(Song song) {}
	public void getSong(Song song) {}
	public List<Song> getAllSongs() {
		// Change implementation.
		return new ArrayList<Song>();
	}
}
