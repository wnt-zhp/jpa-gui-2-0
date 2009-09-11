package cx.ath.jbzdak.jpaGui.ui.table;

import javax.swing.*;
import java.awt.*;

public class TablePanel extends JPanel{

	private static final long serialVersionUID = 1L;

	public TablePanel(JTable table){
		super(new BorderLayout());
		add(table, BorderLayout.CENTER);
		add(table.getTableHeader(), BorderLayout.NORTH);
	}

}
