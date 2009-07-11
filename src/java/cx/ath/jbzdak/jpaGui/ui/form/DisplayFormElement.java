package cx.ath.jbzdak.jpaGui.ui.form;

import java.awt.Component;
import java.beans.PropertyChangeListener;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-07-12
 */
public interface DisplayFormElement<T extends Component> {
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

   void setEditable(boolean editable);

   boolean isEditable(); 

   public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);
}
