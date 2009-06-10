package cx.ath.jbzdak.jpaGui.ui.formatted;

public interface MyFormatter {

   public Object parseValue(String text) throws Exception;

	public String formatValue(Object value) throws FormattingException;

}
