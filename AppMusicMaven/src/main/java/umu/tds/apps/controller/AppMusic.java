package umu.tds.apps.controller;

import java.time.LocalDate;
import umu.tds.apps.models.User;

public class AppMusic {
	
	private static AppMusic onlyInstance = null;
	
	private AppMusic() {}
	
	public static AppMusic getInstance() {
		return onlyInstance == null ? new AppMusic() : onlyInstance;
	}
	
	public void play() {
		// TODO: Create the play function.
	}
	
	// public PlayList createList() {
		// TODO: createList();
	// }
	
	public boolean login(String username, String password) {
		// UserDAO.find(username);
		/* Search for username in BDD.
		 * If the user exists, we check if username.login = password.
		 */
		return false;
	}
	
	public User registerUser(String username, String password, String firstName, String lastName, String email, LocalDate birthDate) {
		// Maybe check if the username doesn't already exists. Email too.
		User user = new User(username, password, firstName, lastName, email, birthDate);
		return user;
	}
}
