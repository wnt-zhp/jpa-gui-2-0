package cx.ath.jbzdak.jpaGui.ui.form;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: Mar 14, 2010
 */
public interface ElementValidator<V> {

   boolean validate(V value) throws Exception;

  // boolean validateText(String text) throws Exception;
}
