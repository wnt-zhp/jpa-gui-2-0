package cx.ath.jbzdak.jpaGui.ui.autoComplete;

import cx.ath.jbzdak.jpaGui.Formatter;
import cx.ath.jbzdak.jpaGui.Utils;
import cx.ath.jbzdak.jpaGui.ui.autoComplete.adapter.NoopAdaptor;
import cx.ath.jbzdak.jpaGui.ui.formatted.formatters.ToStringFormatter;
import static cx.ath.jbzdak.jpaGui.utils.BeansBindingUtils.createAutoBinding;
import org.apache.commons.lang.StringUtils;
import static org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ;
import org.jdesktop.beansbinding.Binding;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Collection;

/**
 * Kombo box z wsparciem autoimport cx.ath.jbzdak.zarlok.ui.autocolmpete.adaptor.PartiaAdaptor;
matycznego uzupełniania.
 * <p>
 * Żeby działał trzeba tylko podać mu {@link AutoCompleteAdaptor},
 * który enkapsuluje proces wyszukiwania i dawania podpowiedzi.
 * </p>
 * <p>Opakowuje podanego mu edytora w instancję {@link WrappedEditor}. </p>
 * @author jb
 *
 */
public class AutocompleteComboBox<V> extends JComboBox {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Adapter enkapsuluje proces generowania podpowiedzi.
	 */
	private AutoCompleteAdaptor<V> adaptor;

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


   private Formatter<? extends V, ? super V> formatter = new ToStringFormatter<V>();

	private boolean strict = false;

	public boolean isError(){
		return getSelectedValue() == null;
	}

	public AutocompleteComboBox(){
		this(new NoopAdaptor<V>());

	}


	public AutocompleteComboBox(AutoCompleteAdaptor<V> adaptor) {
	    this(adaptor, false);
	}

	@SuppressWarnings({"SameParameterValue"})
   public AutocompleteComboBox(AutoCompleteAdaptor<V> adaptor, boolean strict) {
		super();
		setAdaptor(adaptor);
		setFilter("");
		setEditable(true);
		getModel().setElementClass(adaptor.getValueClass());
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
		if(editor!=null){
			editor = new WrappedEditor(editor, this);
			editor.getEditorComponent().addFocusListener(new FocusListener());
		}
		super.setEditor(editor);
		if(editor!=null){
			bindFilter();
		}
	}

	@Override
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

   protected V parseSelectedItem(String s){
      try{
         return strict?null:formatter.parseValue(s);
      }catch (Exception e){
         return null;
      }
   }

	/**
	 * Ustawia i przesyła do {@link AutoCompleteAdaptor} filtr.
	 * @param filter fliter do wywołania
	 */
	public void setFilter(String filter){
		if(adaptor == null || !adaptor.ignoreFilter(this.filter, filter)){
			try{
            ignoreConfigure = true;
            this.filter = filter;
            if(isStrict()){
               setSelectedItem(null);
            }else{
               setSelectedItem(parseSelectedItem(filter));
            }
				getAdaptor().setFilter(StringUtils.defaultString(filter));
			}finally{
				ignoreConfigure = false;
			}
		}
	}

	public AutoCompleteAdaptor<V> getAdaptor() {
		if(adaptor==null){
			adaptor = new NoopAdaptor<V>();
		}
		return adaptor;
	}


	public void setAdaptor(AutoCompleteAdaptor<V> adaptor) {
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
	public void setValues(Collection values){
		if(values == null){
			throw new NullPointerException();
		}
		ignoreConfigure = true;
		try{
			selectedItemReminder = getModel().getSelectedItem();
			getModel().setContents(values);
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

	protected void setAutoCompleteModel(MyComboBoxModel<V> autoCompleteModel) {
		if(super.getModel() != autoCompleteModel){
			setModel(autoCompleteModel);
      }
	}

   @Override
   public  MyComboBoxModel<V> getModel() {
      if(super.getModel()==null){
			setAutoCompleteModel(new MyComboBoxModel<V>(true));
		}
      return (MyComboBoxModel<V>) super.getModel();
   }



   

   @Override
	public void configureEditor(ComboBoxEditor anEditor, Object anItem) {
		if(!ignoreConfigure) {
         super.configureEditor(anEditor, anItem);
      }
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
      if(model instanceof MyComboBoxModel){
         super.setModel(model);
		   model.removeListDataListener(this);//Idea jest taka że
      }
	}

	public V getSelectedValue(){
		if(getSelectedItem()!=null){
			return getSelectedItem();
		}
		return  parseSelectedItem(filter);
	}


	private void selectedValueChanged(Object old, Object newValue){
		firePropertyChange("selectedValue", old, newValue);
		firePropertyChange("error", old!=null, newValue!=null);
	}

	@Override
	public void setSelectedItem(Object anObject) {
		Object oldSelectedItem = getSelectedItem();
		boolean isFromModel = getModel().contains(anObject);
		if(strict && (anObject==null || !isFromModel)){
			return;
		}
		super.setSelectedItem(anObject);
		selectedValueChanged(oldSelectedItem, anObject);
        if(!Utils.equals(oldSelectedItem, anObject)){
            firePropertyChange("selectedItem", oldSelectedItem, anObject);            
        }
	}

   @Override
   public V getSelectedItem() {
      return getModel().getSelectedItem();
   }

   public void clear(){
      setSelectedItem(null);
      getEditor().setText("");
      selectedItemReminder = null;
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
			if(getSelectedItem()!=null){
				getEditor().setItem(getSelectedItem());
			}
		}
	}

	public boolean isStrict() {
		return strict;
	}

	public void setStrict(boolean strict) {
		this.strict = strict;
	}

   public Formatter<? extends V, ? super V> getFormatter() {
      return formatter;
   }

   public void setFormatter(Formatter<? extends V, ? super V> formatter) {
      this.formatter = formatter;
   }

   /**
     * Wiem że nie nadpisują tego ale innej opcji nie ma.
     * Bug polega na tym że {@link DefaultCellEditor} woła tą metodę
     * na bezpośrednio.
     */
    @Override
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
