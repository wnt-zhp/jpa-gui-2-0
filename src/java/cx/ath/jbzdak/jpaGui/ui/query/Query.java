package cx.ath.jbzdak.jpaGui.ui.query;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-20
 * @param <P> typ będący wzorcem do którego dopasowujemy
 *
 * @param <0> Obiekty filtrtowane
 */
public interface Query<P, O> {

   /**
    * Komenta odpalana przez instancje interfejsu kiedy kwerenda się zmieniła
    */
   public String QUERY_CHANGED_COMMAND = "QUERY_CHANGED";

   /**
    * Ustawia filtr, albo inaczej zapyatnie. Jeśli
    * zapytanie zmienia się tak że należy jeszcze raz
    * przefiltrować kolekcję wyników powinno odpalić
    * @{java.awt.event.ActionEvent} z komendą #QUERY_CHANGED_COMMAND
    * @param query ustawiany filtr
    */
   void setQuery(P query);

   /**
    * Sparawza czy <code>str</code> powinno trafić
    * do wyników. Zwraca true jeśli tak.
    * @param str
    * @return
    */
   boolean matches(O str);

   boolean addActionListener(ActionListener actionListener);

   //Dumb methods that acceses listeners.

   List<ActionListener> getActionListeners();

   ActionListener removeActionListener(int index);

   void addPropertyChangeListener(PropertyChangeListener listener);

   void removePropertyChangeListener(PropertyChangeListener listener);

   PropertyChangeListener[] getPropertyChangeListeners();

   void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

   void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);

   PropertyChangeListener[] getPropertyChangeListeners(String propertyName);

   boolean hasListeners(String propertyName);
}
