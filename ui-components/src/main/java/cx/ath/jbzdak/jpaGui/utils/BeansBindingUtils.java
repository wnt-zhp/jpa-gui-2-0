package cx.ath.jbzdak.jpaGui.utils;

import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.Bindings;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-11
 */
public class BeansBindingUtils {
   
   public static Binding createAutoBinding(
           AutoBinding.UpdateStrategy strategy,
           Object source, String sourceProperty, Object target,
           String targeTProperty) {
      return Bindings.createAutoBinding(strategy, source, BeanProperty
              .create(sourceProperty), target, BeanProperty
              .create(targeTProperty));
   }
   
}
