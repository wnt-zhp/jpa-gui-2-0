package cx.ath.jbzdak.jpaGui.ui.query;

import edu.umd.cs.findbugs.annotations.OverrideMustInvoke;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-20
 */
public abstract class AbstractQuery<P, O> implements Query<P, O> {

   @SuppressWarnings({"WeakerAccess"})
   protected final ActionEvent queryChangedEvt = new ActionEvent(this, 0, "QUERY_CHANGED");

   @SuppressWarnings({"WeakerAccess"})
   protected final PropertyChangeSupport support;

   @SuppressWarnings({"WeakerAccess"})
   protected final List<ActionListener> actionListeners = new ArrayList<ActionListener>();

   protected P query;

   protected AbstractQuery() {
      this.support = new PropertyChangeSupport(this);
   }

   @SuppressWarnings({"WeakerAccess"})
   protected AbstractQuery(PropertyChangeSupport support) {
      this.support = support;
   }

   @OverrideMustInvoke
   public void setQuery(P query) {
      if((this.query == null &&  query != null ) || (this.query!=null && !this.query.equals(query))){
         P oldQuery = this.query;
         this.query = query;
         queryChangedEntry();
         support.firePropertyChange("query", oldQuery, this.query);
         fireEvent(queryChangedEvt);
      }
   }

   /**
    * Odpalana po ustawieniu #query, ale przed odpaleniem
    * eventów
    */
   @SuppressWarnings({"WeakerAccess"})
   protected void queryChangedEntry(){

   }

    @SuppressWarnings({"WeakerAccess"})
    protected void fireEvent(ActionEvent event){
        for(ActionListener listener : actionListeners){
            listener.actionPerformed(event);
        }
    }


   public boolean addActionListener(ActionListener actionListener) {
        return actionListeners.add(actionListener);
    }

    public List<ActionListener> getActionListeners() {
        return Collections.unmodifiableList(actionListeners);
    }

    public ActionListener removeActionListener(int index) {
        return actionListeners.remove(index);
    }

   public void addPropertyChangeListener(PropertyChangeListener listener) {support.addPropertyChangeListener(listener);}

   public void removePropertyChangeListener(PropertyChangeListener listener) {
      support.removePropertyChangeListener(listener);
   }

   public PropertyChangeListener[] getPropertyChangeListeners() {return support.getPropertyChangeListeners();}

   public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
      support.addPropertyChangeListener(propertyName, listener);
   }

   public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
      support.removePropertyChangeListener(propertyName, listener);
   }

   public PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
      return support.getPropertyChangeListeners(propertyName);
   }

   public boolean hasListeners(String propertyName) {return support.hasListeners(propertyName);}
}
