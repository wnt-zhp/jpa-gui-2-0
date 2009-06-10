package cx.ath.jbzdak.jpaGui.ui.formatted;


public class NoopFormatter implements MyFormatter {

	@Override
	public String formatValue(Object value) throws FormattingException {
		return value==null?"": value.toString();
	}

	@Override
	public Object parseValue(String text) throws Exception {
		return text;
	}

}
