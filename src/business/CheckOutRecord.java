package business;

import java.util.ArrayList;
import java.util.List;

public class CheckOutRecord {
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

	public void createReturnRecord(CheckOutEntry checkOutRecord)
	{
		
		//Calculate Over Due...
		
	}
}
