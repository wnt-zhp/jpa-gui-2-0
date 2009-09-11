package cx.ath.jbzdak.jpaGui.ui.formatted.formatters;

import cx.ath.jbzdak.jpaGui.FormattingException;
import cx.ath.jbzdak.jpaGui.ParsingException;
import cx.ath.jbzdak.jpaGui.ui.formatted.AbstractFormatter;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.util.Calendar;
import static java.util.Calendar.DAY_OF_MONTH;
import java.util.Date;

public class DateFormatter extends AbstractFormatter<Date, Object> {

	protected final DateParser parser = new DateParser();

	protected final RelativeDateParser dateParser = new RelativeDateParser();

	protected final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);

	@Override
	public String formatValue(Object value) throws FormattingException {
		if (value == null){
			return "";
		}
		if (value instanceof Number) {
			Number dayOffset = (Number) value;
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH,dayOffset.intValue());
			return formatDate(cal.getTime());
		}
		if(value instanceof Date){
			return formatDate((Date) value);
		}
		return value.toString();
	}

	public String formatDate(Date date){
		return dateFormat.format(date);
	}

	@Override
	public Date parseValue(String text) throws Exception {
      if(StringUtils.isBlank(text)){
         Date result = handleNullDate();
         if(result!=null){
            return  result;
         }
         throw new ParsingException("Pole musi zostać wypełnione");
      }
		Integer result = dateParser.parseDate(text, false);
		if(result == null){
			return parser.parse(text);
		}
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		cal.add(DAY_OF_MONTH, result);
		return cal.getTime();
	}

	@SuppressWarnings({"SameReturnValue"})
   protected Date handleNullDate(){
		return null;
	}

}
