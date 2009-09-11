package cx.ath.jbzdak.jpaGui.ui.formatted;

import cx.ath.jbzdak.jpaGui.MyFormatter;

import java.awt.event.ActionListener;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-07-12
 */
public class NoopHandler implements FormatterEventHandler{
   @Override
   public void addFormatterChangedListener(ActionListener actionListener) {  }

   @Override
   public void removeFormatterChangedListener(ActionListener actionListener) { }

   @Override
   public void fireFormatterChanged() {  }

   @Override
   public void setFormatter(MyFormatter formatter) {  }
}
