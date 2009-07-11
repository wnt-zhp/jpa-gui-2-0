package cx.ath.jbzdak.jpaGui.app;

import cx.ath.jbzdak.jpaGui.ExceptionForUser;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-22
 */
@SuppressWarnings({"WeakerAccess"})
public class ValidationException extends Exception implements ExceptionForUser{

   private final ConfigEntry configEntry;

   private final Object value;

   private static String formatMessage(String message, ConfigEntry configEntry, Object value){
      return message + " for key " + configEntry.getName() + " for value"  + value;
   }

   public ValidationException(String message, Throwable cause, ConfigEntry configEntry, Object value) {
      super(formatMessage(message, configEntry, value), cause);
      this.configEntry = configEntry;
      this.value = value;
   }

   public ValidationException(String message, ConfigEntry configEntry, Object value) {
      super(formatMessage(message, configEntry, value));
      this.configEntry = configEntry;
      this.value = value;
   }

   public ConfigEntry getConfigEntry() {
      return configEntry;
   }

   public Object getValue() {
      return value;
   }
}
