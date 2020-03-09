package business;

import java.io.Serializable;

final public class BookCopy implements Serializable {

	private static final long serialVersionUID = -63976228084869815L;
	
	private Book book;
	private int copyNum;
	private boolean available;
	private String bookCopyUniqueID;
	
	public BookCopy(Book book, int copyNum, boolean available) {
		this.book = book;
		this.copyNum = copyNum;
		this.available = available;
		this.bookCopyUniqueID  = book.getIsbn()+"-" + copyNum+"";
	}
	
	public String getBookCopyId()
	{
		return this.bookCopyUniqueID ;
	}

	BookCopy(Book book, int copyNum) {
		this.book = book;
		this.copyNum = copyNum;
		this.bookCopyUniqueID  = book.getIsbn()+"-" + copyNum+"";
	}

	public int getCopyNum() {
		return copyNum;
	}

	public Book getBook() {
		return book;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public void changeAvailability() {
		available = !available;
	}

	@Override
	public boolean equals(Object ob) {
		if(ob == null) return false;
		if(!(ob instanceof BookCopy)) return false;
		BookCopy copy = (BookCopy)ob;
		return copy.book.getIsbn().equals(book.getIsbn()) && copy.copyNum == copyNum;
	}

}
