package cx.ath.jbzdak.jpaGui.ui.form;

import cx.ath.jbzdak.jpaGui.autoComplete.AutocompleteComboBox;
import cx.ath.jbzdak.jpaGui.autoComplete.ComboBoxElement;
import cx.ath.jbzdak.jpaGui.ui.formatted.FormattedFieldElement;
import cx.ath.jbzdak.jpaGui.ui.formatted.MyFormattedTextField;
import cx.ath.jbzdak.jpaGui.ui.formatted.MyFormatter;
import javax.swing.JTextField;
import org.jdesktop.beansbinding.Property;

import java.util.Map;
import java.util.ResourceBundle;

@SuppressWarnings("unchecked")
public class FormFactory<T>  {

    private ResourceBundle resourceBundle;

	private DAOForm<T, DAOFormElement> createdForm = new DAOForm<T,DAOFormElement>();

	private String layout = "default";

	private Map<String,String> constraints;

	public DAOForm<T,DAOFormElement> getCreatedForm() {

		return createdForm;
	}

	private Map<String, String> getConstraints(Class clazz){
		if(constraints!=null){
			return constraints;
		}
		return FormPanelConstraints.getConstraints(layout, clazz);
	}


	public FormPanel<MyFormattedTextField> decorateFormattedTextField(
			String labelText,
			String beanProperty,
			MyFormattedTextField field
	){
		FormattedFieldElement<T> elem = new FormattedFieldElement<T>(field, labelText, beanProperty);
		FormPanel p =  new FormPanel<MyFormattedTextField>(elem, getConstraints(field.getClass()), resourceBundle);
		createdForm.add(elem);

		return p;
	}

	public FormPanel<MyFormattedTextField> decorateFormattedTextField(
			String labelText,
			String beanProperty,
			MyFormatter formatter
	){
		return decorateFormattedTextField(labelText, beanProperty, new MyFormattedTextField(formatter));
	}

	public FormPanel<AutocompleteComboBox> decotrateComboBox(
			String labelText,
			String beanProperty,
			AutocompleteComboBox field
	){
      ComboBoxElement<T> element = new ComboBoxElement<T>(field, labelText, beanProperty);
      FormPanel p =  new FormPanel<AutocompleteComboBox>(element, getConstraints(field.getClass()));
		createdForm.add(element);
		return p;
	}

	public FormPanel<JTextField> decotrateJTextField(
			String labelText,
			String beanProperty
	){
      JTextFieldFormElement<T> jTextFieldFormElement = new JTextFieldFormElement<T>(new JTextField(), labelText,
                                                                                    beanProperty);
      FormPanel p =  new FormPanel<JTextField>(jTextFieldFormElement,getConstraints(JTextField.class),resourceBundle);
		createdForm.add(jTextFieldFormElement);
		return p;
	}

	public FormPanel<JTextField> decotrateJTextField(
			String labelText,
			Property beanProperty
	){
      JTextFieldFormElement<T> jTextFieldFormElement = new JTextFieldFormElement<T>(new JTextField(), labelText,
                                                                                    beanProperty);
      FormPanel p =  new FormPanel<JTextField>(jTextFieldFormElement,getConstraints(JTextField.class), resourceBundle);
		createdForm.add(jTextFieldFormElement);
		return p;
	}

	public String getLayout() {
		return layout;
	}

	public Map<String, String> getConstraints() {
		return constraints;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public void setConstraints(Map<String, String> constraints) {
		this.constraints = constraints;
	}

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }
}
