package cx.ath.jbzdak.jpaGui.db.lifecycleListenerPack;

import cx.ath.jbzdak.jpaGui.db.DBLifecyclePhase;
import cx.ath.jbzdak.jpaGui.db.DBManager;
import cx.ath.jbzdak.jpaGui.db.DefaultLifecycleListener;
import cx.ath.jbzdak.jpaGui.db.LifecycleAdministrator;

import java.io.File;
import java.util.Collections;
import java.util.EnumSet;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-27
 */
public class ReadBackupFile <T extends DBManager, L extends LifecycleAdministrator> extends  DefaultLifecycleListenerPack<T, L> {
   public ReadBackupFile() {
      super(Collections.<String>emptySet(), "backup-read-filename");
      addListener(EnumSet.of(DBLifecyclePhase.PRE_BACKUP), new DefaultLifecycleListener<T,L>(0, "Read backup file name"){
         @Override
         public void executePhase() {
            if(params[0] != null && params[0] instanceof File){
               lifecycleAdministartor.getUserConfiguration().put("backup-file", params[0]);
            }
         }
      });
      addListener(EnumSet.of(DBLifecyclePhase.POST_BACKUP), new DefaultLifecycleListener<T,L>(0, "Clear backup file"){
         @Override
         public void executePhase() {
            lifecycleAdministartor.getUserConfiguration().remove("backup-file");
         }
      });
      addListener(EnumSet.of(DBLifecyclePhase.PRE_READ_BACKUP), new DefaultLifecycleListener<T,L>(0, "Read bakcup file name"){
         @Override
         public void executePhase() throws Exception {
            if(params[0] != null && params[0] instanceof File){
               lifecycleAdministartor.getUserConfiguration().put("read-backup-file", params[0]);
            }
         }
      });
       addListener(EnumSet.of(DBLifecyclePhase.POST_READ_BACKUP), new DefaultLifecycleListener<T,L>(0, "Clear backup file"){
         @Override
         public void executePhase() {
            lifecycleAdministartor.getUserConfiguration().remove("read-backup-file");
         }
      });
   }


}
