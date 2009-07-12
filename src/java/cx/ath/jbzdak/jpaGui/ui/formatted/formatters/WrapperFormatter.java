package cx.ath.jbzdak.jpaGui.ui.formatted.formatters;

import cx.ath.jbzdak.jpaGui.ui.formatted.AbstractFormatter;
import cx.ath.jbzdak.jpaGui.ui.formatted.FormattingException;

import java.text.Format;

public class WrapperFormatter extends AbstractFormatter {

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
