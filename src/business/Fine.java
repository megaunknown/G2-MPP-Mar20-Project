package business;

import java.time.LocalDate;

public class Fine {
	private double amount;
	private boolean paid;
	private LocalDate paidDate;
	private int checkOutEntryID;

	public Fine(double amount, boolean paid, LocalDate paidDate) {
		super();
		this.amount = amount;
		this.paid = paid;
		this.paidDate = paidDate;
	}

	public void setCheckOutEntryID(int checkOutEntryID){
		this.checkOutEntryID = checkOutEntryID;
	}

	public int getCheckOutEntryID()
	{
		return checkOutEntryID;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public LocalDate getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(LocalDate paidDate) {
		this.paidDate = paidDate;
	}

}
