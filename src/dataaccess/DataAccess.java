package dataaccess;

import java.util.HashMap;

import business.Author;
import business.Book;
import business.BookCopy;
import business.LibraryMember;
import business.User;

public interface DataAccess {
	
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUsersMap();
	public HashMap<String, LibraryMember> readMembersMap();
	public HashMap<String,Author> readAuthorsMap();
	public HashMap<String, BookCopy> readBookCopiesMap();
	public void changeBookCopyVisiblity(BookCopy bookCopy);
	public void saveNewMember(LibraryMember member);
	public void editMember(LibraryMember member);
	public void saveNewBookCopy(BookCopy bookCopy);
	public void saveNewBook(Book book);

}
