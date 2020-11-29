package umu.tds.apps.models;

import java.util.ArrayList;
import java.util.List;

public class Song {
	private int id;
	private String title;
	private ArrayList<Artist> artists;
	private String genre; // We only need the name, instead of an object, we are using a property.
	private Long playCount;
	
	// Paths follow the following format:
	// "JAZZ/nina Simone-Fly Me To The Moon.mp3"
	public Song(String path) {
		String[] stringList = path.split("-");
		String name = stringList[1].split("\\.")[0]; // Holds "Fly Me to The Moon"
		String genreArtists = stringList[0]; // Holds "JAZZ/nina Simone"
		String[] genreArtistsArray = genreArtists.split("/");
		String artists = genreArtistsArray[1]; // Holds "nina Simone"
		this.id = 0;
		this.genre = genreArtistsArray[0]; // Holds "JAZZ"
		this.artists = (ArrayList<Artist>) parseArtists(artists);
		this.title = name;
		this.playCount = (long) 0;
	}
	
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
	
	// Paths follow the following format:
	// "JAZZ/nina Simone-Fly Me To The Moon.mp3"
	public String getPath() {
		String path = "";
		path += this.genre + "/";
		path += getArtists() + "-";
		path += this.title + ".mp3";
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
