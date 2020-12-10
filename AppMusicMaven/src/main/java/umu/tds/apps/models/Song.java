package umu.tds.apps.models;

import java.util.ArrayList;
import java.util.List;

public class Song {
	private int id;
	private String title;
	private ArrayList<Artist> artists;
	private String genre; // We only need the name, instead of an object, we are using a property.
	private Long playCount;
	private String separator;
	private String path;
	

	public Song(String title, ArrayList<Artist> artists, String genre, String path) {
		this(title, artists, genre, path, (long) 0);
	}
	
	public Song(String title, ArrayList<Artist> artists, String genre, String path, Long playCount) {
		separator = setSeparator();
		this.path = path;
		this.title = title;
		this.genre = genre;
		this.artists = artists;
		this.playCount = playCount;
		this.id = 0;
	}
	
	private String setSeparator() {				// los separadaores en windows son diferentes que en linux y mac
		if (System.getProperty("os.name").startsWith("Windows"))
			return "\\";
		else 
			return "/";			
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
				artistsString += "&";
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
	
	// Paths follow the following format:
	// "JAZZ/nina Simone-Fly Me To The Moon.mp3"
	public String getPath() {
//		String path = "";
//		path += this.genre + separator;
//		path += getArtists() + "-";
//		path += this.title + ".mp3";
		return path;
	}
	
	
	public static List<Artist> parseArtists(String artistNames) {
		// Removing all spaces to be able to split the names correctly.
		artistNames.replaceAll("\\s+","");
		ArrayList<Artist> artists = new ArrayList<>();
		String[] artistsSplitted = artistNames.split("&");
		if (artistsSplitted.length == 0) {
			artists.add(new Artist(artistNames));
			return artists;
		}
		for (String name : artistNames.split("&")) {
			artists.add(new Artist(name));
		}
		return artists;
	}

	@Override
	public String toString() {
		return "Song [id=" + id + ", title=" + title + ", artists=" + artists + ", genre=" + genre + ", playCount="
				+ playCount + "]";
	}
}
