package umu.tds.apps.models;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import umu.tds.apps.persistence.DAOException;
import umu.tds.apps.persistence.FactoriaDAO;

public class UserRepo {
	private static UserRepo instance;
	private FactoriaDAO dao;

	private HashMap<Integer, User> usersId;
	private HashMap<String, User> usersUsername;

	public static UserRepo getInstance() {
		if (instance == null) instance = new UserRepo();
		return instance;
	}

	private UserRepo(){
		usersId = new HashMap<Integer, User>();
		usersUsername = new HashMap<String, User>();
		
		try {
			dao = FactoriaDAO.getInstancia();
			
			List<User> users = dao.getUserDAO().getAllUsers();
			for (User user : users) {
				usersId.put(user.getId(), user);
				usersUsername.put(user.getUsername(), user);
			}
		} catch (DAOException eDAO) {
			   eDAO.printStackTrace();
		}
	}
	
	public List<User> getUsers() throws DAOException {
		return new LinkedList<User>(usersUsername.values());
	}
	
	public User getUser(String username) {
		return usersUsername.get(username);
	}

	public User getUser(int id) {
		return usersId.get(id);
	}
	
	public void addUser(User user) {
		usersId.put(user.getId(), user);
		usersUsername.put(user.getUsername(), user);
		System.out.println(user);
	}
	
	public void removeUser(User user) {
		usersId.remove(user.getId());
		usersUsername.remove(user.getUsername());
	}
}
