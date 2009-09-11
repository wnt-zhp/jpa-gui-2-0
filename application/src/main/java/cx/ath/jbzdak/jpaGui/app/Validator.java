package cx.ath.jbzdak.jpaGui.app;

import cx.ath.jbzdak.jpaGui.MyFormatter;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-22
 */
public interface Validator extends MyFormatter {

   public abstract void validate(Object o) throws ValidationException;

   public Class getComvertToClass();

}
