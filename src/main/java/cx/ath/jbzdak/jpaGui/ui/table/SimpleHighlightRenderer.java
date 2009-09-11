package cx.ath.jbzdak.jpaGui.ui.table;

import javax.swing.JTable;

public abstract class SimpleHighlightRenderer extends HighlightCellRenderer{

	private static final long serialVersionUID = 1L;

	@Override
	protected void getTableCellRendererEntry(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		setText(value==null?"":value.toString());

	}

}
