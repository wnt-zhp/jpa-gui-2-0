package cx.ath.jbzdak.jpaGui.beansbinding;

import org.jdesktop.observablecollections.ObservableList;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-06-04
 */
public class BeansbindingUtils {

   public static ObservableList observableUnmodifiableList(ObservableList observableList){
      return new UnmodifiableObserwableList(observableList);
   }
}
