package business;

import java.io.Serializable;

final public class Author extends Person implements Serializable {

	private static final long serialVersionUID = 7508481940058530471L;
	
	private String bio;
	private String AuthorId;

	public String getBio() {
		return bio;
	}

	public String getAuthorId() {
		return AuthorId;
	}

	public Author(String f, String l, String t, Address a, String bio,String AuthorId) {
		super(f, l, t, a);

		this.bio = bio;
		this.AuthorId = AuthorId;
	}

	public String getAuthorName(){
		return getLastName() + " " + getFirstName();
	}
	
	@Override
	public String toString() {	
		return getLastName() + " " + getFirstName();
	}
	
}
