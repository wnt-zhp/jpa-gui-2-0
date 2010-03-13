package cx.ath.jbzdak.jpaGui.ui.form;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: Mar 12, 2010
 */
public abstract class SettingValueErrorAction {

   private static final Logger LOGGER = LoggerFactory.getLogger(SettingValueErrorAction.class);

   public static final SettingValueErrorAction THROW = new SettingValueErrorAction() {
      @Override
      void errorWhileSettingValue(Exception e, PropertyFormElement formElement) {
         if (e instanceof RuntimeException) {
            RuntimeException runtimeException = (RuntimeException) e;
            throw runtimeException;
         }
         throw new RuntimeException(e); 
      }
   };

   public static final SettingValueErrorAction SET_ERROR = new SettingValueErrorAction() {
      @Override
      void errorWhileSettingValue(Exception e, PropertyFormElement formElement) {
         formElement.setError(true);
         formElement.setErrorMessage(e);
         LOGGER.warn("Error while setting value of property {}", e);
      }
   };

   public static final SettingValueErrorAction LOG = new SettingValueErrorAction() {
      @Override
      void errorWhileSettingValue(Exception e, PropertyFormElement formElement) {
         LOGGER.warn("Error while setting value of property {}", e);
      }
   };

   private SettingValueErrorAction() {
      
   }

   abstract void errorWhileSettingValue(Exception e, PropertyFormElement formElement);




}
