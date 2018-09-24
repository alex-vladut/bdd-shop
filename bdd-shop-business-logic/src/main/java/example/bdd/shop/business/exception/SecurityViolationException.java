package example.bdd.shop.business.exception;

public class SecurityViolationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public SecurityViolationException(final String message) {
		super(message);
	}
}
