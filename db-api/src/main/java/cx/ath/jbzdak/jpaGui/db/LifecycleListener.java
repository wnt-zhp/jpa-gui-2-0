package cx.ath.jbzdak.jpaGui.db;

import net.jcip.annotations.NotThreadSafe;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
*         Date: 2009-09-26
*/
@NotThreadSafe
public abstract class LifecycleListener{

	private final int priority;

	private final String name;

   protected LifecycleListener(int priority, String name) {
      this.priority = priority;
      this.name = name;
   }

   public int getPriority() {
      return priority;
   }

   public String getName() {
      return name;
   }

   public abstract Object mayGoToPhase();

   public abstract void executePhase() throws Exception;

   /**
    * Should clear all class state. 
    */
   public abstract void clear();
}
