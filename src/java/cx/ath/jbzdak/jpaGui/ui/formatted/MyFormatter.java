package cx.ath.jbzdak.jpaGui.ui.formatted;

import java.awt.event.ActionListener;

/**
 * @param <V>
 * @param <V2>
 */
public interface MyFormatter<V, V2> {

   public V parseValue(String text) throws Exception;

	public String formatValue(V2 value) throws FormattingException;

   public void addFormatterChangedListener(ActionListener actionListener);

   public void removeFormatterChangedListener(ActionListener actionListener);

}
