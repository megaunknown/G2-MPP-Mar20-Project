package business;

public class ValidationHelper<E> {
	
	public void mandatoryValidator(E e) throws ValidationException {
		if (e == null) {
			throw new ValidationException("Mandatory Field...");
		}
		if (e instanceof String && ((String) e).isEmpty()) {
			throw new ValidationException("Mandatory Field...");
		}
	}
	
}
