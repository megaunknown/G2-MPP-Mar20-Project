package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class User  implements Serializable {

	private static final long serialVersionUID = 5147265048973262104L;

	private String id;
	private List<Role> roles;
	private String password;
	private Role userRole;

	public User(String id, String pass,Role UserRole) {
		this.id = id;
		this.password = pass;

		this.roles = new ArrayList<Role>();
		this.roles.add(UserRole);
	}

	public User(String id, String pass,List<Role> UserRole) {
		this.id = id;
		this.password = pass;

		this.roles = new ArrayList<Role>();
		this.roles.addAll(UserRole);
	}

	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public boolean hasAdminRole()
	{
		boolean bResult = false;
		for(int i = 0 ; i < roles.size() ; i++)
		{
			if(roles.get(i).roleDescription().equals("Admin"))
			{
				bResult=true;
				break;
			}
		}
		return bResult;
	}

	public boolean hasLibrarianRole()
	{
		boolean bResult = false;
		for(int i = 0 ; i < roles.size() ; i++)
		{
			if(roles.get(i).roleDescription().equals("Librarian"))
			{
				bResult=true;
				break;
			}
		}
		return bResult;
	}
	
	public Role getUserRole() {
		return userRole;
	}

	public void setUserRole(Role userRole) {
		this.userRole = userRole;
	}
	
}
