package cx.ath.jbzdak.jpaGui.ui.formatted;

import static org.apache.commons.lang.StringUtils.isBlank;

public class NotEmptyFormatter extends NoopFormatter {

	private boolean acceptEmpty; 
	
	private final String errorMessage;


	@SuppressWarnings({"SameParameterValue"})
   private NotEmptyFormatter(boolean acceptEmpty, String errorMessage) {
		super();
		this.acceptEmpty = acceptEmpty;
		this.errorMessage = errorMessage;
	}


	@SuppressWarnings({"WeakerAccess", "SameParameterValue"})
   public NotEmptyFormatter(String errorMessage) {
		this(false, errorMessage);
	}

	public NotEmptyFormatter() {
		this("Nie wprowadzono wartości");
	}
	@Override
	public Object parseValue(String text) throws Exception {
		if( !acceptEmpty && isBlank(text)){
			throw new ParsingException(errorMessage);
		}
		return super.parseValue(text);
	}


	boolean isAcceptEmpty() {
		return acceptEmpty;
	}


	void setAcceptEmpty(boolean acceptEmpty) {
		this.acceptEmpty = acceptEmpty;
	}



}
