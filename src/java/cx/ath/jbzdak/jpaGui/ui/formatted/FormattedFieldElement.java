package cx.ath.jbzdak.jpaGui.ui.formatted;

import cx.ath.jbzdak.jpaGui.ui.form.PropertyFormElement;
import org.jdesktop.beansbinding.Property;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class FormattedFieldElement<E> extends PropertyFormElement<MyFormattedTextField, E> {

   public FormattedFieldElement(MyFormattedTextField renderer, String labelText) {
      super(renderer, labelText);
   }

   public FormattedFieldElement(MyFormattedTextField renderer,
			String labelText, Property<E, Object> entityValueProperty) {
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
	protected Object getRendererValue() {
		return getRenderer().getValue();
	}

	@Override
	protected void setRendererEditable(boolean editable) {
		getRenderer().setEditable(editable);
	}

	@Override
	protected void setRendererValue(Object value) {
        if(isReadNullValues() || value!=null){
		    getRenderer().setValueFromBean(value);
        }
	}

	@Override
	public void clear() {
		getRenderer().setUserEnteredText("");
		getRenderer().setText("");
	}
}
