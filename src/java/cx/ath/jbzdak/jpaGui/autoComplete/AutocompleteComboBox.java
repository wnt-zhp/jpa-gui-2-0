package cx.ath.jbzdak.jpaGui.autoComplete;

import cx.ath.jbzdak.jpaGui.Utils;
import static cx.ath.jbzdak.jpaGui.Utils.createAutoBinding;
import cx.ath.jbzdak.jpaGui.autoComplete.adapter.NoopAdaptor;
import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import org.apache.commons.lang.StringUtils;
import static org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ;
import org.jdesktop.beansbinding.Binding;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

/**
 * Kombo box z wsparciem autoimport cx.ath.jbzdak.zarlok.ui.autocolmpete.adaptor.PartiaAdaptor;
matycznego uzupełniania.
 * <p>
 * Żeby działał trzeba tylko podać mu {@link AutoCompleteAdaptor},
 * który enkapsuluje proces wyszukiwania i dawania podpowiedzi.
 * </p>
 * <p>Opakowuje podanego mu edytora w instancję {@link WrappedEditor}. </p>
 * <p> Co więcej wartości przechowywane w modelu muszą być instancjami
 * {@link AutoCompleteValueHolder}.</p>
 * @author jb
 *
 */
public class AutocompleteComboBox extends JComboBox {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Adapter enkapsuluje proces generowania podpowiedzi.
	 */
	private AutoCompleteAdaptor<AutoCompleteValueHolder> adaptor;

	/**
	 * Ostatnio wklepany przez usera filtr
	 */
	private String filter = "";

	/**
	 * Wiązanie łączące listę z podpowiedziami
	 * w adapterze z zawartościa tego combo boxa.
	 * Patrz {@link #bindAdaptor()}
	 */
	@SuppressWarnings("unchecked")
	private Binding adaptorBinding;

	/**
	 * Wiązanie łączące pole Filtr z edytora
	 * z filtrem w tej klasie.
	 * Patrz: {@link #bindFilter()}
	 */
	@SuppressWarnings("unchecked")
	private Binding filterBinding;

	/**
	 * <p>
	 * Taki hak... </br>
	 * Klasa {@link BasicComboBoxUI} na każdym evencie ze zmianą danych
	 * generowanym przez model próbuje za pomocą {@link #configureEditor(ComboBoxEditor, Object)}
	 * ustawić w edytorze wyświetlanie wybranej wartości. Co powoduje wyjąte i ogólnie chrzani życie. </p>
	 *
	 * <p>Dzieje się to podczas ustawiania filtra z edytora.</p>
	 *
	 * <p>Więc w funkcji {@link #setFilter(String)} ustawiamy to pole na true wywołujemy metodę
	 * powodującą wytwołanie na modelu eventa i spowrotem na false. <br/>
	 * Uff </p>
	 */
	protected boolean ignoreConfigure = false;

	/**
	 * Lekko zmodyfikowany model.
	 * Ogólnie model mysi być tej klasy.
	 * Patrz
	 */
	private MyComboBoxModel autoCompleteModel;

	private boolean strict = false;

	private boolean clear;

	private boolean ignoreError;

//	/**
//	 * Listener dodawany do edytora. Robi prawie dokładnie to co
//	 * {@link JComboBox#actionPerformed(ActionEvent)}. Normalnie
//	 * bym overridenął {@link #actionPerformed(ActionEvent)}, ale
//	 * proszą żeby nie robić tego.
//	 */
//	private ActionListener editorListener = new ActionListener(){
//		  public void actionPerformed(ActionEvent e) {
//		        Object newItem = getEditor().getItem();
//		        setPopupVisible(false);
//		        setSelectedItem(newItem);
//				String oldCommand = getActionCommand();
//				setActionCommand("comboBoxEdited");
//				fireActionEvent();
//				setActionCommand(oldCommand);
//		    }
//	};

	public boolean isError(){
		return getSelectedValue() == null || ignoreError;
	}

	public AutocompleteComboBox(){
		this(new NoopAdaptor<AutoCompleteValueHolder>());

	}


	public AutocompleteComboBox(AutoCompleteAdaptor<AutoCompleteValueHolder> adaptor) {
	    this(adaptor, false);
	}

	public AutocompleteComboBox(AutoCompleteAdaptor<AutoCompleteValueHolder> adaptor, boolean strict) {
		super();
		setAdaptor(adaptor);
		setFilter("");
		setEditable(true);
		getAutoCompleteModel(); //for side-effect
		setValues(adaptor.getCurentFilteredResults());
        setStrict(strict);
	}

