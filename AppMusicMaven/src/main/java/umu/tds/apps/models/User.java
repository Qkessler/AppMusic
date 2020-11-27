package umu.tds.apps.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
	private int id;
	private String username, password, name, lastName, email;
	private boolean premium;
	private Date birthDate;
	private List<PlayList> playlists;
	
	public User(String username, String password, String name, String lastName, String email, Date birthDate) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.premium = false;
		this.birthDate = birthDate;
		this.playlists = new ArrayList<PlayList>();
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getPremium() {
		return premium;
	}

	public void setPremium(Boolean premium) {
		this.premium = premium;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public List<PlayList> getPlaylists() {
		return new ArrayList(playlists);
	}

	public void setPlaylists(List<PlayList> playlists) {
		this.playlists = playlists;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void makePayment() {
		// TODO: makePayment using a discount or not.
	}
	
	public boolean login(String password) {
		return this.password.equals(password);
	}
}
