package cx.ath.jbzdak.jpaGui.autoComplete;

import org.jdesktop.beansbinding.Converter;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-20
 */
public class UnwrapConverter<T> extends Converter<T, AutoCompleteValueHolder>{

   private static final Converter<?, AutoCompleteValueHolder> unwrapConverter = new UnwrapConverter();

   private static final Converter<AutoCompleteValueHolder, ?> backward =
           new BackwardConverter(unwrapConverter);

   public static <T>  Converter<T, AutoCompleteValueHolder> getInstance(){
      return (Converter<T, AutoCompleteValueHolder>) unwrapConverter;
   }

   public static <T> Converter<T, AutoCompleteValueHolder> getReverse(){
      return (Converter<T, AutoCompleteValueHolder>) backward;
   }



   @Override
   public AutoCompleteValueHolder convertForward(T t) {
      return new AutoCompleteValueHolder(String.valueOf(t), t);
   }

   @Override
   public T convertReverse(AutoCompleteValueHolder autoCompleteValueHolder) {
      return (T) autoCompleteValueHolder.getValue();
   }
}
