package cx.ath.jbzdak.jpaGui.ui.formatted;

import cx.ath.jbzdak.jpaGui.BeanHolder;
import cx.ath.jbzdak.jpaGui.BeanHolderAware;
import cx.ath.jbzdak.jpaGui.FormAware;
import cx.ath.jbzdak.jpaGui.ui.form.Form;
import cx.ath.jbzdak.jpaGui.ui.form.PropertyFormElement;
import org.jdesktop.beansbinding.Property;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class FormattedFieldElement<B, V> extends PropertyFormElement<MyFormattedTextField, B, V, BeanHolder<B>> implements BeanHolderAware<B, BeanHolder<B>>, FormAware{

   public FormattedFieldElement(MyFormattedTextField renderer, String labelText) {
      super(renderer, labelText);
   }

   public FormattedFieldElement(MyFormattedTextField renderer,
			String labelText, Property<B, Object> entityValueProperty) {
		super(renderer, labelText, entityValueProperty);
	}

	public FormattedFieldElement(MyFormattedTextField renderer,
			String labelText, String entityPropertyPath) {
		super(renderer, labelText, entityPropertyPath);
	}

	{
		getRenderer().addPropertyChangeListener("parseResults", new PropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
            setErrorMessage(evt.getNewValue());
            setError(evt.getNewValue()!=null);
			}
		});
      setErrorMessage(getRenderer().getParseResults());
      setError(getRenderer().getParseResults()!=null);

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
        if(isReadNullValues() || value!=null){
		    getRenderer().setValueFromBean(value);
        }
	}

	@Override
	public void clear() {
		getRenderer().setUserEnteredText("");
		getRenderer().setText("");
	}

   @Override
   public void setBeanHolder(BeanHolder<B> beanHolder) {
      if (getRenderer().getFormatter() instanceof BeanHolderAware) {
         BeanHolderAware beanHolderAware = (BeanHolderAware) getRenderer().getFormatter();
         beanHolderAware.setBeanHolder(beanHolder);
      }
   }

   @Override
   public void setForm(Form form) {
      if (getRenderer().getFormatter() instanceof FormAware) {
         FormAware formAware = (FormAware) getRenderer().getFormatter();
         formAware.setForm(form);
      }
   }
}
