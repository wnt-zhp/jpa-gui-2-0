package cx.ath.jbzdak.jpaGui.ui.form;

import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Property;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ResourceBundle;
import java.util.List;
import java.util.ArrayList;

import cx.ath.jbzdak.jpaGui.BeanHolder;
import cx.ath.jbzdak.jpaGui.BeanHolderAware;
import cx.ath.jbzdak.jpaGui.ui.FormAware;

public abstract class PropertyFormElement<T extends Component, B, V, BH extends BeanHolder<? extends B>>
        extends DefaultFormElement<T> implements FormElement<T,B,V>, BeanHolderAware<B, BH>, FormAware{

   @SuppressWarnings({"WeakerAccess"})
   protected final Property<B, Object> beanValueProperty;

   protected List<ElementValidator<? super V>> validators = new ArrayList<ElementValidator<? super V>>(); 

   private boolean readNullValues = true;

   private BH beanHolder;

   private Form form;

   private PropertySettingTime propertySettingTime;

   SettingValueErrorAction settingValueErrorAction = SettingValueErrorAction.THROW;

   private final PropertyChangeListener listener = new PropertyChangeListener() {
      @Override
      public void propertyChange(PropertyChangeEvent evt) {
         commit();
      }
   };

   protected PropertyFormElement(T renderer, String labelText) {
      this(renderer, labelText, (ResourceBundle) null, (Property) null);
   }

   protected PropertyFormElement(T renderer, String labelText, String entityPropertyPath) {
      this(renderer, labelText, null, BeanProperty.<Object, Object>create(entityPropertyPath));
   }

   protected PropertyFormElement(T renderer, String name, ResourceBundle bundle) {
      this(renderer, name, bundle, (Property) null);
   }

   protected PropertyFormElement(T renderer, String labelText, ResourceBundle bundle, String entityPropertyPath) {
      this(renderer, labelText, bundle, BeanProperty.<Object, Object>create(entityPropertyPath));
   }

   protected PropertyFormElement(T renderer, String labelText, Property<B, Object> beanValueProperty) {
      this(renderer, labelText, null, beanValueProperty);
   }

   protected PropertyFormElement(T renderer, String labelText, ResourceBundle bundle, Property property) {
      super(renderer, labelText, bundle);
      this.beanValueProperty =  property;
      addPropertyChangeListener("value", new PropertyChangeListener() {
         @Override
         public void propertyChange(PropertyChangeEvent evt) {
            for (ElementValidator<? super V> validator : validators) {
               try {
                  setError(validator.validate((V) evt.getNewValue()));
               } catch (Exception e) {
                  setErrorMessage(e);
                  setError(true);
               }
            }
         }
      });
   }

   @Override
   public void commit() {
      try {
         beanValueProperty.setValue(getBean(), getValue());
      } catch (RuntimeException e) {
         throw new RuntimeException("property: " + beanValueProperty.toString() + "entity: " + getBean(),e);
      }
   }

   @Override
   public void startEditing() {
      Object value = null;
      try {
         value = beanValueProperty.getValue(getBean());
      }catch (RuntimeException e) {
         settingValueErrorAction.errorWhileSettingValue(e, this);
      }
      if(value != null || readNullValues){
         setValue((V) value);
      }
      setRendererEditable(isEditable());
      startEditingEntry(value);
      if(isError()){
         support.firePropertyChange("error", false, true);
      }
      if(getErrorMessage()!=null){
         support.firePropertyChange("errorMsg", null, getErrorMessage());
      }
   }

   @SuppressWarnings({"UnusedParameters", "EmptyMethod", "WeakerAccess"})
   protected void startEditingEntry(Object value) { }

   @Override
   public void rollback() {
      setRendererEditable(false);
      stopEditingEntry();
   }

   @SuppressWarnings({"EmptyMethod", "WeakerAccess", "WeakerAccess"})
   protected  void stopEditingEntry() { }

   @Override
   public void startViewing() {
      setRendererEditable(false);
      setValue((V) beanValueProperty.getValue(getBean()));
      startViewingEntry();
   }

   @SuppressWarnings({"EmptyMethod", "WeakerAccess", "WeakerAccess"})
   protected void startViewingEntry() { }

   protected abstract void setRendererEditable(boolean editable);

   private B getBean() {
      return getBeanHolder().getBean()!=null?getBeanHolder().getBean():null;
   }

   @SuppressWarnings({"EmptyMethod", "WeakerAccess", "UnusedParameters"})
   protected void entitySet(B entity2) {	}

   @SuppressWarnings({"EmptyMethod", "WeakerAccess", "WeakerAccess", "UnusedDeclaration"})
   protected void rendererSet(T renderer) { }

   @Override
   public void setEditable(boolean editable) {
      super.setEditable(editable);
      setRendererEditable(editable);
   }

   @SuppressWarnings({"WeakerAccess"})
   public boolean isReadNullValues() {
      return readNullValues;
   }

   @Override
   public void setReadNullValues(boolean readEntityNullValues) {
      this.readNullValues = readEntityNullValues;
   }

   @SuppressWarnings({"WeakerAccess"})
   protected BH getBeanHolder() {
      return beanHolder;
   }

   @Override
   public void setBeanHolder(BH beanHolder) {
      this.beanHolder = beanHolder;
   }

   public Form getForm() {
      return form;
   }

   public void setForm(Form form) {
      this.form = form;
   }

   @Override
   public void setPropertySettingTime(PropertySettingTime time) {
      this.propertySettingTime = time;
      if(propertySettingTime == PropertySettingTime.ON_COMMIT){
         addPropertyChangeListener("value", listener);
      }else{
         removePropertyChangeListener("value", listener);
      }
   }

   public void setSettingValueErrorAction(SettingValueErrorAction settingValueErrorAction) {
      this.settingValueErrorAction = settingValueErrorAction;
   }

   public void addValidator(ElementValidator<? super V> validator){
      validators.add((validator));
   }

   public boolean removeValidator(ElementValidator<? super V> validator){
      return  validators.remove(validator);
   }
}
