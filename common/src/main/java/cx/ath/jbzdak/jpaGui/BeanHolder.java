package cx.ath.jbzdak.jpaGui;

import cx.ath.jbzdak.common.PropertySupported;
import cx.ath.jbzdak.common.annotation.property.Bound;

/**
 *
 * Coś co przechowuje beana
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-06-30
 */

public interface BeanHolder<B> extends PropertySupported{
   void setBean(B bean);

   @Bound
   B getBean();

   
}
