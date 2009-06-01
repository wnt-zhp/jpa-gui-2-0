package cx.ath.jbzdak.jpaGui.ui.form;

import cx.ath.jbzdak.jpaGui.Utils;
import org.slf4j.Logger;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-22
 */
public abstract class AbstractFormElement<T extends Component> implements FormElement<T> {

   private static final Logger LOGGER = Utils.makeLogger();

   private final T renderer;

   private final String name;

   private String shortDescription;

   private String longDescription;

   private boolean error;

   private boolean editable = true;

   private Object errorMessage;

   protected final PropertyChangeSupport support = new PropertyChangeSupport(this);

   public AbstractFormElement(T renderer, String name) {
      this.renderer = renderer;
      this.name = name;
   }

   public AbstractFormElement(T renderer, String name, String shortDescription, String longDescription) {
      this.renderer = renderer;
      this.name = name;
      this.shortDescription = shortDescription;
      this.longDescription = longDescription;
   }

   @Override
   public T getRenderer() {
      return renderer;
   }

   public String getName() {
      return name;
   }

   public String getShortDescription() {
      return shortDescription;
   }

   public String getLongDescription() {
      return longDescription;
   }

   public Object getErrorMessage() {
      return errorMessage;
   }

   public boolean isError() {
      return error;
   }

   public boolean isEditable() {
      return editable;
   }

   public void setLongDescription(String longDescription) {
      String oldLongDescription = this.longDescription;
      this.longDescription = longDescription;
      support.firePropertyChange("longDescription", oldLongDescription, this.longDescription);
   }

   public void setShortDescription(String shortDescription) {
      String oldShortDescription = this.shortDescription;
      this.shortDescription = shortDescription;
      support.firePropertyChange("shortDescription", oldShortDescription, this.shortDescription);
   }

   public void setError(boolean error) {
      boolean oldError = this.error;
      this.error = error;
      support.firePropertyChange("error", oldError, this.error);
   }

   public void setErrorMessage(Object errorMessage) {
      Object oldErrorMessage = this.errorMessage;
      this.errorMessage = errorMessage;
      support.firePropertyChange("errorMessage", oldErrorMessage, this.errorMessage);
//      if(errorMessage!=null){
//         LOGGER.debug(ErrorHandlers.createLongHandlers().getHandler(errorMessage).getMessage(errorMessage));
//      }
   }

   public void setEditable(boolean editable) {
      boolean oldEditable = this.editable;
      this.editable = editable;
      support.firePropertyChange("editable", oldEditable, this.editable);
   }


   public void addPropertyChangeListener(PropertyChangeListener listener) {
      support.addPropertyChangeListener(listener);
   }

   public void addPropertyChangeListener(String propertyName,
                                         PropertyChangeListener listener) {
      support.addPropertyChangeListener(propertyName, listener);
   }

   public void firePropertyChange(PropertyChangeEvent evt) {
      support.firePropertyChange(evt);
   }

   public void firePropertyChange(String propertyName, Object oldValue,
                                  Object newValue) {
      support.firePropertyChange(propertyName, oldValue, newValue);
   }

   public PropertyChangeListener[] getPropertyChangeListeners() {
      return support.getPropertyChangeListeners();
   }

   public PropertyChangeListener[] getPropertyChangeListeners(
           String propertyName) {
      return support.getPropertyChangeListeners(propertyName);
   }

   public boolean hasListeners(String propertyName) {
      return support.hasListeners(propertyName);
   }

   public void removePropertyChangeListener(PropertyChangeListener listener) {
      support.removePropertyChangeListener(listener);
   }

   public void removePropertyChangeListener(String propertyName,
                                            PropertyChangeListener listener) {
      support.removePropertyChangeListener(propertyName, listener);
   }
}
