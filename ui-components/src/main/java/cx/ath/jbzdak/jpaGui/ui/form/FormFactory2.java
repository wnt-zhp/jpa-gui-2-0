package cx.ath.jbzdak.jpaGui.ui.form;

import javax.swing.*;
import java.util.Map;
import java.util.ResourceBundle;

import cx.ath.jbzdak.jpaGui.Formatter;
import cx.ath.jbzdak.jpaGui.ui.autoComplete.AutoCompleteAdaptor;
import cx.ath.jbzdak.jpaGui.ui.autoComplete.AutocompleteComboBox;
import cx.ath.jbzdak.jpaGui.ui.autoComplete.ComboBoxElement;
import cx.ath.jbzdak.jpaGui.ui.formatted.FormattedFieldElement;
import cx.ath.jbzdak.jpaGui.ui.formatted.FormattedTextField;

@SuppressWarnings({"unchecked", "WeakerAccess"})
public class FormFactory2<T, F extends Form<T, FormElement<?, T, ?>>>  {

   public static <T, F extends Form<T, FormElement<?, T, ?>>> FormFactory2<T, F> createFactory(F form){
      return new FormFactory2<T,F>(form);      
   }

   private ResourceBundle resourceBundle;

   private final F createdForm;

   private String layout = "default";

   private Map<String,String> constraints;

   public FormFactory2(F createdForm) {
      this.createdForm = createdForm;
   }

   public F getCreatedForm() {
      return createdForm;
   }

   private Map<String, String> getConstraints(Class clazz){
      if(constraints!=null){
         return constraints;
      }
      return FormPanelConstraints.getConstraints(layout, clazz);
   }

   @SuppressWarnings({"WeakerAccess"})
   public FormPanel<FormattedTextField, FormattedFieldElement<T, ?>> decorateFormattedTextField(
           String labelText,
           String beanProperty,
           FormattedTextField field
   ){
      FormattedFieldElement<T, ?> elem = new FormattedFieldElement<T, Object>(field, labelText, resourceBundle, beanProperty);
      FormPanel p =  new FormPanel<FormattedTextField, FormattedFieldElement<T, ?>>(elem, getConstraints(field.getClass()));

      createdForm.add(elem);

      return p;
   }

   public FormPanel<FormattedTextField, FormattedFieldElement<T, ?>> decorateFormattedTextField(
           String labelText,
           String beanProperty,
           Formatter formatter
   ){
      return decorateFormattedTextField(labelText, beanProperty, new FormattedTextField(formatter));
   }

   public <V> FormPanel<AutocompleteComboBox<V>, ComboBoxElement<T, V>> decotrateComboBox(
           String labelText,
           String beanProperty,
           AutocompleteComboBox field
   ){
      ComboBoxElement<T, ?> element = new ComboBoxElement<T, Object>(field, labelText, resourceBundle, beanProperty);
      FormPanel<AutocompleteComboBox<V>, ComboBoxElement<T, V>> p =  new FormPanel(element, getConstraints(field.getClass()));
      createdForm.add(element);
      return p;
   }

   public <V> FormPanel<AutocompleteComboBox<V>, ComboBoxElement<T, V>> decorateComboBox(
           String labelText,
           String beanProperty,
           AutoCompleteAdaptor field
   ){
      return decotrateComboBox(labelText, beanProperty, new AutocompleteComboBox(field));
   }
   public <V> FormPanel<AutocompleteComboBox<V>, ComboBoxElement<T, V>> decotrateComboBox(
           String labelText,
           String beanProperty,
           AutoCompleteAdaptor field,
           boolean strict
   ){
      return decotrateComboBox(labelText, beanProperty, new AutocompleteComboBox(field, strict));
   }

   public FormPanel<JTextField, JTextFieldFormElement<T>> decotrateJTextField(
           String labelText,
           String beanProperty
   ){
      JTextFieldFormElement<T> jTextFieldFormElement = new JTextFieldFormElement<T>(new JTextField(), labelText, resourceBundle,
              beanProperty);
      FormPanel p =  new FormPanel<JTextField, JTextFieldFormElement<T>>(jTextFieldFormElement,getConstraints(JTextField.class));
      createdForm.add(jTextFieldFormElement);
      return p;
   }

//    public FormPanel<JTextField, JTextFieldFormElement<T>> decotrateJTextField(
//            String labelText,
//            Property beanProperty
//    ){
//        JTextFieldFormElement<T> jTextFieldFormElement = new JTextFieldFormElement<T>(new JTextField(), labelText,
//                beanProperty);
//        FormPanel p =  new FormPanel<JTextField, JTextFieldFormElement<T>>(jTextFieldFormElement,getConstraints(JTextField.class), resourceBundle);
//        createdForm.add(jTextFieldFormElement);
//        return p;
//    }

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
