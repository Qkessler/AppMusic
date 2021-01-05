package umu.tds.apps.persistence;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.apps.models.Song;
import umu.tds.apps.models.SongRepo;
import umu.tds.apps.models.User;

public class UserAdapterTDS implements IUserAdapterDAO {

	private static UserAdapterTDS instance;
	private static SimpleDateFormat dateFormat;
	private ServicioPersistencia servicioPersistencia = FactoriaServicioPersistencia.getInstance()
			.getServicioPersistencia();
	private SongRepo songRepo = SongRepo.getInstance();

	private static final String USER = "user";
	private static final String NAME = "name";
	private static final String LAST_NAME = "last_name";
	private static final String EMAIL = "email";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String BIRTH_DATE = "birth_date";
	private static final String RECENT_SONGS = "recent_songs";
	private static final String PLAYLISTS = "playlists";

	public static UserAdapterTDS getInstance() {
		dateFormat = new SimpleDateFormat("E MMMM dd k:m:s z yyyy");
		if (instance == null) {
			return new UserAdapterTDS();
		}
		return instance;
	}

	@Override
	public void registerUser(User user) {
		boolean registered = true;
		try {
			servicioPersistencia.recuperarEntidad(user.getId());
		} catch (NullPointerException e) {
			registered = false;
		}
		if (registered)
			return;
		Entidad eUser = new Entidad();
		eUser.setNombre(USER);
		eUser.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad(NAME, user.getName()),
				new Propiedad(LAST_NAME, user.getLastName()), new Propiedad(EMAIL, user.getEmail()),
				new Propiedad(USERNAME, user.getUsername()), new Propiedad(PASSWORD, user.getPassword()),
				new Propiedad(BIRTH_DATE, user.getBirthDate().toString()),
				new Propiedad(RECENT_SONGS, user.recentSongsToString()),
				new Propiedad(PLAYLISTS, user.playListsToString()))));
		eUser = servicioPersistencia.registrarEntidad(eUser);
		user.setId(eUser.getId());

	}

	@Override
	public void removeUser(User user) {
		boolean removable = true;
		Entidad eUser = null;
		try {
			eUser = servicioPersistencia.recuperarEntidad(user.getId());
		} catch (NullPointerException e) {
			removable = false;
		}
		if (!removable)
			return;
		servicioPersistencia.borrarEntidad(eUser);
	}

	@Override
	public void updateUser(User user) {
		Entidad eUser = servicioPersistencia.recuperarEntidad(user.getId());
		for (Propiedad prop : eUser.getPropiedades()) {
			switch (prop.getNombre()) {
			case NAME:
				prop.setValor(String.valueOf(user.getName()));
				break;
			case LAST_NAME:
				prop.setValor(String.valueOf(user.getLastName()));
				break;
			case EMAIL:
				prop.setValor(String.valueOf(user.getEmail()));
				break;
			case USERNAME:
				prop.setValor(String.valueOf(user.getUsername()));
				break;
			case PASSWORD:
				prop.setValor(String.valueOf(user.getPassword()));
				break;
			case BIRTH_DATE:
				prop.setValor(String.valueOf(user.getBirthDate()));
				break;
			case RECENT_SONGS:
				prop.setValor(String.valueOf(user.recentSongsToString()));
				break;
			case PLAYLISTS:
				prop.setValor(String.valueOf(user.playListsToString()));
			default:
				break;
			}
			servicioPersistencia.modificarPropiedad(prop);
		}
	}

	
	@Override
	public User getUser(int id) {
		Entidad eUser = servicioPersistencia.recuperarEntidad(id);
		String name = servicioPersistencia.recuperarPropiedadEntidad(eUser, NAME);
		String lastName = servicioPersistencia.recuperarPropiedadEntidad(eUser, LAST_NAME);
		String email = servicioPersistencia.recuperarPropiedadEntidad(eUser, EMAIL);
		String username = servicioPersistencia.recuperarPropiedadEntidad(eUser, USERNAME);
		String password = servicioPersistencia.recuperarPropiedadEntidad(eUser, PASSWORD);
		String birthDate = servicioPersistencia.recuperarPropiedadEntidad(eUser, BIRTH_DATE);
		String recentSongsString = servicioPersistencia.recuperarPropiedadEntidad(eUser, RECENT_SONGS);
		String playListsString = servicioPersistencia.recuperarPropiedadEntidad(eUser, PLAYLISTS);
		
		Date date = parseBirthDate(birthDate);
//		Date date = null;
//		try {
//			date = dateFormat.parse(birthDate);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		
		
		User user = new User(username, password, name, lastName, email, date);
		user.setId(id);

		if (recentSongsString.equals(""))
			user.setRecentSongs(new LinkedList<Song>());
		else {
			String[] songIds = recentSongsString.split(" ");
			List<String> sIds = new ArrayList<String>(Arrays.asList(songIds));
			LinkedList<Song> recentSongs = (LinkedList<Song>) songRepo.getSongsFromIds(sIds);
			user.setRecentSongs(recentSongs);
		}

//		String[] playListIds = playListsString.split(" ");
//		List<String> pIds = new ArrayList<String>(Arrays.asList(playListIds));
//		ArrayList<PlayList> playLists = (ArrayList<Song>) playListRepo.getPlayListsFromIds(pIds);
//		user.setPlaylists(playlists);

		return user;
	}
	
	// el formato de la fecha es tal que así: Thu Dec 10 16:39:00 CET 1750  -->  10/12/1750
	private Date parseBirthDate(String date) {
		String[] dateSplitted = date.split(" ");
		int year = Integer.parseInt(dateSplitted[5]) - 1900;
		int day = Integer.parseInt(dateSplitted[2]);
		int month;
		switch (dateSplitted[1]) {
		case "Jan":
			month = 0;
			break;
		case "Feb":
			month = 1;
			break;
		case "Mar":
			month = 2;
			break;
		case "Apr":
			month = 3;
			break;
		case "May":
			month = 4;
			break;
		case "Jun":
			month = 5;
			break;
		case "Jul":
			month = 6;
			break;
		case "Aug":
			month = 7;
			break;
		case "Sep":
			month = 8;
			break;
		case "Oct":
			month = 9;
			break;
		case "Nov":
			month = 10;
			break;
		case "Dec":
			month = 11;
			break;
		default:
			return null;
		}
		@SuppressWarnings("deprecation")
		Date parsedDate = new Date(year, month, day);
		return parsedDate;
	}

	@Override
	public List<User> getAllUsers() {
		ArrayList<Entidad> eUsers = servicioPersistencia.recuperarEntidades(USER);
		List<User> users = eUsers.stream().map(e -> getUser(e.getId())).collect(Collectors.toList());
		return users;
	}

}
