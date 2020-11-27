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
	
	public List<User> getUsuarios() throws DAOException {
		return new LinkedList<User>(usersUsername.values());
	}
	
	public User getUsuario(String login) {
		return usersUsername.get(login);
	}

	public User getUsuario(int id) {
		return usersId.get(id);
	}
	
	public void addUsuario(User usuario) {
		usersId.put(usuario.getId(), usuario);
		usersUsername.put(usuario.getUsername(), usuario);
	}
	
	public void removeUsuario(User usuario) {
		usersId.remove(usuario.getId());
		usersUsername.remove(usuario.getUsername());
	}
}
