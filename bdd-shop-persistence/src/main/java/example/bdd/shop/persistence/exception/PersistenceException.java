package example.bdd.shop.persistence.exception;

public class PersistenceException extends RuntimeException {
	private static final long serialVersionUID = 4876192429894007122L;
	
	public PersistenceException(final String message) {
		super(message);
	}

}
