package cx.ath.jbzdak.jpaGui.ui.formatted.formatters;

import cx.ath.jbzdak.jpaGui.ui.formatted.ParsingException;
import static org.apache.commons.lang.StringUtils.isEmpty;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateParser {


	Pattern datePattern = Pattern.compile("\\s*(\\d{1,2})\\s*[\\:\\;\\-\\.\\,]\\s*(\\d{1,2})\\s*(?:[\\:\\;\\-\\.\\,]\\s*(\\d{1,4}))?\\s*");

	public Date parse(String text) throws ParsingException{
		Matcher m = datePattern.matcher(text);
		if(!m.matches()){
			throw new ParsingException("Błędny format daty: '" + text + "'");
		}
		Calendar c = Calendar.getInstance();
		String rok = m.group(3);
		String miesiac = m.group(2);
		String dzien = m.group(1);
		if(!isEmpty(rok)){
			Calendar tmp = Calendar.getInstance();
			tmp.setTime(new Date());
			int parsedRok = Integer.parseInt(rok);
			if(parsedRok < 30){
				parsedRok+=2000;
			   }
			c.set(Calendar.YEAR, parsedRok);
		}else{
			Calendar tmp = Calendar.getInstance();
			tmp.setTime(new Date());
			c.set(Calendar.YEAR, tmp.get(Calendar.YEAR));
		}
		int miesiacInt = Integer.parseInt(miesiac);
		int miesiacMax = c.getActualMaximum(Calendar.YEAR);
		int miesiacMin = c.getActualMinimum(Calendar.YEAR);
		if(miesiacInt > miesiacMax || miesiacInt < miesiacMin){
			throw new ParsingException("Błędny format daty. Niepoprawny numer miesiąca: '" + miesiacInt + "'");
		}
		c.set(Calendar.MONTH, miesiacInt-1); //Styczeń to zero!
		int dzienInt = Integer.parseInt(dzien);
		int dzienMax = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		int dzienMin = c.getActualMinimum(Calendar.DAY_OF_MONTH);
		if(dzienInt > dzienMax || dzienInt < dzienMin){
			throw new ParsingException("Błędny format daty. Niepoprawny numer dnia: '" + dzienInt + "'");
		}
		c.set(Calendar.DAY_OF_MONTH, dzienInt);
		return c.getTime();
	}

}
