package cx.ath.jbzdak.jpaGui;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-07-11
 */
public interface BeanHolderAware<B, BH extends BeanHolder<? extends B>> {
   void setBeanHolder(BH beanHolder);
}
