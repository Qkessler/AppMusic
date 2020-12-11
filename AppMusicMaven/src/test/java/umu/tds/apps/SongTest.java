package umu.tds.apps;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import umu.tds.apps.models.Artist;
import umu.tds.apps.models.Song;

public class SongTest {
	private final static String TITLE = "Test Song";
	private final static String GENRE = "TEST";
	private final static String PATH = "Test path";

	private ArrayList<Artist> artists;
	private Song song;

	@Before
	public void setUp() {
		artists = new ArrayList<>();
		artists.add(new Artist("Test artist 1"));
		song = new Song(TITLE, artists, GENRE, PATH);
	}

	@Test
	public void testConstructor() {
		// Testing the constructor without giving the playCount number.
		Song testSong = new Song(TITLE, artists, GENRE, PATH);
		assertEquals(song, testSong);
	}

	@Test
	public void testConstructorPlayCount() {
		// Testing the constructor giving the playCount number.
		Song testSong = new Song(TITLE, artists, GENRE, PATH, (long) 0);
		assertEquals(song, testSong);
	}

	@Test
	public void testGetIdInitial() {
		int initial = 0;
		assertEquals(song.getId(), initial);
	}

	@Test
	public void testGetIdChanged() {
		// In the same test we are checking the getId after a change and
		// the setId method.
		int changed = 1;
		song.setId(changed);
		assertEquals(song.getId(), changed);
	}

	@Test
	public void testGetTitle() {
		assertEquals(song.getTitle(), TITLE);
	}

	@Test
	public void testGetArtistsUnique() {
		String expected = "Test artist 1";
		assertEquals(song.getArtists(), expected);
	}

	@Test
	public void testGetArtistsMultiple() {
		// In this test we are adding another artists, with the intention of testing
		// the inner condition.
		String expected = "Test artist 1&Test artist 2";
		artists.add(new Artist("Test artist 2"));
		song = new Song(TITLE, artists, GENRE, PATH);
		assertEquals(song.getArtists(), expected);
	}

	@Test
	public void testGetGenre() {
		assertEquals(song.getGenre(), GENRE);
	}

	@Test
	public void testGetPlayCount() {
		Long initial = (long) 0;
		assertEquals(song.getPlayCount(), initial);
	}

	@Test
	public void testGetPlayCountPlayed() {
		// Testing getPlayCount and playSong at the same time.
		Long expected = (long) 1;
		song.playSong();
		assertEquals(song.getPlayCount(), expected);
	}

	@Test
	public void testGetPath() {
		assertEquals(song.getPath(), PATH);
	}

	@Test
	public void testParseArtistsUnique() {
		String artistsString = "Test artist 1";
		assertEquals(Song.parseArtists(artistsString), artists);
	}

	@Test
	public void testParseArtistsMultiple() {
		String artistsString = "Test artist 1&Test artist 2";
		artists.add(new Artist("Test artist 2"));
		assertEquals(Song.parseArtists(artistsString), artists);
	}

	@Test
	public void testToString() {
		String expected = "Song [id=0, title=" + TITLE + ", artists=" + artists + ", genre=" + GENRE + ", playCount="
				+ 0 + "]";
		assertEquals(song.toString(), expected);
	}

}
