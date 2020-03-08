package dataaccess;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import business.Address;
import business.Author;
import business.Book;
import business.BookCopy;
import business.Fine;
import business.LibraryMember;
import business.User;
import dataaccess.DataAccessFacade.StorageType;
import business.CheckOutEntry;
import business.CheckOutRecord;


public class DataAccessFacade implements DataAccess {

	public static enum StorageType {
		BOOKS, MEMBERS, USERS,AUTHORS,BOOKCOPIES,FINES,CHECKOUTENTRIES,ADDRESSES;
	}

	public static final String OUTPUT_DIR = System.getProperty("user.dir") + "\\src\\dataaccess\\storage";
	public static final String DATE_PATTERN = "MM/dd/yyyy";

	//implement: other save operations
	public void saveNewMember(LibraryMember member) {
		HashMap<String, LibraryMember> mems = readMembersMap();
		String memberId = member.getMemberId();
		mems.put(memberId, member);
		saveToStorage(StorageType.MEMBERS, mems);
	}
	
	public void editMember(LibraryMember member) {
		HashMap<String, LibraryMember> mems = readMembersMap();
		String memberId = member.getMemberId();
		
		mems.replace(memberId, member);
		saveToStorage(StorageType.MEMBERS, mems);
	}

	public void saveNewBook(Book book) {
		HashMap<String, Book> books = readBooksMap();
		String isbn = book.getIsbn();
		books.put(isbn, book);
		saveToStorage(StorageType.BOOKS, books);
	}

	public void saveNewBookCopy(BookCopy bookCopy) {
		HashMap<Integer, BookCopy> bookCopies = readBookCopiesMap();
		int copyNum = bookCopy.getCopyNum();
		bookCopies.put(copyNum, bookCopy);
		saveToStorage(StorageType.BOOKCOPIES, bookCopies);
	}

	public void saveNewLibraryMember(LibraryMember libraryMember) {
		HashMap<String, LibraryMember> libraryMemberMap = readMembersMap();
		String memberId = libraryMember.getMemberId();
		libraryMemberMap.put(memberId, libraryMember);
		saveToStorage(StorageType.MEMBERS, libraryMemberMap);
	}
	
	
	@SuppressWarnings("unchecked")
	public  static HashMap<String, CheckOutEntry> readCheckOutEntriesMap() {
		//Returns a Map with name/value pairs being
		//   EntryID -> CheckOutEntry
		return (HashMap<String, CheckOutEntry>) readFromStorage(StorageType.CHECKOUTENTRIES);
	}
	
	@SuppressWarnings("unchecked")
	public  HashMap<Integer, BookCopy> readBookCopiesMap() {
		//Returns a Map with name/value pairs being
		//   CopyId -> BookCopy
		return (HashMap<Integer, BookCopy>) readFromStorage(StorageType.BOOKCOPIES);
	}

	@SuppressWarnings("unchecked")
	public  HashMap<String,Book> readBooksMap() {
		//Returns a Map with name/value pairs being
		//   isbn -> Book
		return (HashMap<String,Book>) readFromStorage(StorageType.BOOKS);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, LibraryMember> readMembersMap() {
		//Returns a Map with name/value pairs being
		//   memberId -> LibraryMember
		return (HashMap<String, LibraryMember>) readFromStorage(StorageType.MEMBERS);
	}


	@SuppressWarnings("unchecked")
	public HashMap<String, User> readUsersMap() {
		//Returns a Map with name/value pairs being
		//   userId -> User
		return (HashMap<String, User>)readFromStorage(StorageType.USERS);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Author> readAuthorsMap() {
		// Returns a Map with CheckOutEntry/fine pairs being
		// CheckOutEntry -> fine
		return (HashMap<String, Author>)readFromStorage(StorageType.AUTHORS);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Fine> readFinesMap() {
		// Returns a Map with CheckOutEntry/fine pairs being
		// CheckOutEntry -> fine
		return (HashMap<String, Fine>)readFromStorage(StorageType.FINES);
	}

	/////load methods - these place test data into the storage area
	///// - used just once at startup
	static void loadAddressList(List<Address> addressList) {
		List<Address> address = new ArrayList<Address>();
		addressList.forEach(addr -> address.add(addr));
		saveToStorage(StorageType.ADDRESSES, address);
	}

	static void loadFineMap(List<Fine> fineList) {
		HashMap<String, Fine> finesMap = new HashMap<String, Fine>();
		fineList.forEach(fine -> finesMap.put(fine.getCheckOutEntryID()+"", fine));
		saveToStorage(StorageType.FINES, finesMap);
	}

	static void loadAuthorsMap(List<Author> authorsList) {
		HashMap<String, Author> authorsMap = new HashMap<String, Author>();
		authorsList.forEach(author -> authorsMap.put(author.getAuthorId(),author));
		saveToStorage(StorageType.AUTHORS, authorsMap);
	}


	public static void loadBookCopiesMap(List<BookCopy> bookCopiesList) {
		HashMap<String, BookCopy> bookCopies = new HashMap<String, BookCopy>();
		bookCopiesList.forEach( bc -> bookCopies.put(bc.getCopyNum()+"",bc));
		saveToStorage(StorageType.BOOKCOPIES, bookCopies);
	}
	
	static void loadBookCopiesList(List<BookCopy> bookCopiesList) {
		saveToStorage(StorageType.BOOKCOPIES, bookCopiesList);
	}



	static void loadBookMap(List<Book> bookList) {
		HashMap<String, Book> books = new HashMap<String, Book>();
		bookList.forEach(book -> books.put(book.getIsbn(), book));
		saveToStorage(StorageType.BOOKS, books);
	}


	static void loadUserMap(List<User> userList) {
		HashMap<String, User> users = new HashMap<String, User>();
		userList.forEach(user -> users.put(user.getId(), user));
		saveToStorage(StorageType.USERS, users);
	}

	static void loadMemberMap(List<LibraryMember> memberList) {
		HashMap<String, LibraryMember> members = new HashMap<String, LibraryMember>();
		memberList.forEach(member -> members.put(member.getMemberId(), member));
		saveToStorage(StorageType.MEMBERS, members);
	}

	public static void saveToStorage(StorageType type, Object ob) {
		ObjectOutputStream out = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(ob);
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch(Exception e) {}
			}
		}
	}

	static Object readFromStorage(StorageType type) {
		ObjectInputStream in = null;
		Object retVal = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			in = new ObjectInputStream(Files.newInputStream(path));
			retVal = in.readObject();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch(Exception e) {}
			}
		}
		return retVal;
	}



	final static class Pair<S,T> implements Serializable{

		S first;
		T second;
		Pair(S s, T t) {
			first = s;
			second = t;
		}
		@Override
		public boolean equals(Object ob) {
			if(ob == null) return false;
			if(this == ob) return true;
			if(ob.getClass() != getClass()) return false;
			@SuppressWarnings("unchecked")
			Pair<S,T> p = (Pair<S,T>)ob;
			return p.first.equals(first) && p.second.equals(second);
		}

		@Override
		public int hashCode() {
			return first.hashCode() + 5 * second.hashCode();
		}
		@Override
		public String toString() {
			return "(" + first.toString() + ", " + second.toString() + ")";
		}
		private static final long serialVersionUID = 5399827794066637059L;
	}





}
