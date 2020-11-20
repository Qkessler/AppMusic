package umu.tds.apps.models;

import java.time.LocalDate;
import java.util.List;

public class User {
	private String username, password, firstName, lastName, email;
	private Boolean premium = false; // Check again.
	private LocalDate birthDate;
	private List<PlayList> playlists;
	
	public User(String username, String password, String firstName, String lastName, String email, LocalDate birthDate) {
		
	}
	
	public void makePayment() {
		// TODO: makePayment using a discount or not.
	}
	
	public boolean login(String password) {
		return this.password.equals(password);
	}
}
