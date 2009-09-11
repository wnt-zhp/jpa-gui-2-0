package cx.ath.jbzdak.jpaGui;

import cx.ath.jbzdak.jpaGui.ui.formatted.FormattingException;
import cx.ath.jbzdak.jpaGui.ui.formatted.MyFormatter;
import org.jdesktop.beansbinding.Converter;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-17
 */
public class FormatterConverter extends Converter{

    private final MyFormatter formatter;

    public FormatterConverter(MyFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public Object convertForward(Object o) {
        try {
            return formatter.formatValue(o);
        } catch (FormattingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object convertReverse(Object o) {
        try {
            return formatter.parseValue(o!=null?String.valueOf(o):null);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
