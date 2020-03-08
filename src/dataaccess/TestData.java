package dataaccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import business.Address;
import business.Admin;
import business.Author;
import business.Book;
import business.BookCopy;
import business.Librarian;
import business.LibraryMember;
import business.User;

/**
 * This class loads data into the data repository and also sets up the storage
 * units that are used in the application. The main method in this class must be
 * run once (and only once) before the rest of the application can work
 * properly. It will create three serialized objects in the dataaccess.storage
 * folder.
 *
 *
 */
public class TestData {
	
	@SuppressWarnings("serial")
	List<Address> allAddresses = new ArrayList<Address>() {
		{
			add(new Address("101 S. Main", "Fairfield", "IA", "52556"));
			add(new Address("51 S. George", "Georgetown", "MI", "65434"));
			add(new Address("23 Headley Ave", "Seville", "Georgia", "41234"));
			add(new Address("1 N. Baton", "Baton Rouge", "LA", "33556"));
			add(new Address("5001 Venice Dr.", "Los Angeles", "CA", "93736"));
			add(new Address("1435 Channing Ave", "Palo Alto", "CA", "94301"));
			add(new Address("42 Dogwood Dr.", "Fairfield", "IA", "52556"));
			add(new Address("501 Central", "Mountain View", "CA", "94707"));
		}
	};


	@SuppressWarnings("serial")
	public List<Author> allAuthors = new ArrayList<Author>() {
		{
			add(new Author("Joe", "Thomas", "641-445-2123", allAddresses.get(0), "A happy man is he.", "A1"));
			add(new Author("Sandra", "Thomas", "641-445-2123", allAddresses.get(0), "A happy wife is she.", "A2"));
			add(new Author("Nirmal", "Pugh", "641-919-3223", allAddresses.get(1), "Thinker of thoughts.", "A3"));
			add(new Author("Andrew", "Cleveland", "976-445-2232", allAddresses.get(2), "Author of childrens' books.", "A4"));
			add(new Author("Sarah", "Connor", "123-422-2663", allAddresses.get(3), "Known for her clever style.", "A5"));
		}
	};

	@SuppressWarnings("serial")
	List<Book> allBooks = new ArrayList<Book>() {
		{
			add(new Book("23-11451", "The Big Fish", 21, Arrays.asList(allAuthors.get(0), allAuthors.get(1))));
			add(new Book("28-12331", "Antartica", 7, Arrays.asList(allAuthors.get(2))));
			add(new Book("99-22223", "Thinking Java", 21, Arrays.asList(allAuthors.get(3))));
			add(new Book("48-56882", "Jimmy's First Day of School", 7, Arrays.asList(allAuthors.get(4))));
		}
	};

	@SuppressWarnings("serial")
	List<BookCopy> allBookCopies = new ArrayList<BookCopy>() {
		{
			add(new BookCopy(allBooks.get(0), 11, true));
			add(new BookCopy(allBooks.get(1), 222, true));
			add(new BookCopy(allBooks.get(1), 1111, true));
		}
	};

	@SuppressWarnings("serial")
	List<User> allUsers = new ArrayList<User>() {
		{
			add(new User("101", "xyz", new Librarian()));
			add(new User("102", "abc", new Admin()));
			add(new User("103", "111", Arrays.asList(new Librarian(), new Admin())));
		}
	};
	
	@SuppressWarnings("serial")
	List<LibraryMember> allLibraryMembers = new ArrayList<LibraryMember>() {
		{
			add(new LibraryMember("1001", "Andy", "Rogers", "641-223-2211", allAddresses.get(4)));
			add(new LibraryMember("1002", "Drew", "Stevens", "702-998-2414", allAddresses.get(5)));
			add(new LibraryMember("1003", "Sarah", "Eagleton", "451-234-8811", allAddresses.get(6)));
			add(new LibraryMember("1004", "Ricardo", "Montalbahn", "641-472-2871", allAddresses.get(0)));
		}
	};
	
	public static void main(String[] args) {
		TestData td = new TestData();
		td.addressData();
		
		td.userData();
		td.bookData();
		td.authorsData();
		td.bookCopiesData();
		td.libraryMemberData();
		
		
		DataAccess da = new DataAccessFacade();
		System.out.println(da.readBooksMap());
		System.out.println(da.readUsersMap());
		
		
		td.bookData();
		
	}
	
	public void authorsData() {
		DataAccessFacade.loadAuthorsMap(allAuthors);
	}

	public void userData() {
		DataAccessFacade.loadUserMap(allUsers);
	}

	public void bookCopiesData() {
		DataAccessFacade.loadBookCopiesMap(allBookCopies);
	}

	public void libraryMemberData() {
		DataAccessFacade.loadMemberMap(allLibraryMembers);
	}
	
	public void addressData() {
		DataAccessFacade.loadAddressList(allAddresses);
	}
	
	/// create books
		public void bookData() {
			allBooks.get(0).addCopy();
			allBooks.get(0).addCopy();
			allBooks.get(1).addCopy();
			allBooks.get(3).addCopy();
			allBooks.get(2).addCopy();
			allBooks.get(2).addCopy();
			DataAccessFacade.loadBookMap(allBooks);
		}
}
