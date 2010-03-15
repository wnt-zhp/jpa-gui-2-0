package cx.ath.jbzdak.jpaGui.ui.form.validators;

import cx.ath.jbzdak.jpaGui.ui.form.ElementValidator;
import cx.ath.jbzdak.jpaGui.ui.form.ValidationException;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: Mar 14, 2010
 */
public class NonNullValidator implements ElementValidator<Object>{

   String message = "Nie ustawiono wartości";

   public NonNullValidator() {
   }

   public NonNullValidator(String message) {
      this.message = message;
   }

   @Override
   public boolean validate(Object value) throws Exception {
      if(value == null){
         throw new ValidationException(message);
      }
      return false;
   }
}
