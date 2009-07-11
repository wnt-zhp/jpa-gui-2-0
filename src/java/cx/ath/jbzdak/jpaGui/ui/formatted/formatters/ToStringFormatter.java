package cx.ath.jbzdak.jpaGui.ui.formatted.formatters;

import cx.ath.jbzdak.jpaGui.ui.formatted.FormattingException;
import cx.ath.jbzdak.jpaGui.ui.formatted.MyFormatter;

public class ToStringFormatter<V> implements MyFormatter<V, Object> {

	@Override
	public String formatValue(Object value) throws FormattingException {
		return value!=null?value.toString():"";
	}

	@Override
	public V parseValue(String text) throws Exception {
		throw new UnsupportedOperationException();
	}

}
