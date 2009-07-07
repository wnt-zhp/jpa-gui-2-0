package cx.ath.jbzdak.jpaGui.ui.form;

import java.util.Collection;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-24
 */
public interface Form<FE extends FormElement<?, ? super B, ?>, B> {
   void startEditing();

   void startViewing();

   void rollback();

   void commit();

   void addValidator(Validator<? super B> v);

   boolean add(FE e);

   boolean addAll(Collection<? extends FE> c);

   boolean remove(FE o);
   

}
