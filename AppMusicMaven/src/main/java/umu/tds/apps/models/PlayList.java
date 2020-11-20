package umu.tds.apps.models;

import java.util.LinkedList;
import java.util.List;

public class PlayList {
	private String name;
	private List<Song> songs;
	
	public PlayList(String name) {
		this.name = name;
		songs = new LinkedList<Song>(); // Check whether LinkedList is good here.
	}
	
	public String getName() {
		return name;
	}
	
	public void addSong(Song song) {
		songs.add(song);
	}
	
	public List<Song> getSongs() {
		// Creating a new copy, we reduce the possibility of corruption in our songs.
		return new LinkedList<Song>(songs);
	}
}
