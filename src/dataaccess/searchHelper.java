package dataaccess;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import business.Book;
import business.BookCopy;
import business.CheckOutEntry;
import business.LibraryMember;
import business.User;
import ui.UI_Helper_Class;

public class searchHelper {
	
	public static CheckOutEntry createCheckOutEntry(String MemberID,
													String currentUserID,
													String ISBN,
													int BookCopy,
													String strMessage) {
		DataAccess dataAccess = new DataAccessFacade();
		
		HashMap<String,Book> books =  dataAccess.readBooksMap();
		HashMap<String,LibraryMember> LibMember = dataAccess.readMembersMap();
		HashMap<String,User> users = dataAccess.readUsersMap();
		
		Book book = books.get(ISBN);
		
		LibraryMember libryMember = LibMember.get(MemberID);
		
		User usr = users.get(currentUserID);
		
		if(libryMember == null)
		{
			strMessage = "Library Member ID is Not Valid";
			UI_Helper_Class.showMessageBoxError(strMessage);
			return null;
		}
		
		if(book == null)
		{
			strMessage = "ISBN Not Valid";

			UI_Helper_Class.showMessageBoxError(strMessage);
			return null;
		}
		
		BookCopy bookCopy = getBookCopy(ISBN,BookCopy);
		
		if(bookCopy ==  null)
		{
			strMessage = "Book Copy is Not Valid";

			UI_Helper_Class.showMessageBoxError(strMessage);
			return null;
		}
		
		if(!bookCopy.isAvailable())
		{
			strMessage = "Book Copy is Not Avaliable.";

			UI_Helper_Class.showMessageBoxError(strMessage);
			return null;
		}
		
		if(usr == null)
		{
			strMessage = "Unable to fetch current USER";
			UI_Helper_Class.showMessageBoxError(strMessage);
			return null;
		}
		
		LocalDate currentDate = LocalDate.now(); // Create a date object
		LocalDate dueDate = currentDate.plusDays(book.getMaxCheckoutLength());
		
		HashMap<String,CheckOutEntry> CheckOutentries = DataAccessFacade.readCheckOutEntriesMap();
		CheckOutEntry checkOutentry = new CheckOutEntry(currentDate, dueDate, null, libryMember, usr, bookCopy);
		checkOutentry.setEntryID(CheckOutentries.size()+1);
		
		return checkOutentry;
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

	public static BookCopy getBookCopy(String ISBN, int BookCopy) {
		DataAccess dataAccess = new DataAccessFacade();
		HashMap<String,BookCopy> BookCopyListObj =  dataAccess.readBookCopiesMap();
		return BookCopyListObj.get(ISBN+"-" + BookCopy);
	}
	
	public static Book getBookByISBN(String strISBN) {
		DataAccess dataAccess = new DataAccessFacade();
		HashMap<String,Book> books =  dataAccess.readBooksMap();
		return books.get(strISBN);
	}
	
	public static boolean checkBookAndBookCopy(String ISBN,int copyNumber) {
		Book book = getBookByISBN(ISBN);
		boolean bResult = false;
		if(book.getCopyNums().contains(copyNumber))
			bResult = true;
		return bResult;
	}
	
	public static boolean checkBookCopy(String ISBN,int copyNumber) {
		Book book = getBookByISBN(ISBN);
		boolean bResult = false;
		if(book.getCopyNums().contains(copyNumber))
			bResult = true;
		return bResult;
	}
	
	public static List<String> getLibraryMembersIDs() {
		List<String> strings = new ArrayList<String>();
		DataAccess da = new DataAccessFacade();
		HashMap<String,LibraryMember> membersMap = da.readMembersMap();
		membersMap.values().forEach(a -> strings.add(a.getMemberId()));
		
		return strings;
	}
	
}
