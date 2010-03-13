package cx.ath.jbzdak.jpaGui.ui.form;

import java.util.Collection;

import cx.ath.jbzdak.common.annotation.property.DefaultValue;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-24
 */
public interface Form<B, FE extends FormElement<?, ? super B, ?>>{

   void startEditing();

   void startViewing();

   void rollback();

   void commit();

   void addValidator(Validator<? super B> v);

   boolean add(FE e);

   void addAll(Collection<? extends FE> c);

   boolean remove(FE o);

   /**
    * Ustawia czy powinno się wartości ustawiać w beanie dopiero przy komicie czy wcześniej
    * @param time
    */
   @DefaultValue("true")
   public void setPropertySettingTime(PropertySettingTime time);
   

}
