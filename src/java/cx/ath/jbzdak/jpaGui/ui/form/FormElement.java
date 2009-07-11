package cx.ath.jbzdak.jpaGui.ui.form;

import java.awt.Component;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-22
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
   V getValue();

   /**
    * Should this formElement override nonnull values from bean, or not.
    * @param readNullValues
    */
	public void setReadNullValues(boolean readNullValues);

}
