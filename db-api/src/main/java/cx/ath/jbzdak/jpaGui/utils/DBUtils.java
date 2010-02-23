package cx.ath.jbzdak.jpaGui.utils;

import cx.ath.jbzdak.jpaGui.AnnotationUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-11
 */
public class DBUtils {

   public static boolean willAutoSetId(Object object){
      AnnotatedElement id = getIDAnnotatedElement(object);
      if(!id.isAnnotationPresent(GeneratedValue.class)){
         return false;
      }
      if (id instanceof Member) {
         Member member = (Member) id;
         return AnnotationUtils.getProperty(object, member)==null;
      }
      throw new IllegalStateException();
   }

   private static AnnotatedElement getIDAnnotatedElement(Object object){
      AnnotatedElement id = AnnotationUtils.findByAnnotatio(Id.class, object.getClass().getDeclaredFields());
      if (id == null) {
         id = AnnotationUtils.findByAnnotatio(Id.class, object.getClass().getDeclaredMethods());
      }
      return id;
   }

   private static Member getIDMember(Object object){
      Member id = AnnotationUtils.findByAnnotatio(Id.class, object.getClass().getDeclaredFields());
      if (id == null) {
         id = AnnotationUtils.findByAnnotatio(Id.class, object.getClass().getDeclaredMethods());
      }
      return id;
   }

   public static Object getId(Object object) {
      if(object==null){
         throw new IllegalArgumentException();
      }
      return AnnotationUtils.getProperty(object, getIDMember(object));
   }

   public static Object setId(Object object, Object value, Class valueClass) {
       if(object==null){
          throw new IllegalArgumentException();
       }
       return AnnotationUtils.setProperty(object, getIDMember(object), value, valueClass);
    }



	/**
	 * Sprawdza czy pole annotowane annotacją {@link Id} jest nullem czy nie.
	 * Jeśli klasa nie ma takiego pola leci {@link IllegalArgumentException}.
	 * @param object obiekt którego id jest sprawdzane
	 * @return true jesli id obiektu nie jest nullem.
	 */
	public static boolean isIdNull(Object object) {
		return getId(object)==null;
	}
}
