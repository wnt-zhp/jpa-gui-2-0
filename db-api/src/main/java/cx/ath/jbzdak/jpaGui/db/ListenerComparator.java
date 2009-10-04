package cx.ath.jbzdak.jpaGui.db;


import java.util.Comparator;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-26
 */
public class ListenerComparator implements Comparator<LifecycleListener> {

   @Override
   public int compare(LifecycleListener o1, LifecycleListener o2) {
      	return o1.getPriority()-o2.getPriority();
   }
}
