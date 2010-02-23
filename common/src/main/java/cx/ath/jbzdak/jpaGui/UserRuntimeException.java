package cx.ath.jbzdak.jpaGui;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-11-28
 */
public class UserRuntimeException extends RuntimeException implements ExceptionForUser{
   public UserRuntimeException() {
   }

   public UserRuntimeException(Throwable cause) {
      super(cause);
   }

   public UserRuntimeException(String message) {
      super(message);
   }

   public UserRuntimeException(String message, Throwable cause) {
      super(message, cause);
   }
}
