package cx.ath.jbzdak.jpaGui;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-12
 */
public class ConfigurationException extends RuntimeException{
   public ConfigurationException() {
   }

   public ConfigurationException(String message) {
      super(message);
   }

   public ConfigurationException(String message, Throwable cause) {
      super(message, cause);
   }

   public ConfigurationException(Throwable cause) {
      super(cause);
   }
}
