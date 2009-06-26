package cx.ath.jbzdak.jpaGui.autoComplete;

import cx.ath.jbzdak.jpaGui.ui.form.PropertyFormElement;
import org.jdesktop.beansbinding.Property;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
/**
 * {@link cx.ath.jbzdak.jpaGui.ui.form.DAOFormElement} obsługujący {@link AutocompleteComboBox}.
 * @author jb
 *
 * @param <E>
 */
public class ComboBoxElement<E> extends PropertyFormElement<AutocompleteComboBox, E> {

	public ComboBoxElement(AutocompleteComboBox renderer, String labelText,
			Property<E, Object> entityValueProperty) {
		super(renderer, labelText, entityValueProperty);
	}

	public ComboBoxElement(AutocompleteComboBox renderer, String labelText,
			String entityPropertyPath) {
		super(renderer, labelText, entityPropertyPath);
	}

	{
		getRenderer().addPropertyChangeListener("selectedValue", new PropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
            setErrorMessage(evt.getNewValue()==null?"Nie wybrano wartości":null);
            setError(evt.getNewValue()==null);
			}
		});
	}

	@Override
	protected Object getRendererValue() {
		return getRenderer().getBeanValue();
	}

	@Override
	protected void setRendererEditable(boolean editable) {
		getRenderer().setEnabled(editable);

	}

	@Override
	protected void setRendererValue(Object value) {
		getRenderer().setBeanValue(value);
	}

	@Override
	public void clear() {
		setRendererValue(null);
		getRenderer().setFilter("");
	}
}
