package business;

import java.util.List;

import business.Book;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	public List<String> allAuthorsIds();
	public List<BookCopy> allBookCopies(Book book);
}
