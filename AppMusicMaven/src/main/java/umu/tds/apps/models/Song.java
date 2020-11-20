package umu.tds.apps.models;

import java.util.LinkedList;

public class Song {
	private String title;
	private LinkedList<Artist> artists;
	private String genre; // We only need the name, instead of an object, we are using a property.
	private Long playCount;

	public Song(String title, LinkedList<Artist> artists, String genre) {
		this.title = title;
		this.genre = genre;
		this.artists = artists;
		playCount = (long) 0;
	}
	
	// Hypothetic playSong -> playCount++;

	public String getTitle() {
		return title;
	}

	public LinkedList<Artist> getArtists() {
		return artists;
	}

	public String getGenre() {
		return genre;
	}
	
	/*
	public String getPath() {
		return ""
	}
	*/
}
