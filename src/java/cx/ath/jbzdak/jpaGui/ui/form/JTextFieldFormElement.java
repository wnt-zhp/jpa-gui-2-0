package cx.ath.jbzdak.jpaGui.ui.form;

import javax.swing.JTextField;
import org.jdesktop.beansbinding.Property;

public class JTextFieldFormElement<T> extends PropertyFormElement<JTextField, T> {

	public JTextFieldFormElement(JTextField renderer, String labelText,
			Property<T, Object> entityValueProperty) {
		super(renderer, labelText, entityValueProperty);
	}


	public JTextFieldFormElement(JTextField renderer, String labelText,
			String entityPropertyPath) {
		super(renderer, labelText, entityPropertyPath);

	}

	@Override
	protected Object getRendererValue(){
		return null;
	}

	@Override
	protected void setRendererEditable(boolean editable) {
		getRenderer().setEditable(false);

	}

	@Override
	protected void setRendererValue(Object value) {
		getRenderer().setText(value==null?"":value.toString());
	}

	@Override
	public void clear() {
		getRenderer().setText("");

	}

	@Override
	public void commit() {
		//Ignorujemy commity!
	}


}
