package cx.ath.jbzdak.jpaGui.ui.formatted.formatters;

import cx.ath.jbzdak.jpaGui.FormattingException;
import cx.ath.jbzdak.jpaGui.ParsingException;
import cx.ath.jbzdak.jpaGui.ui.formatted.AbstractFormatter;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-06-02
 */
public class NonNullFormatter extends AbstractFormatter<Object, Object> {

   private String errorMessage = "Pole nie może być puste";

   public NonNullFormatter() {
   }

   public NonNullFormatter(String errorMessage) {
      this.errorMessage = errorMessage;
   }

   public Object parseValue(String text) throws Exception {
      if(StringUtils.isBlank(text)){
         throw new ParsingException(errorMessage);
      }
      return text;
   }

   public String formatValue(Object value) throws FormattingException {
      return value==null?"":value.toString();
   }

   public String getErrorMessage() {
      return errorMessage;
   }

   public void setErrorMessage(String errorMessage) {
      this.errorMessage = errorMessage;
   }
}
