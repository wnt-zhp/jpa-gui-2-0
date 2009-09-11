package cx.ath.jbzdak.jpaGui.ui.formatted;

import java.awt.event.ActionListener;

/**
 * @param <PARSE_RESULT>
 * @param <FORMAT_ARG>
 */
public interface MyFormatter<PARSE_RESULT, FORMAT_ARG> {

   public PARSE_RESULT parseValue(String text) throws Exception;

	public String formatValue(FORMAT_ARG value) throws FormattingException;

   public void addFormatterChangedListener(ActionListener actionListener);

   public void removeFormatterChangedListener(ActionListener actionListener);

}
