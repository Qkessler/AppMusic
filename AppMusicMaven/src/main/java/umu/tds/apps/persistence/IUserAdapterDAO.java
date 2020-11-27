package umu.tds.apps.persistence;

import java.util.List;

import umu.tds.apps.models.User;

public interface IUserAdapterDAO {
	public void registerUser(User user);
	public void removeUser(User user);
	public void updateProfile(User user);
	public User getUser(int id);
	public List<User> getAllUsers();
}
