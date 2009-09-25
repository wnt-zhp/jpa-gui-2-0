package cx.ath.jbzdak.jpaGui.db.h2;

import cx.ath.jbzdak.jpaGui.db.DBManager;
import cx.ath.jbzdak.jpaGui.db.DefaultLifecycleListener;
import edu.umd.cs.findbugs.annotations.Nullable;

import java.io.File;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-13
 */
public class H2BackupListener<T extends DBManager> extends DefaultLifecycleListener<T>{

   public H2BackupListener() {
      super(0, "BACKUP");
   }

   @Override
   public void doTask(@Nullable T t, @Nullable Object... o) throws Exception {
      Object[] backupParams = (Object[]) o[2];
      File target = (File) backupParams[0];
      t.executeNativeStatement("BACKUP TO " + target.getAbsolutePath() + " COMPRESSION ZIP;");
   }
}
