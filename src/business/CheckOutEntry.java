package business;

import java.io.Serializable;
import java.time.LocalDate;


public class CheckOutEntry implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int entryID;
	private LocalDate checkoutDate;
	private LocalDate dueDate;
	private LocalDate returnDate;

	private LibraryMember member;
	private User user;
	private BookCopy bookCopy;


	public CheckOutEntry(LocalDate checkoutDate, LocalDate dueDate, LocalDate returnDate, LibraryMember member,User user, BookCopy bookCopy) {
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
		this.returnDate = returnDate;
		this.member = member;
		this.user = user;
		this.bookCopy = bookCopy;

	}

	public void setEntryID(int entryID)
	{
		this.entryID  = entryID;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public LibraryMember getMember() {
		return member;
	}

	public void setMember(LibraryMember member) {
		this.member = member;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BookCopy getBookCopy() {
		return bookCopy;
	}

	public void setBookCopy(BookCopy bookCopy) {
		this.bookCopy = bookCopy;
	}
	
	public int getEntryID()
	{
		return this.entryID;
	}
	
}
