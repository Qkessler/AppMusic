package umu.tds.business;

import javax.swing.*;
import java.util.Date;
import java.util.LinkedList;

public class User {
	private String name;
	private String password;
	private String lastName;
	private Date birthDate;
	private String username;
	private LinkedList<Song> recentSongs;
	private boolean premium;
	
	public User(String name, String password, String lastName, Date birthDate, String username) {
		this.name = name;
		this.password = password;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.username = username;
		recentSongs = new LinkedList<Song>();
		premium = false;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isPremium() {
		return premium;
	}
	public void setPremium(boolean premium) {
		this.premium = premium;
	}
	public String getName() {
		return name;
	}
	public String getLastName() {
		return lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}
	public LinkedList<Song> getRecentSongs() {
		return recentSongs;
	}
	
	

}
