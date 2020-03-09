package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckOutRecord implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<CheckOutEntry> list;

	public CheckOutRecord()
	{
		list = new ArrayList<CheckOutEntry>();
	}

	public void createCheckOutRecord(CheckOutEntry checkOutRecord) throws Exception
	{
		if(list.contains(checkOutRecord)){
			throw new Exception("Entry is already exists");
		}
		else
		{
			list.add(checkOutRecord);
		}
	}

}
