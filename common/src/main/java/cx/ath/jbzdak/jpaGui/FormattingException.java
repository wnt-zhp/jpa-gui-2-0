package cx.ath.jbzdak.jpaGui;

public class FormattingException extends Exception implements ExceptionForUser {

	private static final long serialVersionUID = 1L;

   private String rendererText;

   public FormattingException(String message, String rendererText) {
      super(message);
      this.rendererText = rendererText;
   }

   public FormattingException(String message, Throwable cause, String rendererText) {
      super(message, cause);
      this.rendererText = rendererText;
   }

   public FormattingException(Throwable cause, String rendererText) {
      super(cause);
      this.rendererText = rendererText;
   }

   public FormattingException() {
		super();
	}

	public FormattingException(String message, Throwable cause) {
		super(message, cause);
	}

	public FormattingException(String message) {
		super(message);
	}

	public FormattingException(Throwable cause) {
		super(cause);
	}

   public String getRendererText() {
      return rendererText;
   }
}
