package cx.ath.jbzdak.jpaGui.ui.form;

import cx.ath.jbzdak.jpaGui.BeanHolder;

import java.awt.*;
import java.beans.PropertyChangeListener;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-22
 */
public interface FormElement<T extends Component, B, V> {
   /**
	 * Zwraca renderera
    * @return
    */
   T getRenderer();

    //Funkcje pozwalające na pobieranie informacji o błędach.
   /**
	 * Sprawdza czy wprowadzono błędną wartość.
    *
    * Poowinna to być bound property. Binding znajdzie sobie
    * addPropertyChangeListener()
    * @return
    */
   boolean isError();

   /**
	 * Pobiera wiadomość o błędzie.
    *
    * Uwaga {@link #isError()} nie implikuje że {@link #getErrorMessage()} != null.
    *
    * Przy komitowaniu Form sprawdza tylko drugi warunek - isError go nie obchodzi.
    *
    * @return
    */
   Object getErrorMessage();

   //Opis form elementu

   /**
	 * Zwraca nazwę pola/
    * @return
    */
   String getName();

   /**
    * Zwraca długi opis który będzie wyświetlony na przykład
    * po kliknięciu guzika ze znakiem zapytania.
    * @return
    */
   String getLongDescription();

   /**
    * Zwraca krótki opis mogący być na przykłąd wyśwyietlany w tooltipie
    * @return
    */
   String getShortDescription();

   public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);


   //O
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

   void setEditable(boolean editable);

   boolean isEditable();

   void setBeanHolder(BeanHolder<? extends B> beanHolder);

   /**
    * Should this formElement override nonnull values from bean, or not.
    * @param readNullValues
    */
	public void setReadNullValues(boolean readNullValues);

}
