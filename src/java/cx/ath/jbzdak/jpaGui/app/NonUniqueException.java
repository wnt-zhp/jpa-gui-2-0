package cx.ath.jbzdak.jpaGui.app;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-22
 */
public class NonUniqueException extends RuntimeException{
   public NonUniqueException() {
   }

   public NonUniqueException(String message) {
      super(message);
   }

   public NonUniqueException(String message, Throwable cause) {
      super(message, cause);
   }

   public NonUniqueException(Throwable cause) {
      super(cause);
   }
}
