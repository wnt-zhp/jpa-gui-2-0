package cx.ath.jbzdak.jpaGui;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-07-03
 */
public class AnnotationUtils {

   public static <T extends AnnotatedElement> T findByAnnotatio(Class<? extends Annotation> annotation, T[] elements){
      for (T element : elements) {
         if (element.isAnnotationPresent(annotation)) {
            return element;
         }
      }
      return null;
   }

   public static Object getProperty(Object bean, Member member){
      if (member instanceof Method) {
         Method m = (Method) member;
         try {
            return m.invoke(bean);
         } catch (Exception e) {
           Utils.throwRuntime(e);
         }
      }
      if(member instanceof Field){
         String getterName = getAccesorName("get", member.getName());
         try {
            Method m = bean.getClass().getMethod(getterName);
            return m.invoke(bean);
         } catch (Exception e) {
            Utils.throwRuntime(e);
         }
      }
      throw new IllegalArgumentException();
   }

   static String getAccesorName(String prefix, String name){
      if(name.isEmpty()){
         throw new IllegalArgumentException();
      }
      return prefix + Character.toUpperCase(name.charAt(0)) + name.substring(1);
   }

}
