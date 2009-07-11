package cx.ath.jbzdak.jpaGui.ui.error;

import cx.ath.jbzdak.jpaGui.ClassHandler;
import cx.ath.jbzdak.jpaGui.ui.error.ErrorHandlers.Formatter;
import javax.swing.table.AbstractTableModel;

import java.util.List;

public class ErrorTableModel extends AbstractTableModel {

	private static final String[] names = {"Błąd", "Detale błędu"};

	private static final long serialVersionUID = 1L;


	private final ClassHandler<Formatter> messageHandlers = ErrorHandlers.createShortHandlers();

	private List<?> errors;

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		return errors.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object error = errors.get(rowIndex);
		switch(columnIndex){
		case 0:
			return messageHandlers.getHandler(error).getMessage(error);
		case 1:
			return error;
		default:
			throw new IndexOutOfBoundsException("There are two columns. Tried to access " + columnIndex);
		}
	}

	@Override
	public String getColumnName(int column) {
		return names[column];
	}

	public List<?> getErrors() {
		return errors;
	}

	public void setErrors(List<?> errors) {
		this.errors = errors;
		fireTableDataChanged();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 1;
	}


}
