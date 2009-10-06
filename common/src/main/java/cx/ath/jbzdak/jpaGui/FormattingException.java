package cx.ath.jbzdak.jpaGui;

public class FormattingException extends Exception implements ExceptionForUser {

	private static final long serialVersionUID = 1L;

   private final String rendererText;

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

   public String getRendererText() {
      return rendererText;
   }
}
