package cx.ath.jbzdak.jpaGui.ui.autoComplete;

import org.jdesktop.beansbinding.Converter;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-20
 */
public class BackwardConverter<T,V> extends Converter<T,V>{

   private final Converter<V,T> converter;

   public BackwardConverter(Converter<V, T> converter) {
      this.converter = converter;
   }

   @Override
   public V convertForward(T t) {
      return converter.convertReverse(t);
   }

   @Override
   public T convertReverse(V v) {
      return converter.convertForward(v);
   }
}
