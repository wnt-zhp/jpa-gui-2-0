package cx.ath.jbzdak.jpaGui.ui.formatted;

import cx.ath.jbzdak.jpaGui.MyFormatter;

import java.awt.event.ActionListener;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-07-12
 */
public abstract class AbstractFormatter<V, V2> implements MyFormatter<V,V2> {

   protected final FormatterEventHandler eventHandler;

   protected AbstractFormatter() {
      this(new NoopHandler());
   }

   protected AbstractFormatter(FormatterEventHandler eventHandler) {
      this.eventHandler = eventHandler;
   }

   @Override
   public void addFormatterChangedListener(ActionListener actionListener) {
      eventHandler.addFormatterChangedListener(actionListener);
   }

   @Override
   public void removeFormatterChangedListener(ActionListener actionListener) {
      eventHandler.removeFormatterChangedListener(actionListener);
   }

   protected void fireFormatterChanged() {eventHandler.fireFormatterChanged();}

}
