package cx.ath.jbzdak.jpaGui.ui.form;

import cx.ath.jbzdak.jpaGui.BeanHolder;
import org.jdesktop.beansbinding.Property;

import javax.swing.*;
import java.util.ResourceBundle;

public class JTextFieldFormElement<B> extends PropertyFormElement<JTextField, B, Object, BeanHolder<B>> {

	public JTextFieldFormElement(JTextField renderer, String labelText,
			Property<B, Object> entityValueProperty) {
		super(renderer, labelText, entityValueProperty);
	}


	public JTextFieldFormElement(JTextField renderer, String labelText,
			String entityPropertyPath) {
		super(renderer, labelText, entityPropertyPath);

	}

   public JTextFieldFormElement(JTextField renderer, String labelText, ResourceBundle bundle, String entityPropertyPath) {
      super(renderer, labelText, bundle, entityPropertyPath);
   }

   @Override
	public Object getValue(){
		return null;
	}

	@Override
	protected void setRendererEditable(boolean editable) {
		getRenderer().setEditable(false);

	}

	@Override
	public void setValue(Object value) {
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
