package cx.ath.jbzdak.jpaGui.ui.formatted;

import cx.ath.jbzdak.jpaGui.MyFormatter;

import java.awt.event.ActionListener;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-07-12
 */
public interface FormatterEventHandler {


   public void addFormatterChangedListener(ActionListener actionListener);

   public void removeFormatterChangedListener(ActionListener actionListener);

   public void fireFormatterChanged();

   public void setFormatter(MyFormatter formatter);
}
