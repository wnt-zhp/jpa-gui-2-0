package cx.ath.jbzdak.jpaGui.ui.formatted;

import cx.ath.jbzdak.jpaGui.MyFormatter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-07-12
 */
public class FormatterEventHandlerImpl implements FormatterEventHandler{

   private final List<ActionListener> listeners = new ArrayList<ActionListener>();

   private ActionEvent formatterChanged;
   
   private MyFormatter formatter;

   public FormatterEventHandlerImpl() {
   }

   public FormatterEventHandlerImpl(MyFormatter formatter) {
      this.formatter = formatter;
   }

   @Override
   public void setFormatter(MyFormatter formatter) {
      if(this.formatter!=formatter){
         this.formatter = formatter;
         formatterChanged = null;
      }
   }

   @Override
   public void addFormatterChangedListener(ActionListener actionListener) {
      listeners.add(actionListener);
   }

   @Override
   public void removeFormatterChangedListener(ActionListener actionListener) {
      listeners.remove(actionListener);
   }

   @Override
   public void fireFormatterChanged(){
      for(ActionListener al : listeners){
         al.actionPerformed(formatterChanged);
      }
   }

   protected ActionEvent getFormatterChanged() {
      if (formatterChanged == null) {
        formatterChanged = new ActionEvent(formatter, 0, "FORMATTER_CHANGED");
      }
      return formatterChanged;
   }
}
