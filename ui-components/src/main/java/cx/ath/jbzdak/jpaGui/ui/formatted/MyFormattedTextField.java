package cx.ath.jbzdak.jpaGui.ui.formatted;

import cx.ath.jbzdak.jpaGui.FormattingException;
import cx.ath.jbzdak.jpaGui.MyFormatter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.Serializable;

public class MyFormattedTextField<V> extends JTextField{

	private static final long serialVersionUID = 1L;

	private MyFormatter<?extends V, ? super V> formatter;

	/**
	 * Prawdziwe jeśli tekst odpowiada wartości w polu.
	 */
	private boolean valueCurrent;

	private boolean ignoreSetText;

   private boolean parsingText;

	private V value;

	private Exception parseResults;

	private String userEnteredText;

	private boolean clearTextField;

	private boolean separateuserTextAndValue= true;

	private boolean echoErrorsAtOnce = false;

   //Listeners
	private TextListener listener;

   private final ActionListener formatterListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
         attemptParseText();
         formatValue();
      }
   };

   @SuppressWarnings({"WeakerAccess"})
   public MyFormattedTextField() {
		super();
		addFocusListener(new FocusListener());
		setColumns(6);
	}

	public MyFormattedTextField(MyFormatter<?extends V, ? super V> formatter) {
		this();
		setFormatter(formatter);
	}

	@Override
	public void setDocument(Document doc) {
		if(getDocument()!=null){
			getDocument().removeDocumentListener(getListener());
		}
		super.setDocument(doc);
		doc.addDocumentListener(getListener());
	}

	TextListener getListener() {
		if(listener == null){
			listener = new TextListener();
		}
		return listener;
	}

   public Exception getParseResults() {
		return parseResults;
	}

	void setParseResults(Exception parseResults) {
		Exception oldErrors = this.parseResults;
		this.parseResults = parseResults;
		firePropertyChange("parseResults", oldErrors, parseResults);
		firePropertyChange("hadParseErrors", oldErrors!=null, parseResults!=null);

	}

	public V getValue() {
		return value;
	}

	@SuppressWarnings({"WeakerAccess"})
   public void setValue(V value) {
		Object oldValue = this.value;
		this.value = value;
		firePropertyChange("value", oldValue, this.value);
      setParseResults(null);
	   formatValue();
	}

	public void setValueFromBean(V value) {
		setValue(value);
		formatValue();
		userEnteredText = getText();
	}

	public boolean isValueCurrent() {
		return valueCurrent;
	}

	void setValueCurrent(boolean valueCurrent) {
		boolean old = this.valueCurrent;
		this.valueCurrent = valueCurrent;
		firePropertyChange("valueCurrent", old, this.valueCurrent);
	}

	@SuppressWarnings({"WeakerAccess"})
   public  void formatValue(){
		ignoreSetText = true;
		try{
         if(!parsingText){
			   setText(formatter.formatValue(getValue()));
         }
		} catch (FormattingException e) {
         setParseResults(e);
		}finally{
			ignoreSetText = false;
		}
	}

	public String getUserEnteredText() {
		return userEnteredText;
	}

	public void setUserEnteredText(String userEnteredText) {
		String oldText = this.userEnteredText;
		this.userEnteredText = userEnteredText;
		firePropertyChange("userEnteredText", oldText, this.userEnteredText);
	}

	public void attemptParseText(){
      parsingText = true;
		try {
			setValue(formatter.parseValue(getUserEnteredText()));
			setValueCurrent(true);
			setParseResults(null);
			setBackground(UIManager.getColor("TextField.background"));
		} catch (Exception e) {
			setParseResults(e);
			setValueCurrent(false);
			if(echoErrorsAtOnce){
				setBackground(Color.PINK);
			}
		}finally {
         parsingText = false; 
      }
	}

	@SuppressWarnings({"MethodOnlyUsedFromInnerClass"})
   private void onTextChange(){
		if(!ignoreSetText){
			setUserEnteredText(getText());
			attemptParseText();
		}
	}

	private class TextListener implements DocumentListener, Serializable{
		private static final long serialVersionUID = 1L;
		@Override
		public void changedUpdate(DocumentEvent e) { onTextChange(); }
		@Override
		public void insertUpdate(DocumentEvent e) { onTextChange();	}
		@Override
		public void removeUpdate(DocumentEvent e) {	onTextChange();	}
	}

	private class FocusListener extends FocusAdapter implements Serializable{
		private static final long serialVersionUID = 1L;
		@Override
		public void focusGained(FocusEvent e) {
			if(separateuserTextAndValue){
				setText(userEnteredText);
			}
			setBackground(UIManager.getColor("TextField.background"));
		}

		@Override
		public void focusLost(FocusEvent e) {
			if(separateuserTextAndValue){
				formatValue();
			}
			if(parseResults!=null){
				setBackground(Color.PINK);
			}
		}
	}

	public void clear(){
		if(clearTextField){
			setUserEnteredText("");
			setText("");
		}
	}

	public boolean getHadParseErrors(){
		return parseResults!=null;
	}

	public boolean isClearTextField() {
		return clearTextField;
	}

	public void setClearTextField(boolean clearTextField) {
		this.clearTextField = clearTextField;
	}

	public MyFormatter getFormatter() {
		return formatter;
	}

	@SuppressWarnings({"WeakerAccess"})
   public void setFormatter(MyFormatter<? extends V, ? super V> formatter) {
      if(this.formatter != formatter){
         boolean oldEcho = echoErrorsAtOnce;
         if(this.formatter!=null){
            this.formatter.removeFormatterChangedListener(formatterListener);
         }
         this.formatter = formatter;
         if(this.formatter!=null){
            this.formatter.addFormatterChangedListener(formatterListener);
         }
         try{
            echoErrorsAtOnce = false;
            attemptParseText();
         }finally {
            echoErrorsAtOnce = oldEcho;
         }
      }

	}

	public boolean isSeparateuserTextAndValue() {
		return separateuserTextAndValue;
	}

	public void setSeparateuserTextAndValue(boolean separateuserTextAndValue) {
		this.separateuserTextAndValue = separateuserTextAndValue;
	}

	public boolean isEchoErrorsAtOnce() {
		return echoErrorsAtOnce;
	}

	public void setEchoErrorsAtOnce(boolean echoErrorsAtOnce) {
		this.echoErrorsAtOnce = echoErrorsAtOnce;
	}


}
