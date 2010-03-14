package cx.ath.jbzdak.jpaGui.ui.form;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: Mar 14, 2010
 */
public class ValidationException extends Exception{

   public ValidationException() {
   }

   public ValidationException(Throwable cause) {
      super(cause);
   }

   public ValidationException(String message) {
      super(message);
   }

   public ValidationException(String message, Throwable cause) {
      super(message, cause);
   }
}
