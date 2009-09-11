package cx.ath.jbzdak.jpaGui.app;

import cx.ath.jbzdak.jpaGui.ExceptionForUser;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: Apr 27, 2009
 */
@SuppressWarnings({"WeakerAccess"})
public class GenericRuntimeUserException extends RuntimeException implements ExceptionForUser{
   public GenericRuntimeUserException(String message) {
      super(message);
   }

   public GenericRuntimeUserException(String message, Throwable cause) {
      super(message, cause);
   }

   public GenericRuntimeUserException(Throwable cause) {
      super(cause);
   }
}
