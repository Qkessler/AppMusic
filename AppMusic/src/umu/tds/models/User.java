package umu.tds.models;

import java.time.LocalDate;

public class User {
	private String username, password, firstName, lastName, email;
	private LocalDate birthDate;
	// ...
	
	public User(String username, String password, String firstName, String lastName, String email, LocalDate birthDate) {
		
	}
	
	public boolean login(String password) {
		return this.password.equals(password);
	}
}
