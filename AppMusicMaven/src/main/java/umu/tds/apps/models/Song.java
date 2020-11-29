package umu.tds.apps.models;

import java.util.ArrayList;

public class Song {
	private int id;
	private String title;
	private ArrayList<Artist> artists;
	private String genre; // We only need the name, instead of an object, we are using a property.
	private Long playCount;
	
	public Song(String title, ArrayList<Artist> artists, String genre) {
		this(title, artists, genre, (long) 0);
	}
	
	public Song(String title, ArrayList<Artist> artists, String genre, Long playCount) {
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

	public String getArtists() {
		String artistsString = "";
		for(int i = 0; i < artists.size(); i++) {
			Artist curArtist = artists.get(i);
			artistsString += curArtist.getName();
			if (i < artists.size() - 1) {
				artistsString += " & ";
			}
		}
		return artistsString;
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
