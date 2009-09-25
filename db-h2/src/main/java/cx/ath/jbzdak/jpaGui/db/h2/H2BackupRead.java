package cx.ath.jbzdak.jpaGui.db.h2;

import cx.ath.jbzdak.jpaGui.db.DBLifecyclePhase;
import cx.ath.jbzdak.jpaGui.db.DBManager;
import cx.ath.jbzdak.jpaGui.db.DefaultLifecycleListener;
import cx.ath.jbzdak.jpaGui.db.LifecycleAdministrator;
import edu.umd.cs.findbugs.annotations.Nullable;

import java.io.File;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-13
 */
public class H2BackupRead<T extends DBManager> extends DefaultLifecycleListener<T>{
   public H2BackupRead(int priority, String name) {
      super(priority, name);
   }

   @Override
   public void doTask(@Nullable T t, @Nullable Object... o) throws Exception {
      LifecycleAdministrator administrator = (LifecycleAdministrator) o[1];
      Object[] backupParams = (Object[]) o[2];
      File target = (File) backupParams[0];
      if(!target.exists()){
         throw new RuntimeException("Backup file doesn;t exists, can't read from it");
      }
      administrator.goToPhase(DBLifecyclePhase.CLEAR_DB_CONTENTS);
      t.executeNativeStatement("RUNSCRIPT FROM " + target.getAbsolutePath() + " COMPRESSION ZIP;");
   }
}
