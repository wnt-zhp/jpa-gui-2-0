package cx.ath.jbzdak.jpaGui;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: Mar 16, 2010
 */
public class SimpleBeanHolder<B> implements BeanHolder<B>{

   private final PropertyChangeSupport support = new PropertyChangeSupport(this);

   private B bean;

   public B getBean() {
      return bean;
   }

   public void setBean(B bean) {
      B oldBean = this.bean;
      this.bean = bean;
      support.firePropertyChange("bean", oldBean, this.bean);
   }

   public void addPropertyChangeListener(PropertyChangeListener listener) {
      support.addPropertyChangeListener(listener);
   }

   public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
      support.addPropertyChangeListener(propertyName, listener);
   }

   public boolean hasListeners(String propertyName) {
      return support.hasListeners(propertyName);
   }

   public void removePropertyChangeListener(PropertyChangeListener listener) {
      support.removePropertyChangeListener(listener);
   }

   public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
      support.removePropertyChangeListener(propertyName, listener);
   }
}
