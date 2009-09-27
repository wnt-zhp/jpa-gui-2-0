package cx.ath.jbzdak.jpaGui.db.h2;

import cx.ath.jbzdak.jpaGui.db.DBLifecyclePhase;
import cx.ath.jbzdak.jpaGui.db.DBManager;
import cx.ath.jbzdak.jpaGui.db.DefaultLifecycleListener;
import cx.ath.jbzdak.jpaGui.db.LifecycleAdministrator;

import java.io.File;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-13
 */
public class H2BackupRead<T extends DBManager> extends DefaultLifecycleListener<T, LifecycleAdministrator>{
   public H2BackupRead() {
      super(0, "Read backup");
   }

   @Override
   public void executePhase(T manager, LifecycleAdministrator administrator, Object... params) throws Exception{
      File target = (File) administrator.getUserConfiguration().get("read-backup-file");
      if(target == null){
         throw new IllegalStateException("Cant resolve file frow which we need to read backup.");
      }
      if(!target.isFile()){
         throw new IllegalStateException("Backup file from wchich we need to read is not a file or does nor exist");
      }
      administrator.goToPhase(DBLifecyclePhase.CLEAR_DB_CONTENTS);
      manager.executeNativeStatement("RUNSCRIPT FROM " + target.getAbsolutePath() + " COMPRESSION ZIP;");
   }

}
