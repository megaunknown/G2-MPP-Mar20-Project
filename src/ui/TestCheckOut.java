package ui;

import java.util.HashMap;

import business.*;
import dataaccess.*;

public class TestCheckOut {
	public static void main(String[] args)
	{
		 CheckOutEntry COE;
		 BookCopy bookcopy;
		 Book book;
		 String strMessage ="";
		 
		 COE = searchHelper.createCheckOutEntry("1001",
					"101",
					"23-11451",
					11,
					strMessage);
		 
		System.out.println("Message :" + strMessage);
		
		if(COE != null && strMessage.length() == 0)
		{
			DataAccess da = new DataAccessFacade();
			
			//First Part ===> Save The Changes to Book Copy
			HashMap<String,BookCopy> hasMapBookCopies = da.readBookCopiesMap();
			BookCopy BC = hasMapBookCopies.get("23-11451"+ "-" + "11");
			da.changeBookCopyVisiblity(BC);
	        //Second Part ==> Save New Entry to Checkout  
	        DataAccessFacade.saveNewCheckOutEntry(COE);	
		}
	
	}
}
