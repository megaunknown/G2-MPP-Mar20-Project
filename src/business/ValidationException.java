package business;

import java.io.Serializable;

public class ValidationException extends Exception implements Serializable {
	
	private static final long serialVersionUID = 8978723266036027364L;

	public ValidationException() {
		super();
	}
	
	public ValidationException(String msg) {
		super(msg);
	}
	
	public ValidationException(Throwable t) {
		super(t);
	}

}
