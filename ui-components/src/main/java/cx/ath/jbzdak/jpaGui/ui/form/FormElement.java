package cx.ath.jbzdak.jpaGui.ui.form;

import java.awt.*;

import cx.ath.jbzdak.common.annotation.property.Bound;
import cx.ath.jbzdak.common.annotation.property.DefaultValue;

/**
 *
 * @param <T> Komponent
 * @param <B> Bean którego własność ustawiamy
 * @param <V> Typ ustawianej własności
 */
public interface FormElement<T extends Component, B, V> extends DisplayFormElement<T>{

   /**
	 * Rozpoczyna edycję.
    *
    * Ustawia edytowalność na {@link #isEditable()},
    * i zczytuje z wartość pola z .
    */
   void startEditing();

   /**
	 * Rozpoczyna podgląd.
    *
    * Ustawia edytowalność na false i
    * zczytuje wartość pola
    */
   void startViewing();

   /**
	 * Kończy edycję. Ustawia edytowalność na false.
    */
   void rollback();

   /**
	 * Zapisuje wartość pola do beana.
    */
   void commit();

   /**
	 * Czyści zawartość i wygenerowaną wartość
    */
   void clear();

   /**
    * Progammatically sets value to this formElement
    * @param v
    */
   void setValue(V v);

   /**
    * Gets last value form this element
    * @return
    */
   @Bound
   V getValue();

   /**
    * Should this formElement override non tull values in element
    * with null values from  bean
    * @param readNullValues
    */
   @DefaultValue("true")
	public void setReadNullValues(boolean readNullValues);

   public void setPropertySettingTime(PropertySettingTime time);

   public void addValidator(ElementValidator<? super V> validator);

   public boolean removeValidator(ElementValidator<? super V> validator);

}
