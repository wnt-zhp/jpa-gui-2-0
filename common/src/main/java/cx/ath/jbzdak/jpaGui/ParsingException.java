package cx.ath.jbzdak.jpaGui;

public class ParsingException extends Exception implements ExceptionForUser {


	private static final long serialVersionUID = 1L;

	public ParsingException() {
		super();
	}

	public ParsingException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParsingException(String message) {
		super(message);
	}

	public ParsingException(Throwable cause) {
		super(cause);
	}

}
