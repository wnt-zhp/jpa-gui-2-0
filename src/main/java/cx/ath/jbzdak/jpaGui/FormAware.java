package cx.ath.jbzdak.jpaGui;

import cx.ath.jbzdak.jpaGui.ui.form.Form;
import cx.ath.jbzdak.jpaGui.ui.form.FormElement;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-07-11
 */
public interface FormAware<FE extends FormElement<?, ? super B, ?>, B> {
   void setForm(Form<B, FE> form);
}
