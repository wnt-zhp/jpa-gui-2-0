package cx.ath.jbzdak.jpaGui.db.h2;

import cx.ath.jbzdak.jpaGui.db.DBManager;
import cx.ath.jbzdak.jpaGui.db.DefaultLifecycleListener;
import edu.umd.cs.findbugs.annotations.Nullable;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-13
 */
public class H2CleanDb<T extends DBManager> extends DefaultLifecycleListener<T>{
   public H2CleanDb() {
      super(0, "CLEAN_DB");
   }

   @Override
   public void doTask(@Nullable T t, @Nullable Object... o) throws Exception {
      
      t.executeNativeStatement("DROP ALL OBJECTS;");
   }
}
