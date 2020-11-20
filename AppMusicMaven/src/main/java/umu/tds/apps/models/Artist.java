package umu.tds.apps.models;

import java.util.List;

public class Artist {
	private String name;
	
	public Artist(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Song> getSongs() {
		// Maybe go through the SongRepo checking?
		return null;
	}
}
