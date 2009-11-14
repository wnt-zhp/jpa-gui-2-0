package cx.ath.jbzdak.jpaGui.ui.formatted;

import cx.ath.jbzdak.jpaGui.BeanHolder;
import cx.ath.jbzdak.jpaGui.BeanHolderAware;
import cx.ath.jbzdak.jpaGui.Formatter;
import cx.ath.jbzdak.jpaGui.ui.FormAware;
import cx.ath.jbzdak.jpaGui.ui.form.Form;
import cx.ath.jbzdak.jpaGui.ui.form.PropertyFormElement;
import org.jdesktop.beansbinding.Property;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ResourceBundle;


public class FormattedFieldElement<B, V> extends PropertyFormElement<FormattedTextField, B, V, BeanHolder<B>> implements BeanHolderAware<B, BeanHolder<B>>, FormAware {

   public FormattedFieldElement(FormattedTextField renderer, String labelText) {
      super(renderer, labelText);
   }

   public FormattedFieldElement(FormattedTextField renderer,
                                String labelText, Property<B, Object> entityValueProperty) {
      super(renderer, labelText, entityValueProperty);
   }

   public FormattedFieldElement(FormattedTextField renderer,
                                String labelText, String entityPropertyPath) {
      super(renderer, labelText, entityPropertyPath);
   }

   public FormattedFieldElement(FormattedTextField renderer, String labelText, ResourceBundle bundle, String entityPropertyPath) {
      super(renderer, labelText, bundle, entityPropertyPath);
   }

   {
      getRenderer().addPropertyChangeListener("parseResults", new PropertyChangeListener() {
         @Override
         public void propertyChange(PropertyChangeEvent evt) {
            setErrorMessage(evt.getNewValue());
            setError(evt.getNewValue() != null);
         }
      });
      getRenderer().addPropertyChangeListener("formatter", new PropertyChangeListener() {
         @Override
         public void propertyChange(PropertyChangeEvent evt) {
            updateFormatter((Formatter) evt.getNewValue());
         }
      });
      setErrorMessage(getRenderer().getParseResults());
      //noinspection ThrowableResultOfMethodCallIgnored
      setError(getRenderer().getParseResults() != null);

   }

   @Override
   public V getValue() {
      return (V) getRenderer().getValue();
   }

   @Override
   protected void setRendererEditable(boolean editable) {
      getRenderer().setEditable(editable);
   }

   @Override
   public void setValue(V value) {
      if (isReadNullValues() || value != null) {
         getRenderer().setValueFromBean(value);
      }
   }

   @Override
   public void clear() {
      getRenderer().setUserEnteredText("");
      getRenderer().setText("");
   }

   public void updateFormatter(Formatter f) {
      if (f == null) {
         return;
      }
      if (f instanceof BeanHolderAware) {
         BeanHolderAware beanHolderAware = (BeanHolderAware) f;
         beanHolderAware.setBeanHolder(getBeanHolder());
      }
      if (f instanceof FormAware) {
         FormAware formAware = (FormAware) f;
         formAware.setForm(getForm());
      }

   }


   @Override
   public void setForm(Form form) {
      super.setForm(form);
      updateFormatter(getRenderer().getFormatter());
   }

   @Override
   public void setBeanHolder(BeanHolder<B> beanHolder) {
      super.setBeanHolder(beanHolder);
      updateFormatter(getRenderer().getFormatter());
   }
}
