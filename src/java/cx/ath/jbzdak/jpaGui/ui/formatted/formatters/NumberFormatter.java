package cx.ath.jbzdak.jpaGui.ui.formatted.formatters;

import cx.ath.jbzdak.jpaGui.ui.formatted.FormattingException;
import cx.ath.jbzdak.jpaGui.ui.formatted.MyFormatter;
import cx.ath.jbzdak.jpaGui.ui.formatted.ParsingException;

import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class NumberFormatter<N extends Number> implements MyFormatter{
	private static final Pattern numberPattern = Pattern.compile("(?:\\d*([.,]))?\\d+");

	private final NumberFormat format = NumberFormat.getNumberInstance();

	protected abstract N parseResult(String text);

	@Override
	public String formatValue(Object value) throws FormattingException {
		return value==null?"":format.format(value);
	}

	@Override
	public N parseValue(String text) throws Exception {
		if(text==null){
			throw new NullPointerException();
		}
		Matcher m = numberPattern.matcher(text);
		if(!m.matches()){
			throw new ParsingException("Nieprawidłowy format liczby");
		}
		if(m.group(1)!=null && !".".equals(m.group(1))){
			text = text.replace(m.group(1), ".");
		}
		N result = parseResult(text);
		return result;
	}
}