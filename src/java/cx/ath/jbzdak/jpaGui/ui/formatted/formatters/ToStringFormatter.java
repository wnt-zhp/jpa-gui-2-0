package cx.ath.jbzdak.jpaGui.ui.formatted.formatters;

import cx.ath.jbzdak.jpaGui.ui.formatted.FormattingException;
import cx.ath.jbzdak.jpaGui.ui.formatted.MyFormatter;

public class ToStringFormatter implements MyFormatter {

	@Override
	public String formatValue(Object value) throws FormattingException {
		return value!=null?value.toString():"";
	}

	@Override
	public Object parseValue(String text) throws Exception {
		throw new UnsupportedOperationException();
	}

}
