package cx.ath.jbzdak.jpaGui.ui.form;

import cx.ath.jbzdak.jpaGui.Utils;
import org.slf4j.Logger;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ResourceBundle;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-22
 */
public class DefaultFormElement<T extends Component> implements DisplayFormElement<T> {

   private static final Logger LOGGER = Utils.makeLogger();

   protected final PropertyChangeSupport support = new PropertyChangeSupport(this);

   private final ResourceBundle resourceBundle;

   private final T renderer;

   private final String name;

   private String shortDescription = null;

   private String longDescription = null;

   private boolean error = false;

   private boolean editable = true;

   private Object errorMessage = null;
   


   private static String loadDescription(String name, ResourceBundle bundle){
      if(bundle != null && name != null && bundle.containsKey(name)){
         return bundle.getString(name);
      }
      return null;
   }

   private static String loadName(String name, ResourceBundle bundle){
      String elementName = loadDescription(name, bundle);
      if(elementName!=null){
         return elementName;
      }
      return name;
   }

   public DefaultFormElement(T renderer, String name) {
      this(renderer, name, null, null, null);
   }

   public DefaultFormElement(T renderer, String name, ResourceBundle bundle) {
      this(renderer, loadName(name, bundle), loadDescription(name + ".shortDescription", bundle), loadDescription(name + ".longDescription", bundle), bundle);

   }

   public DefaultFormElement(T renderer, String name, String shortDescription, String longDescription, ResourceBundle resourceBundle) {
      this.renderer = renderer;
      this.name = name;
      this.shortDescription = shortDescription;
      this.longDescription = longDescription;
      this.resourceBundle = resourceBundle;
   }

   @Override
   public T getRenderer() {
      return renderer;
   }

   @Override
   public String getName() {
      return name;
   }

   @Override
   public String getShortDescription() {
      return shortDescription;
   }

   @Override
   public String getLongDescription() {
      return longDescription;
   }

   @Override
   public Object getErrorMessage() {
      return errorMessage;
   }

   @Override
   public boolean isError() {
      return error;
   }

   @Override
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

   @Override
   public void setEditable(boolean editable) {
      boolean oldEditable = this.editable;
      this.editable = editable;
      support.firePropertyChange("editable", oldEditable, this.editable);
   }

   public void addPropertyChangeListener(PropertyChangeListener listener) {
      support.addPropertyChangeListener(listener);
   }

   @Override
   public void addPropertyChangeListener(String propertyName,
                                         PropertyChangeListener listener) {
      support.addPropertyChangeListener(propertyName, listener);
   }

   protected void firePropertyChange(PropertyChangeEvent evt) {
      support.firePropertyChange(evt);
   }

   protected void firePropertyChange(String propertyName, Object oldValue,
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
