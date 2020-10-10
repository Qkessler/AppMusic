package umu.tds.business;

import java.util.LinkedList;

public class Song {
	private String title;
	private LinkedList<String> artists;
	private String genre;
	
	public Song(String title, LinkedList<String> artists, String genre) {
		this.title = title;
		this.genre = genre;
		this.artists = artists;
	}
	
	public String getTitle() {
		return title;
	}
	public LinkedList<String> getArtists() {
		return artists;
	}
	public String getGenre() {
		return genre;
	}
	
	/*
	 	public String getPath() {
			StringBuilder path = new StringBuilder();
			
		
		}
	*/

}
