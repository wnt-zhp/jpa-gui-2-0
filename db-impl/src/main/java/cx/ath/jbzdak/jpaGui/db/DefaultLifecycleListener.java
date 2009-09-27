package cx.ath.jbzdak.jpaGui.db;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-11
 */
public class DefaultLifecycleListener<T extends DBManager, L extends LifecycleAdministrator>
        extends LifecycleListener<T, L> {
  
   public DefaultLifecycleListener(int priority, String name) {
      super(priority, name);
   }

   @Override
   public Object mayGoToPhase(T dbManager, DBLifecyclePhase phase) {
      return null;
   }

   @Override
   public void executePhase(T manager, L administrator, Object... params) throws Exception{
     
   }
}
