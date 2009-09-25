package cx.ath.jbzdak.jpaGui.db;

import edu.umd.cs.findbugs.annotations.Nullable;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-11
 */
public class DefaultLifecycleListener<T extends DBManager> extends LifecycleAdministrator.LifecycleListener<T> {
   public DefaultLifecycleListener() {
   }

   public DefaultLifecycleListener(int priority, String name) {
      super(priority, name);
   }

   @Override
   public Object mayGoToPhase(T dbManager, DBLifecyclePhase phase) {
      return null;
   }

   @Override
   public void doTask(@Nullable T t, @Nullable Object... o) throws Exception {
   }
}
