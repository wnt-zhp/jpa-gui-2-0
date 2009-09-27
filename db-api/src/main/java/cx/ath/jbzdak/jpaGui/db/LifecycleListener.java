package cx.ath.jbzdak.jpaGui.db;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
*         Date: 2009-09-26
*/
public abstract class LifecycleListener<T extends DBManager, L extends LifecycleAdministrator>{


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

   public abstract Object mayGoToPhase(T dbManager, DBLifecyclePhase phase);

   public abstract void executePhase(T manager, L administrator, Object... params) throws Exception;

}
