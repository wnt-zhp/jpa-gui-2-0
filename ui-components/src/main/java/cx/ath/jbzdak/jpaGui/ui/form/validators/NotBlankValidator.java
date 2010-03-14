package cx.ath.jbzdak.jpaGui.ui.form.validators;

import org.apache.commons.lang.StringUtils;

import cx.ath.jbzdak.jpaGui.ui.form.ElementValidator;
import cx.ath.jbzdak.jpaGui.ParsingException;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: Mar 14, 2010
 */
public class NotBlankValidator implements ElementValidator<String>{


   String message = "Pole nie może być puste";

   public NotBlankValidator() {
   }

   public NotBlankValidator(String message) {
      this.message = message;
   }

   @Override
   public boolean validate(String value) throws Exception {
      if(StringUtils.isBlank(value)){
         throw new ParsingException(message);
      }
      return false;  
   }
}
