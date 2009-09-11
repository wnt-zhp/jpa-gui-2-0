package cx.ath.jbzdak.jpaGui.ui.formatted.formatters;


public class DoubleFormatter extends NumberFormatter<Double>{

	@Override
	protected Double parseResult(String text) {
		return Double.valueOf(text);
	}

	
}
