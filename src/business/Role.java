package business;

import java.util.HashMap;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public abstract class Role {
	
	private String userId;
	private String userPassword;

	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserPassword() {
		return userPassword;
	}
	
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public abstract String roleDescription();

	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUsersMap();

		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}

		String passwordFound = map.get(id).getPassword();

		if(!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
	}

}
