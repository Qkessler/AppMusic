package umu.tds.apps.persistence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.apps.models.User;

public class UserAdapterTDS implements IUserAdapterDAO{
	
	private static UserAdapterTDS instance;
	private static SimpleDateFormat dateFormat;
	private ServicioPersistencia servicioPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();

	private static final String USER = "user";
	private static final String NAME = "name";
	private static final String LAST_NAME = "last_name";
	private static final String EMAIL = "email";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String BIRTH_DATE = "birth_date";
	
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
		} catch(NullPointerException e) {
			registered = false;
		}
		if (registered) return;
		Entidad eUser = new Entidad();
		eUser.setNombre(USER);
		eUser.setPropiedades(
				new ArrayList<Propiedad>(Arrays.asList(
						new Propiedad(NAME, user.getName()),
						new Propiedad(LAST_NAME, user.getLastName()),
						new Propiedad(EMAIL, user.getEmail()),
						new Propiedad(USERNAME, user.getUsername()),
						new Propiedad(PASSWORD, user.getPassword()),
						new Propiedad(BIRTH_DATE, user.getBirthDate().toString())
				)));
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
		if (!removable) return;
		servicioPersistencia.borrarEntidad(eUser);
	}

	@Override
	public void updateProfile(User user) {
		Entidad eUser = servicioPersistencia.recuperarEntidad(user.getId());
		for(Propiedad prop : eUser.getPropiedades()) {
			switch(prop.getNombre()) {
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
		Date date = null;
		try {
			date = dateFormat.parse(birthDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		User user = new User(username, password, name, lastName, email, date);
		user.setId(id);
		return user; 
	}

	@Override
	public List<User> getAllUsers() {
		ArrayList<Entidad> eUsers = servicioPersistencia.recuperarEntidades(USER);
		List<User> users = eUsers.stream()
				.map(e -> getUser(e.getId()))
				.collect(Collectors.toList());
		return users;
	}
	
}
