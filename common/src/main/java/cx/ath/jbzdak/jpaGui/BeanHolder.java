package cx.ath.jbzdak.jpaGui;

/**
 *
 * Coś co przechowuje beana
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-06-30
 */

public interface BeanHolder<B> {
   void setBean(B bean);

   B getBean();
}
