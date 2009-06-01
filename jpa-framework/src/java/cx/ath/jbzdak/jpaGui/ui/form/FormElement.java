package cx.ath.jbzdak.jpaGui.ui.form;

import java.awt.Component;
import java.beans.PropertyChangeListener;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-22
 */
public interface FormElement<T extends Component> {
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

   /**
	 * Zwraca renderera
    * @return
    */
   T getRenderer();

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

   void setEditable(boolean editable);

   boolean isEditable();
}
