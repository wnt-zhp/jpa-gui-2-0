package cx.ath.jbzdak.jpaGui.ui;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-25
 */
public interface ValueComponent {

   void setValue(Object o);

   void setValueProgrammatically(Object o);

   Object getValue();
}
