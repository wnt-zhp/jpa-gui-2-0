package cx.ath.jbzdak.jpaGui.ui.formatted.formatters;

import java.math.BigDecimal;

public class BigDecimalFormatter extends NumberFormatter<BigDecimal> {

	@Override
	protected BigDecimal parseResult(String text) {
		return new BigDecimal(text);
	}

}
