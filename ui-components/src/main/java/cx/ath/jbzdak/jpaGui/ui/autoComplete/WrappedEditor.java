package cx.ath.jbzdak.jpaGui.ui.autoComplete;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cx.ath.jbzdak.jpaGui.FormattingException;
import cx.ath.jbzdak.jpaGui.genericListeners.DoStuffDocumentListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.text.JTextComponent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Edytor używany przez {@link AutocompleteComboBox},
 * opakowuje on dowolnego innego edytora który używa
 * {@link JTextComponent} jako editorComponent.
 *
 *	Główną rzeczą jest to że sprytnie wysłuchuje zmian w tekście {@link JComboBox}.
 *	Idea jest taka żeby nie odpalać eventa o zmianie filtra jeśli to nie użytkownik
 * 	zmienił zawatrość pola. Więc zmiant w zawatości pola tekstowego (własność text)
 *  są progragowane tylko jeśli poprzedził je {@link KeyEvent}.
 *
 *    Drugą rzeczą jest rozróżnienie między filtrem a txtem. Filtr to wartość wprowadzona
 *    do pola przez użytkownika - text to wartość funkcji getText z wywołanej na
 *    editorComponent, która może być podmieniona programowo.
 * @author jb
 *
 */
public class WrappedEditor implements ComboBoxEditor{

   private static final Logger LOGGER = LoggerFactory.getLogger(WrappedEditor.class);

	private final ComboBoxEditor wrapped;

   private final AutocompleteComboBox autocompleteComboBox;

	private final PropertyChangeSupport support
		= new PropertyChangeSupport(this);

	private String filter;

	private boolean ignoreSetText = false;

   private boolean formatItems = true;

	public WrappedEditor(ComboBoxEditor wrapped, AutocompleteComboBox autocompleteComboBox) {
		super();
		this.wrapped = wrapped;
      this.autocompleteComboBox = autocompleteComboBox;
      final JTextComponent textComponent = getEditorComponent();
      textComponent.getDocument().addDocumentListener(new DoStuffDocumentListener() {
         @Override
         protected void doStuff(DocumentEvent e) {
            if( !ignoreSetText){ setFilter(textComponent.getText()); }
            }
      });
	}

	@Override
   public void addActionListener(ActionListener l) {
		wrapped.addActionListener(l);
	}

	@Override
   public JTextComponent getEditorComponent() {
		return (JTextComponent) wrapped.getEditorComponent();
	}

	@Override
   public Object getItem() {
		return wrapped.getItem();
	}

	@Override
   public void removeActionListener(ActionListener l) {
		wrapped.removeActionListener(l);
	}

	@Override
   public void selectAll() {
		wrapped.selectAll();
	}

	@Override
   public void setItem(Object anObject) {
		try{
			ignoreSetText = true;
         if(formatItems){
            try {
               wrapped.setItem(autocompleteComboBox.getFormatter().formatValue(anObject));
            } catch (FormattingException e) {
               LOGGER.debug("Error while formatting value {}", e);
               wrapped.setItem(anObject);
            }
         }else{
            wrapped.setItem(anObject);
         }
		}finally{
			ignoreSetText = false;
		}
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String text) {
		String oldtext = this.filter;
		this.filter = text;
		support.firePropertyChange("filter", oldtext, text);
	}

	@SuppressWarnings({"SameParameterValue"})
   public void setText(String text){
		try{
			ignoreSetText = true;
			getEditorComponent().setText(text);
		}finally{
			ignoreSetText = false;
		}
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		support.addPropertyChangeListener(propertyName, listener);
	}

	public PropertyChangeListener[] getPropertyChangeListeners() {
		return support.getPropertyChangeListeners();
	}

	public PropertyChangeListener[] getPropertyChangeListeners(
			String propertyName) {
		return support.getPropertyChangeListeners(propertyName);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		support.removePropertyChangeListener(propertyName, listener);
	}

   ComboBoxEditor getWrapped() {
      return wrapped;
   }

   public boolean isFormatItems() {
      return formatItems;
   }

   public void setFormatItems(boolean formatItems) {
      this.formatItems = formatItems;
   }

}
