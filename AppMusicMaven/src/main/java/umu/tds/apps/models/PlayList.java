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
	
	public void addSong(Song song) {
		songs.add(song);
	}
	
	public List<Song> getSongs() {
		// Creating a new copy, we reduce the possibility of corruption in our songs.
		return new ArrayList<Song>(songs);
	}

	public int getId() {
		return id;
	}
}
