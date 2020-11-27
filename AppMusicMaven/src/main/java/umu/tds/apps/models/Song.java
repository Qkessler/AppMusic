package umu.tds.apps.models;

import java.util.LinkedList;

public class Song {
	private int id;
	private String title;
	private LinkedList<Artist> artists;
	private String genre; // We only need the name, instead of an object, we are using a property.
	private Long playCount;
	
	public Song(String title, LinkedList<Artist> artists, String genre) {
		this(title, artists, genre, (long) 0);
	}
	
	public Song(String title, LinkedList<Artist> artists, String genre, Long playCount) {
		this.title = title;
		this.genre = genre;
		this.artists = artists;
		this.playCount = playCount;
		this.id = 0;
	}
	
	public int getId() {
		return id;
	}
	
	// Used by the registerSong function in SongAdapterTDS.
	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public LinkedList<Artist> getArtists() {
		return new LinkedList<Artist>(artists);
	}

	public String getGenre() {
		return genre;
	}
	
	public Long getPlayCount() {
		return playCount;
	}
	
	/*
	public String getPath() {
		return ""
	}
	*/
}
