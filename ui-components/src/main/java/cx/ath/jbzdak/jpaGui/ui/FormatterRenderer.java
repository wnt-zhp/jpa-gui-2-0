package cx.ath.jbzdak.jpaGui.ui;

import static cx.ath.jbzdak.jpaGui.Utils.makeLogger;
import cx.ath.jbzdak.jpaGui.Formatter;
import cx.ath.jbzdak.jpaGui.FormattingException;
import org.slf4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class FormatterRenderer extends DefaultTableCellRenderer{

   public FormatterRenderer(Formatter formatter) {
		super();
		this.formatter = formatter;
	}

	private static final long serialVersionUID = 1L;

	private static final Logger logger = makeLogger();

	private final Formatter formatter;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if(value==null){
			logger.debug("value is null "  + formatter.getClass());
		}
		try {
			return super.getTableCellRendererComponent(table, formatter.formatValue(value), isSelected, hasFocus,
					row, column);
		} catch (FormattingException e) {
			logger.warn("",e);
			return super.getTableCellRendererComponent(table, "", isSelected, hasFocus,
					row, column);
		}
	}

}
