package cx.ath.jbzdak.jpaGui.ui.formatted.formatters;

import cx.ath.jbzdak.jpaGui.FormattingException;
import cx.ath.jbzdak.jpaGui.ui.formatted.AbstractFormatter;

public class ToStringFormatter<V> extends AbstractFormatter<V, Object> {

	@Override
	public String formatValue(Object value) throws FormattingException {
		return value!=null?value.toString():"";
	}

	@Override
	public V parseValue(String text) throws Exception {
		throw new UnsupportedOperationException();
	}

}
