package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class Librarian extends Role implements Serializable{
	private static final long serialVersionUID = 1L;


	public CheckOutEntry createCheckoutEntry(String ISBN,String currentUserID, int copyNum, String memberId, String ErrorMessage) {
		String msg = "";
		BookCopy bc = null;
		bc = searchBookCopy(ISBN, copyNum, msg);
		if (bc == null) {
			ErrorMessage = msg;
			return null;
		}

		CheckOutEntry coe = null;

		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> members = da.readMembersMap();

		if (members.get(memberId) == null) {
			ErrorMessage = "Member is not existed";
			return null;
		}

		LibraryMember member = members.get(memberId);
		HashMap<String, Book> books = da.readBooksMap();
		Book book = books.get(ISBN);

		LocalDate checkoutDate = LocalDate.now();
		LocalDate returnDate = null;
		LocalDate dueDate = checkoutDate.plusDays(book.getMaxCheckoutLength());

		
		String UserID = "103";
	
		//coe = new CheckOutEntry(checkoutDate, dueDate, returnDate, member, UserID,bc);

		return coe;
	}
	
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
