package cx.ath.jbzdak.jpaGui.db.h2;

import cx.ath.jbzdak.jpaGui.db.DBManager;
import cx.ath.jbzdak.jpaGui.db.DefaultLifecycleListener;
import cx.ath.jbzdak.jpaGui.db.LifecycleAdministrator;

import java.io.File;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-13
 */
public class H2BackupListener<T extends DBManager, L extends LifecycleAdministrator> extends DefaultLifecycleListener<T, L>{

   public H2BackupListener() {
      super(0, "BACKUP");
   }

   @Override
   public void executePhase() {
      File f = (File) lifecycleAdministartor.getUserConfiguration().get("backup-file");
      if(f==null){
         throw new IllegalArgumentException("Cant backup, beacause the backup file is unset");
      }
      if(f.exists()){
         throw new IllegalArgumentException("Cant backup, backup file already exists");
      }
      dbManager.executeNativeStatement("BACKUP TO " + f.getAbsolutePath() + " COMPRESSION ZIP;");
   }

}
