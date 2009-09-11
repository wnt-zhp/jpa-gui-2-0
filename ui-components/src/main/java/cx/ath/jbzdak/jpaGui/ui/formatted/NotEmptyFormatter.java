package cx.ath.jbzdak.jpaGui.ui.formatted;

import cx.ath.jbzdak.jpaGui.FormattingException;
import cx.ath.jbzdak.jpaGui.ParsingException;
import static org.apache.commons.lang.StringUtils.isBlank;

public class NotEmptyFormatter<T> extends AbstractFormatter<String, T> {

	private boolean acceptEmpty; 
	
	private final String errorMessage;


	@SuppressWarnings({"SameParameterValue"})
   private NotEmptyFormatter(boolean acceptEmpty, String errorMessage) {
		super(new FormatterEventHandlerImpl());
		this.acceptEmpty = acceptEmpty;
		this.errorMessage = errorMessage;
      this.eventHandler.setFormatter(this);
	}

	@SuppressWarnings({"WeakerAccess", "SameParameterValue"})
   public NotEmptyFormatter(String errorMessage) {
		this(false, errorMessage);
	}

	public NotEmptyFormatter() {
		this("Nie wprowadzono wartości");
	}

	@Override
	public String parseValue(String text) throws Exception {
		if( !acceptEmpty && isBlank(text)){
			throw new ParsingException(errorMessage);
		}
		return text!=null?text:"";
	}

   @Override
   public String formatValue(T value) throws FormattingException {
      if(value==null){
         throw new FormattingException(errorMessage, "");
      }
      return value.toString();
   }

   boolean isAcceptEmpty() {
		return acceptEmpty;
	}


	void setAcceptEmpty(boolean acceptEmpty) {
      if(this.acceptEmpty != acceptEmpty){
         this.acceptEmpty = acceptEmpty;
         fireFormatterChanged();
      }


	}



}
