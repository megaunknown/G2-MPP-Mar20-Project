package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class SystemController implements ControllerInterface {

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

	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMembersMap().keySet());
		return retval;
	}

	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	@Override
	public List<String> allAuthorsIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readAuthorsMap().keySet());
		return retval;
	}
	
	@Override
	public List<BookCopy> allBookCopies(Book book) {
		DataAccess da = new DataAccessFacade();
		List<BookCopy> retval = new ArrayList<>();
		da.readBookCopiesMap().values().forEach(a -> retval.add(a));
		return retval;
	}

}
