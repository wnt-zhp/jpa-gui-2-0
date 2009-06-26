package cx.ath.jbzdak.jpaGui.app;

import java.util.List;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-22
 */
public interface ConfigEntry<T> {

   String getName();

   Class<T> getValueClass();

   boolean isSingle();

   T getSingleValue();

   List<T> getValues();

   String getShortDescription();

   String getLongDescription();

   void setValues(List<T> values) throws ValidationException;

   void setSingleValue(T t) throws ValidationException;

}
