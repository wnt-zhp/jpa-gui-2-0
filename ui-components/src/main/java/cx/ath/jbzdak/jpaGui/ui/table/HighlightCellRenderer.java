package cx.ath.jbzdak.jpaGui.ui.table;

import cx.ath.jbzdak.jpaGui.MyFormatter;
import cx.ath.jbzdak.jpaGui.ui.formatted.formatters.ToStringFormatter;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.painter.AlphaPainter;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.MattePainter;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public abstract class HighlightCellRenderer extends JXLabel implements TableCellRenderer {

	private static final long serialVersionUID = 1L;

	private final TableCellRenderer cellRenderer = createCellRender();

	private final MattePainter backgroundPainter = new MattePainter();

	private final MattePainter highlightPainter = new MattePainter(getHightlightColor());

	private final AlphaPainter<JXLabel> alphaPainter = new AlphaPainter<JXLabel>();
	{
		alphaPainter.setAlpha(0.5f);
		alphaPainter.setPainters(highlightPainter);
	}

	private final CompoundPainter<JXLabel> cellPainter = new CompoundPainter<JXLabel>(backgroundPainter,alphaPainter);

	private MyFormatter formatter = new ToStringFormatter();

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		JComponent c = (JComponent) cellRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);
		backgroundPainter.setFillPaint(c.getBackground());
		setForeground(c.getForeground());
		setBorder(c.getBorder());
		if(isHighlighted(table, value, isSelected, hasFocus, row, column) && !isSelected){
			setBackgroundPainter(cellPainter);
		}else{
			setBackgroundPainter(backgroundPainter);
		}
		getTableCellRendererEntry(table, value, isSelected, hasFocus, row, column);
		return this;
	}

	@SuppressWarnings({"WeakerAccess"})
   protected TableCellRenderer createCellRender() {
		return new DefaultTableCellRenderer();
	}

	/**
	 * True jeśli dana komórka ma być podświetlona.
	 * @param table
	 * @param value
	 * @param isSelected
	 * @param hasFocus
	 * @param row
	 * @param column
	 * @return
	 */
	protected abstract boolean isHighlighted(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column);

	/**
	 * Ustawia kolor podświetlenia. Funkcja ta jest wywoływana raz przy
	 * tworzeniu obiektu.
	 * @return
	 */
	@SuppressWarnings({"SameReturnValue", "WeakerAccess"})
   protected Color getHightlightColor(){
		return Color.YELLOW;
	}

	/**
	 * Wywoływane bezpośrednio przed wyjściem z metody
	 * {@link #getTableCellRendererComponent(JTable, Object, boolean, boolean, int, int)}
	 * w ten sposób można jeszcze cos ustawić i tak dalej. /
	 *
	 * @param table
	 * @param value
	 * @param isSelected
	 * @param hasFocus
	 * @param row
	 * @param column
	 */
	protected abstract void getTableCellRendererEntry(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column);

	public MyFormatter getFormatter() {
		return formatter;
	}

	public void setFormatter(MyFormatter formatter) {
		this.formatter = formatter;
	}


}
