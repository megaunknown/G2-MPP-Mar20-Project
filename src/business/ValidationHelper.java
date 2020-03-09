package business;

import javafx.collections.ObservableList;

public class ValidationHelper<E> {
	
	public void mandatoryValidator(E e) throws ValidationException {
		if (e == null) {
			throw new ValidationException("Mandatory Field...");
		}
		if (e instanceof String && ((String) e).isEmpty()) {
			throw new ValidationException("Mandatory Field...");
		}
		//Hanh added
		if(e instanceof ObservableList<?> && ((ObservableList<?>) e).isEmpty())
		{
			throw new ValidationException("Mandatory Field...");
		}
	}
	
}
