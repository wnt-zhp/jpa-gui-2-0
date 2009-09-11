package cx.ath.jbzdak.jpaGui.app;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-24
 */
public class ConfigurationException extends Exception{
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
