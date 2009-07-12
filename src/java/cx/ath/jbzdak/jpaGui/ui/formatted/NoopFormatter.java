package cx.ath.jbzdak.jpaGui.ui.formatted;


public class NoopFormatter<T> extends AbstractFormatter<String, T>{

	@Override
	public String formatValue(T value) throws FormattingException {
		return value==null?"": value.toString();
	}

	@Override
	public String parseValue(String text) throws Exception {
		return text;
	}

  
}
