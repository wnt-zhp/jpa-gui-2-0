package cx.ath.jbzdak.jpaGui.ui.autoComplete;

import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Property;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ResourceBundle;

import cx.ath.jbzdak.jpaGui.BeanHolder;
import cx.ath.jbzdak.jpaGui.BeanHolderAware;
import cx.ath.jbzdak.jpaGui.ui.FormAware;
import cx.ath.jbzdak.jpaGui.ui.form.Form;
import cx.ath.jbzdak.jpaGui.ui.form.PropertyFormElement;

/**
 * {@link cx.ath.jbzdak.jpaGui.ui.form.FormElement} obsługujący {@link AutocompleteComboBox}.
 *
 * @author jb
 * @param <B>
 */
public class ComboBoxElement<B, V> extends PropertyFormElement<AutocompleteComboBox<V>, B, V, BeanHolder<B>> implements FormAware {

   public ComboBoxElement(AutocompleteComboBox renderer, String labelText,
                          Property<B, Object> entityValueProperty) {
      this(renderer, labelText, null, entityValueProperty);
   }

   public ComboBoxElement(AutocompleteComboBox renderer, String labelText,
                          String entityPropertyPath) {
      this(renderer, labelText, null, BeanProperty.<Object, Object>create(entityPropertyPath));
   }

   public ComboBoxElement(AutocompleteComboBox<V> renderer, String labelText, ResourceBundle bundle, String entityPropertyPath) {
      this(renderer, labelText, bundle, BeanProperty.<Object, Object>create(entityPropertyPath));
   }

   public ComboBoxElement(AutocompleteComboBox<V> renderer, String labelText, ResourceBundle bundle, Property property) {
      super(renderer, labelText, bundle, property);
      getRenderer().addPropertyChangeListener("selectedItem", new PropertyChangeListener() {
         @Override
         public void propertyChange(PropertyChangeEvent evt) {
            setErrorMessage(evt.getNewValue() == null ? "Nie wybrano wartości" : null);
            setError(evt.getNewValue() == null);
         }
      });
   }

   @Override
   public V getValue() {
      return getRenderer().getSelectedItem();
   }

   @Override
   protected void setRendererEditable(boolean editable) {
      getRenderer().setEnabled(editable);

   }

   public void setValue(V value) {
      V oldValue = getValue();
       if (isReadNullValues() || value != null) {
         getRenderer().setSelectedItem(value);
      }
      support.firePropertyChange("value", oldValue, value);
   }

   @Override
   public void clear() {
      setValue(null);
      getRenderer().setFilter("");
   }

   @Override
   public void setForm(Form form) {
      if (getRenderer().getAdaptor() instanceof FormAware) {
         FormAware formAware = (FormAware) getRenderer().getAdaptor();
         formAware.setForm(form);
      }
   }

   @Override
   public void setBeanHolder(BeanHolder<B> beanHolder) {
      super.setBeanHolder(beanHolder);
      if (getRenderer().getAdaptor() instanceof BeanHolderAware) {
         BeanHolderAware beanHolderAware = (BeanHolderAware) getRenderer().getAdaptor();
         beanHolderAware.setBeanHolder(beanHolder);
      }
   }
}
