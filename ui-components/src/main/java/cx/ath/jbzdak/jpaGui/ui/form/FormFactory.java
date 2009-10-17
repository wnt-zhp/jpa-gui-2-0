package cx.ath.jbzdak.jpaGui.ui.form;

import cx.ath.jbzdak.jpaGui.MyFormatter;
import cx.ath.jbzdak.jpaGui.ui.autoComplete.AutocompleteComboBox;
import cx.ath.jbzdak.jpaGui.ui.autoComplete.ComboBoxElement;
import cx.ath.jbzdak.jpaGui.ui.formatted.FormattedFieldElement;
import cx.ath.jbzdak.jpaGui.ui.formatted.MyFormattedTextField;
import org.jdesktop.beansbinding.Property;

import javax.swing.*;
import java.util.Map;
import java.util.ResourceBundle;

@SuppressWarnings({"unchecked", "WeakerAccess"})
public class FormFactory<T>  {

    private ResourceBundle resourceBundle;

    private final DAOForm<T, FormElement<?, T, ?>> createdForm = new DAOForm<T,FormElement<?, T, ?>>();

    private String layout = "default";

    private Map<String,String> constraints;

    public DAOForm<T,FormElement<?, T, ?>> getCreatedForm() {

        return createdForm;
    }

    private Map<String, String> getConstraints(Class clazz){
        if(constraints!=null){
            return constraints;
        }
        return FormPanelConstraints.getConstraints(layout, clazz);
    }

    @SuppressWarnings({"WeakerAccess"})
    public FormPanel<MyFormattedTextField, FormattedFieldElement<T, ?>> decorateFormattedTextField(
            String labelText,
            String beanProperty,
            MyFormattedTextField field
    ){
        FormattedFieldElement<T, ?> elem = new FormattedFieldElement<T, Object>(field, labelText, beanProperty);
        FormPanel p =  new FormPanel<MyFormattedTextField, FormattedFieldElement<T, ?>>(elem, getConstraints(field.getClass()), resourceBundle);

        createdForm.add(elem);

        return p;
    }

    public FormPanel<MyFormattedTextField, FormattedFieldElement<T, ?>> decorateFormattedTextField(
            String labelText,
            String beanProperty,
            MyFormatter formatter
    ){
        return decorateFormattedTextField(labelText, beanProperty, new MyFormattedTextField(formatter));
    }

    public FormPanel decotrateComboBox(
            String labelText,
            String beanProperty,
            AutocompleteComboBox field
    ){
        ComboBoxElement<T, ?> element = new ComboBoxElement<T, Object>(field, labelText, beanProperty);
        FormPanel p =  new FormPanel(element, getConstraints(field.getClass()),resourceBundle);
        createdForm.add(element);
        return p;
    }

    public FormPanel<JTextField, JTextFieldFormElement<T>> decotrateJTextField(
            String labelText,
            String beanProperty
    ){
        JTextFieldFormElement<T> jTextFieldFormElement = new JTextFieldFormElement<T>(new JTextField(), labelText,
                beanProperty);
        FormPanel p =  new FormPanel<JTextField, JTextFieldFormElement<T>>(jTextFieldFormElement,getConstraints(JTextField.class),resourceBundle);
        createdForm.add(jTextFieldFormElement);
        return p;
    }

    public FormPanel<JTextField, JTextFieldFormElement<T>> decotrateJTextField(
            String labelText,
            Property beanProperty
    ){
        JTextFieldFormElement<T> jTextFieldFormElement = new JTextFieldFormElement<T>(new JTextField(), labelText,
                beanProperty);
        FormPanel p =  new FormPanel<JTextField, JTextFieldFormElement<T>>(jTextFieldFormElement,getConstraints(JTextField.class), resourceBundle);
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
