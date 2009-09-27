package cx.ath.jbzdak.jpaGui.db.h2;

import cx.ath.jbzdak.jpaGui.db.DBManager;
import cx.ath.jbzdak.jpaGui.db.DefaultLifecycleListener;
import cx.ath.jbzdak.jpaGui.db.LifecycleAdministrator;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-13
 */
public class H2CleanDb<T extends DBManager> extends DefaultLifecycleListener<T, LifecycleAdministrator>{
   public H2CleanDb() {
      super(0, "CLEAN_DB");
   }

   @Override
   public void executePhase(T manager, LifecycleAdministrator administrator, Object... params) {
      manager.executeNativeStatement("DROP ALL OBJECTS;");
   }
   
}
