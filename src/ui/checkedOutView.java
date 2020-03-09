package ui;

import java.time.LocalDate;

public class checkedOutView {
	private int entryID;
	private LocalDate CheckedOutDate;
	private LocalDate DueDate;
	private LocalDate ReturnDate;
	public int getEntryID() {
		return entryID;
	}
	public void setEntryID(int entryID) {
		this.entryID = entryID;
	}
	public LocalDate getCheckedOutDate() {
		return CheckedOutDate;
	}
	public void setCheckedOutDate(LocalDate checkedOutDate) {
		CheckedOutDate = checkedOutDate;
	}
	public LocalDate getDueDate() {
		return DueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		DueDate = dueDate;
	}
	public LocalDate getReturnDate() {
		return ReturnDate;
	}
	public void setReturnDate(LocalDate returnDate) {
		ReturnDate = returnDate;
	}
	public String getMemberName() {
		return MemberName;
	}
	public void setMemberName(String memberName) {
		MemberName = memberName;
	}
	public String getMemberId() {
		return MemberId;
	}
	public void setMemberId(String memberId) {
		MemberId = memberId;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public String getBookTile() {
		return BookTile;
	}
	public void setBookTile(String bookTile) {
		BookTile = bookTile;
	}
	private String MemberName;
	private String MemberId;
	private String ISBN;
	private String BookTile;
	
}
