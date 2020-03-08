package business;

import java.io.Serializable;

public class Admin extends Role implements Serializable{
	@Override
	public String roleDescription() {
		return "Admin";
	}

}
