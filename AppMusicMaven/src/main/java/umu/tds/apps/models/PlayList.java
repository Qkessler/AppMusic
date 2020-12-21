package umu.tds.apps.models;

import java.util.ArrayList;
import java.util.List;

public class PlayList {
	private String name;
	private List<Song> songs;
	private int id;
	
	public PlayList(String name) {
		this.name = name;
		this.id = 0;
		songs = new ArrayList<Song>(); // Check whether LinkedList is good here.
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Song> getSongs() {
		// Creating a new copy, we reduce the possibility of corruption in our songs.
		return new ArrayList<Song>(songs);
	}
	
	public String songsToString() {		// método para la construcción de propiedades del método DAO
		String songsString = "";
		for(Song song : songs) {
			songsString += song.getId() + " ";
		}
		return songsString;
	}

	public int getId() {
		return id;
	}
	
	// Used by the registerPlaylist
	public void setId(int id) {
		this.id = id;
	}

	public void addSong(Song song) {
		songs.add(song);
	}
	
	public boolean removeSong(Song song) {
		return songs.remove(song);
	}
	
	public void removeSong(int index) {
		songs.remove(index);
	}

}
