package cx.ath.jbzdak.jpaGui.db;


import java.io.Serializable;
import java.util.Comparator;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-26
 */
public class ListenerComparator implements Comparator<LifecycleListener>, Serializable {

   private static final long serialVersionUID = 42L;

   @Override
   public int compare(LifecycleListener o1, LifecycleListener o2) {
      	return o1.getPriority()-o2.getPriority();
   }
}
