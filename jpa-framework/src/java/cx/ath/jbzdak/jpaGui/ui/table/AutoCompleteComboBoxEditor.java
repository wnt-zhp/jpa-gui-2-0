package cx.ath.jbzdak.jpaGui.ui.table;

import cx.ath.jbzdak.jpaGui.autoComplete.AutoCompleteValueHolder;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;

public class AutoCompleteComboBoxEditor extends DefaultCellEditor{

	private static final long serialVersionUID = 1L;

	public AutoCompleteComboBoxEditor(JComboBox comboBox) {
		super(comboBox);
	}

	@Override
	public Object getCellEditorValue() {
		Object value = super.getCellEditorValue();
		if(value == null){
			return null;
		}
		if (value instanceof AutoCompleteValueHolder) {
			AutoCompleteValueHolder acvh = (AutoCompleteValueHolder) value;
			return acvh.getValue();
		}
		return value;
	}

}
