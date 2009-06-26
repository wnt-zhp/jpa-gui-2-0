package cx.ath.jbzdak.jpaGui.ui.formatted.formatters;

import cx.ath.jbzdak.jpaGui.ui.formatted.FormattingException;
import cx.ath.jbzdak.jpaGui.ui.formatted.MyFormatter;

import java.text.Format;

public class WrapperFormatter implements MyFormatter {

	private final Format internal;

	public WrapperFormatter(Format internal) {
		super();
		this.internal = internal;
	}

	@Override
	public String formatValue(Object value) throws FormattingException {
		return internal.format(value);
	}

	@Override
	public Object parseValue(String text) throws Exception {
		return internal.parseObject(text);
	}

}
