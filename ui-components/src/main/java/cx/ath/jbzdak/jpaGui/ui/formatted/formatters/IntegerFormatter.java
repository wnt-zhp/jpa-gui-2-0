package cx.ath.jbzdak.jpaGui.ui.formatted.formatters;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: Mar 16, 2010
 */
public class IntegerFormatter extends NumberFormatter<Integer>{

   public IntegerFormatter() {
   }

   @Override
   protected Integer parseResult(String text) {
      return Integer.valueOf(text);
   }
}
