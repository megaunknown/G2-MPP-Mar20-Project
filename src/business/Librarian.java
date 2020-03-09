package business;

import java.io.Serializable;
import java.util.HashMap;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class Librarian extends Role implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public BookCopy searchBookCopy(String ISBN, int copyNum,String refMsg) {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> books = da.readBooksMap();
		if (books.get(ISBN) == null) {
			refMsg = "Not valid ISBN";
			return null;
		}
		Book book = books.get(ISBN);

		if (book.getCopy(copyNum) == null) {
			refMsg = "Not Avaliable Copy Number.";
			return null;
		}
		
		BookCopy bookCopy = book.getCopy(copyNum);
		return bookCopy;
	}
	
	

	@Override
	public String roleDescription() {
		  return "Librarian";
	}

}