	/**
	 * Edytor może być dowolny, ale zostanie opakowany obiektem klasy
	 * {@link WrappedEditor}. Który zarządza wywoływaniem metod filtrujących.
	 *
	 */
	@Override
	public void setEditor(ComboBoxEditor editor) {
		if(filterBinding!=null){
			filterBinding.unbind();
		}
//		if(getEditor()!=null){
//			getEditor().removeActionListener(editorListener);
//		}
		if(editor!=null){
			editor = new WrappedEditor(editor);
			editor.getEditorComponent().addFocusListener(new FocusListener());
		}
		super.setEditor(editor);
		if(editor!=null){
			bindFilter();
		}
//		editor.removeActionListener(this);
//		editor.addActionListener(editorListener);
	}

	public WrappedEditor getEditor(){
		return (WrappedEditor) super.getEditor();
	}


	private void bindAdaptor(){
		adaptorBinding = createAutoBinding(READ, adaptor, "curentFilteredResults", this, "values");
		adaptorBinding.bind();
	}

	private void bindFilter(){
		filterBinding = createAutoBinding(READ, getEditor(), "filter", this, "filter");
		filterBinding.bind();
	}

	/**
	 * Ustawia i przesyła do {@link AutoCompleteAdaptor} filtr.
	 * @param filter fliter do wywołania
	 */
	public void setFilter(String filter){
		//Object oldSelectedItem = null;
		if(!Utils.equals(filter, this.filter)){
			try{
				ignoreConfigure = true;
				getAdaptor().setFilter(StringUtils.defaultString(filter));
			}finally{
				ignoreConfigure = false;
			}
		}
		ignoreConfigure = true;
		try{
			//if(!isBlank(filter)){//TODO co ten warunek tu robi...
				if(isStrict()){
					setSelectedItem(null);
				}else{
					setSelectedItem(getAdaptor().getValueHolderFromFilter());
				}
			//}
		}finally{
			ignoreConfigure = false;
		}
		this.filter = filter;
	}

	public AutoCompleteAdaptor<AutoCompleteValueHolder> getAdaptor() {
		if(adaptor==null){
			adaptor = new NoopAdaptor<AutoCompleteValueHolder>();
		}
		return adaptor;
	}


	public void setAdaptor(AutoCompleteAdaptor<AutoCompleteValueHolder> adaptor) {
		if(this.adaptorBinding != null){
			adaptorBinding.unbind();
		}
		if(adaptor!=null && getAdaptor()!=null){
			adaptor.setFilter(getAdaptor().getFilter());
		}
		this.adaptor = adaptor;
		if(adaptor!=null){
			bindAdaptor();
		}
	}

	/**
	 * Ustawia podpowiedzi. Oraz jeśli edytor ma focusa, to wyświetla popupa.
	 * @param values
	 */
	@SuppressWarnings("unchecked")
	public void setValues(List values){
		if(values == null){
			throw new NullPointerException();
		}
		ignoreConfigure = true;
		try{
			if(!values.isEmpty() && !(values.get(0)instanceof AutoCompleteValueHolder)){
				for(int ii=0; ii < values.size();ii++){
					values.set(ii, new AutoCompleteValueHolder(null, values.get(ii)));
				}
			}
			selectedItemReminder = getModel().getSelectedItem();
			getAutoCompleteModel().setContents(values);
			if(getEditor().getEditorComponent().isFocusOwner()){
				if(isPopupVisible()){
					hidePopup();
				}
				if(isShowing()){
					showPopup();
				}
			}
		}finally{
			ignoreConfigure = false;
		}
	}

	/**
	 * Do testów.
	 * @param args
	 */
	@Deprecated
	public static void main(String[] args) {
		JFrame f = new JFrame();
		AutocompleteComboBox box = new AutocompleteComboBox();
		f.add(box, BorderLayout.CENTER);
		f.pack();
		f.setVisible(true);
	}

	public MyComboBoxModel getAutoCompleteModel() {
		if(autoCompleteModel==null){
			setAutoCompleteModel(new MyComboBoxModel(true));
		}
		return autoCompleteModel;
	}

	protected void setAutoCompleteModel(MyComboBoxModel autoCompleteModel) {
		if(this.autoCompleteModel != autoCompleteModel){
			setModel(autoCompleteModel);
		}
		this.autoCompleteModel = autoCompleteModel;
	}

	@Override
	public void configureEditor(ComboBoxEditor anEditor, Object anItem) {
		if(!ignoreConfigure)
			super.configureEditor(anEditor, anItem);
	}

