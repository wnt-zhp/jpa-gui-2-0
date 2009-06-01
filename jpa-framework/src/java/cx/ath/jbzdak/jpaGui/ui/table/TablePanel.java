package cx.ath.jbzdak.jpaGui.ui.table;

import javax.swing.JPanel;
import javax.swing.JTable;

import java.awt.BorderLayout;

public class TablePanel extends JPanel{

	private static final long serialVersionUID = 1L;

	public TablePanel(JTable table){
		super(new BorderLayout());
		add(table, BorderLayout.CENTER);
		add(table.getTableHeader(), BorderLayout.NORTH);
	}

}
