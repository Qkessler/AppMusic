package umu.tds.controller;

import java.time.LocalDate;

import umu.tds.models.User;

public class AppMusic {
	
	public boolean login(String username, String password) {
		// UserDAO.find(username);
		/* Search for username in BDD.
		 * If the user exists, we check if username.login = password.
		 */
		return true;
	}
	
	public User registerUser(String username, String password, String firstName, String lastName, String email, LocalDate birthDate) {
		// Maybe check if the username doesn't already exists. Email too.
		User user = new User(username, password, firstName, lastName, email, birthDate);
		return user;
	}
}