	/**
	 * Oczekujemy instancji {@link MyComboBoxModel}.
	 * {@link DefaultComboBoxModel} ustawia selectedItem w dziwnych momentach,
	 * co powoduje nieprzewidywanle zachowanie pola podpowiedzi.
	 * Mój model nie ma tego ficzera.
	 *
	 * JComboBox też instaluje się jako listener na tym modelu,
	 * ta metoda usuwa <code>this</code> jako listenera. Jest to używane
	 * tylko do zmiany selectedItem przy zmianie zawartości modelu,
	 * ale nasz model nigdy tego nie zmieni. Do tego są bugi w implemenacji
	 * powodują niepotrzebne wywoływanie tej metody...
	 */
	@Override
	public void setModel(ComboBoxModel model) {
		super.setModel(model);
		model.removeListDataListener(this);//Idea jest taka że
	}

	public AutoCompleteValueHolder getSelectedValue(){
		if(getSelectedItem()!=null && (getSelectedItem() instanceof AutoCompleteValueHolder)){
			return (AutoCompleteValueHolder) getSelectedItem();
		}
		if(strict){
			return null;
		}else{
			return getAdaptor().getValueHolderFromFilter();
		}
	}

	public Object getBeanValue(){
		AutoCompleteValueHolder r = getSelectedValue();
		return r==null?null:r.getValue();
	}

	public void setBeanValue(Object o){
		setSelectedItem(getAdaptor().getValueHolderProperty(o));
	}

	private void selectedValueChanged(Object old, Object newValue){
		if (old instanceof AutoCompleteValueHolder) {
			AutoCompleteValueHolder tmp = (AutoCompleteValueHolder) old;
			old = tmp.getValue();
		}
		if(newValue instanceof AutoCompleteValueHolder){
			newValue = ((AutoCompleteValueHolder) newValue).getValue();
		}
		firePropertyChange("selectedValue", old, newValue);
		if(!ignoreError){
			firePropertyChange("error", old!=null, newValue!=null);
		}
	}

	@Override
	public void setSelectedItem(Object anObject) {
		Object oldSelectedItem = getSelectedItem();
		boolean isFromModel = anObject instanceof AutoCompleteValueHolder;
		AutoCompleteValueHolder holder = (AutoCompleteValueHolder) (isFromModel?anObject:null);
		if(strict && anObject==null){
			return;
		}
		if(anObject!=null){
			if(strict && (!isFromModel || holder.isAutoCreated())){
				return;
			}
			if(!isFromModel){
				anObject = getAdaptor().getValueHolderProperty(anObject);
			}
		}
		super.setSelectedItem(anObject);
		selectedValueChanged(oldSelectedItem, anObject);
        if(!Utils.equals(oldSelectedItem, anObject)){
            firePropertyChange("selectedItem", oldSelectedItem, anObject);            
        }
	}


	public void clear(){
		if(clear){
			setSelectedItem(null);
			getEditor().setText("");
			selectedItemReminder = null;
		}
	}

	public boolean isClear() {
		return clear;
	}

	public void setClear(boolean clear) {
		this.clear = clear;
	}

	private class FocusListener extends FocusAdapter{
		@Override
		public void focusGained(FocusEvent e) {
			setFilter(getEditor().getFilter());
			getEditor().getEditorComponent().setBackground(UIManager.getColor("TextField.background"));
			showPopup();
		}

		@Override
		public void focusLost(FocusEvent e) {
			Object selectedItem = getSelectedItem();
			if(selectedItem!=null){
				getEditor().setItem(getSelectedItem());
				return;
			}
			if(strict){
				if(!ignoreError){
					getEditor().getEditorComponent().setBackground(Color.PINK);
					getEditor().setText("");
				}
				return;
			}
			AutoCompleteValueHolder holder = getSelectedValue();
			if(holder==null){
				getEditor().getEditorComponent().setBackground(Color.PINK);
				getEditor().setText("");
			}
		}
	}

	public boolean isStrict() {
		return strict;
	}

	public void setStrict(boolean strict) {
		this.strict = strict;
	}

	public boolean isIgnoreError() {
		return ignoreError;
	}

	public void setIgnoreError(boolean ignoreError) {
		this.ignoreError = ignoreError;
	}

    /**
     * Wiem że nie nadpisują tego ale innej opcji nie ma.
     * Bug polega na tym że {@link DefaultCellEditor} woła tą metodę
     * na bezpośrednio.
     */
    public void actionPerformed(ActionEvent e) {
        Object newItem = getEditor().getItem();
        setPopupVisible(false);
        setSelectedItem(newItem);
		String oldCommand = getActionCommand();
		setActionCommand("comboBoxEdited");
		fireActionEvent();
		setActionCommand(oldCommand);
    }
}
