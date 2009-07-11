package cx.ath.jbzdak.jpaGui.ui.error;

import cx.ath.jbzdak.common.famfamicons.IconManager;
import cx.ath.jbzdak.jpaGui.ClassHandler;
import static cx.ath.jbzdak.jpaGui.Utils.initLocation;
import cx.ath.jbzdak.jpaGui.genericListeners.HideWindowActionListener;
import cx.ath.jbzdak.jpaGui.ui.ButtonFactory;
import cx.ath.jbzdak.jpaGui.ui.error.ErrorHandlers.Formatter;
import edu.umd.cs.findbugs.annotations.CheckForNull;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import net.miginfocom.swing.MigLayout;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

public class ErrorDialog extends JDialog {

	private static final long serialVersionUID = 1L;

   public static void displayErrorDialog(List<Object> errors, @CheckForNull Frame frame){
      ErrorDialog errorDialog = new ErrorDialog(frame);
      errorDialog.setErrors(errors);
      errorDialog.setVisible(true);
   }

	List<?> errors = Collections.emptyList();

	final JTable table;
   
	final ErrorTableModel model = new ErrorTableModel();
	{
		table = new JTable();
		model.setErrors(errors);
		table.setModel(model);
		TableColumn detailsColumn = table.getColumnModel().getColumn(1);
		detailsColumn.setCellEditor(new DetailRenderer());
		detailsColumn.setCellRenderer(new DetailRenderer());
		detailsColumn.setPreferredWidth(IconManager.getIconSafe("error_go").getIconWidth()+ 5);
		table.getColumnModel().getColumn(0).setPreferredWidth(300);
	}

	final JButton closeButton;
	{
		closeButton = ButtonFactory.makeButton("dismiss");
		closeButton.addActionListener(new HideWindowActionListener(this));
	}


	private final ClassHandler<Formatter> detailsHandlers = ErrorHandlers.createLongHandlers();

	public ErrorDialog() {
		super();
		initialize();
	}

	public ErrorDialog(@CheckForNull Frame owner, boolean modal) {
		super(owner, modal);
		initialize();
	}

	public ErrorDialog(@CheckForNull Frame owner) {
		super(owner);
		initialize();
	}

	private void initialize() {
		setLayout(new MigLayout("fillx", "[fill]"));
		add(new JScrollPane(table));
		add(closeButton, "tag finished, dock south");
		pack();
		initLocation(this);
	}

	private class DetailRenderer extends AbstractCellEditor implements
			TableCellRenderer, TableCellEditor {

		private static final long serialVersionUID = 1L;

		Object value;

		final JButton rendererEditor;

		final DisplayErrorDetailsDialog dialog = new DisplayErrorDetailsDialog(
				ErrorDialog.this);

		{
			rendererEditor = new JButton(IconManager.getIconSafe("error_go"));
			rendererEditor.setBorderPainted(false);
			rendererEditor.setContentAreaFilled(false);
			rendererEditor.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dialog.setText(detailsHandlers.getHandler(value).getMessage(value));
					dialog.setVisible(true);
				}
			});
		}

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			return rendererEditor;
		}

		@Override
		public Object getCellEditorValue() {
			return value;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			this.value = value;
			return rendererEditor;
		}

	}


	public void setErrors(List<?> errors) {
		this.errors = errors;
		model.setErrors(errors);
	}

	@Override
	public void setVisible(boolean b) {
		pack();
		super.setVisible(b);
	}
}
