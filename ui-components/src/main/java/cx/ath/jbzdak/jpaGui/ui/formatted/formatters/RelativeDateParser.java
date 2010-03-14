package cx.ath.jbzdak.jpaGui.ui.formatted.formatters;

import cx.ath.jbzdak.jpaGui.ParsingException;
import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RelativeDateParser {

	private static final Pattern zaNDni = Pattern.compile("za\\s+(\\w+)\\s+dni");
	private static final Pattern nDniTemu = Pattern.compile("(\\w+)\\s+dni temu");
	private static final Pattern plusMinusPattern = Pattern.compile("([+-])?\\s*(\\d+)");

	private static final String[] dni = {"dwa", "trzy", "cztery", "pięć", "sześć"};


	public Integer parseDate(String representation) throws ParsingException{
		return parseDate(representation, true);
	}

	@SuppressWarnings({"ReuseOfLocalVariable", "ReuseOfLocalVariable"})
   public Integer parseDate(String representation, boolean throwOnNoMatch) throws ParsingException {
		representation = representation.trim();
		Matcher match =zaNDni.matcher(representation);
		if(match.matches()){
			return parseDniWord(match.group(1));
		}
		match = nDniTemu.matcher(representation);
		if(match.matches()){
			return - parseDniWord(match.group(1));
		}
		match = plusMinusPattern.matcher(representation);
		if(match.matches()){
			boolean minus = false;
			if(!StringUtils.isEmpty(match.group(1))){
				minus = "-".equals(match.group(1));
			}
			int result = parseDniWord(match.group(2));
         if(result > 10){
         }
			if(minus) {
            return -result;
         }
			return result;
		}
		Integer result = parseOneWordRepresenattion(representation);
		if(result!=null){
			return result;
		}
		if(throwOnNoMatch){
			throw new ParsingException("Nieznany format");
		}else{
			return null;
		}
	}

	@SuppressWarnings({"WeakerAccess"})
   public int parseDniWord(String word) throws ParsingException{
		try{
			int dni = Integer.parseInt(word);
         if(dni > 10){
            throw new ParsingException("Względne daty można podawać tylko do 10 dni");
         }
		}catch (NumberFormatException e) {
         //Jak wyjątek -- idziemy dalej
		}
		for(int ii=0; ii < dni.length; ii++){
			if(dni[ii].equals(word)){
				return ii+2;
			}
		}
		throw new ParsingException("Błędny format. Nieznane słwo '" + word + "'");
	}

	private Integer parseOneWordRepresenattion(String representation){
		if(representation.matches("dzi[sś]")){
			return 0;
		}
		if(representation.matches("jutro")){
			return 1;
		}
		if(representation.matches("pojutrze")){
			return 2;
		}
		if(representation.matches("wczo.?")){
			return -1;
		}
		if(representation.matches("wczor.?")){
			return -1;
		}
		if(representation.matches("wczor[ea]j")){
			return -1;
		}
		if(representation.matches("wczor[ea]j")){
			return -1;
		}
		if(representation.matches("przedwczor[ea]j")){
			return -2;
		}
		return null;
	}


}
