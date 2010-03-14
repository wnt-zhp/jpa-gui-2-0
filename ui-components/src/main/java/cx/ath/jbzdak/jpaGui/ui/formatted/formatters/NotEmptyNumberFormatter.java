package cx.ath.jbzdak.jpaGui.ui.formatted.formatters;

import org.apache.commons.lang.StringUtils;

import cx.ath.jbzdak.jpaGui.Formatter;
import cx.ath.jbzdak.jpaGui.FormattingException;
import cx.ath.jbzdak.jpaGui.ParsingException;
import cx.ath.jbzdak.jpaGui.ui.formatted.AbstractFormatter;


/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: Mar 13, 2010
 */
public class NotEmptyNumberFormatter<N extends Number>extends AbstractFormatter<N,N>{

   final NumberFormatter<N> formatter;

   public static <N extends Number> NotEmptyNumberFormatter<N> createNotEmptyFormatter(NumberFormatter<N> wrapped){
      return new NotEmptyNumberFormatter<N>(wrapped);
   }

   public NotEmptyNumberFormatter(NumberFormatter<N> formatter) {
      this.formatter = formatter;
   }

   @Override
   public String formatValue(N value) throws FormattingException {
      return formatter.formatValue(value);
   }

   @Override
   public N parseValue(String text) throws Exception {
      if(StringUtils.isBlank(text)){
         throw new ParsingException("Nie wypełniono pola");
      }
      return formatter.parseValue(text);
   }
}
