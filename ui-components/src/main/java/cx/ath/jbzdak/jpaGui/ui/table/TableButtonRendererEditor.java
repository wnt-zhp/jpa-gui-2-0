package cx.ath.jbzdak.jpaGui.ui.table;

import org.apache.commons.collections.functors.CloneTransformer;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-11-17
 */
public class TableButtonRendererEditor extends JPanel implements TableCellEditor, TableCellRenderer{

   final List<CellEditorListener> listenerList = new ArrayList<CellEditorListener>();

   final ChangeEvent changeEvent = new ChangeEvent(this);

   private Object value;

   final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

   public TableButtonRendererEditor() {
      super(new BorderLayout());
   }

   @Override
   public void addCellEditorListener(CellEditorListener l) {
      listenerList.add(l);
   }

   @Override
   public Object getCellEditorValue() {
      return null;
   }

   @Override
   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      JComponent result = (JComponent) renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      setValue(value);
      setFont(result.getFont());
      setForeground(result.getForeground());
      setBackground(result.getBackground());
      try {
         setBorder((Border) CloneTransformer.getInstance().transform(result.getBorder()));
      } catch (IllegalStateException e) {
         setBorder(result.getBorder());
      }
      return this; 
   }

   @Override
   public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
      return getTableCellRendererComponent(table, value, isSelected, true, row, column);
   }

   @Override
   public boolean isCellEditable(EventObject anEvent) {
      return true;
   }

   @Override
   public boolean shouldSelectCell(EventObject anEvent) {
      return true;
   }

   @Override
   public boolean stopCellEditing() {
      setValue(null);
      for(CellEditorListener listener : listenerList){
         listener.editingStopped(changeEvent);
      }
      return false;
   }

   @Override
   public void cancelCellEditing() {
      setValue(null);
      for(CellEditorListener listener : listenerList){
         listener.editingCanceled(changeEvent);
      }
   }

   @Override
   public void removeCellEditorListener(CellEditorListener l) {
      listenerList.remove(l);
   }

   public Object getValue() {
      return value;
   }

   public void setValue(Object value) {
      Object oldValue = this.value;
      this.value = value;
      firePropertyChange("value", oldValue, this.value);
   }
}
