package cx.ath.jbzdak.jpaGui.db.h2;

import cx.ath.jbzdak.jpaGui.db.DBLifecyclePhase;
import cx.ath.jbzdak.jpaGui.db.DBManager;
import cx.ath.jbzdak.jpaGui.db.lifecycleListenerPack.DefaultLifecycleListenerPack;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-13
 */
public class H2BackupPack<T extends DBManager> extends DefaultLifecycleListenerPack<T>{

   public H2BackupPack() {
      super(new HashSet<String>(Arrays.asList("clean-db")), "backup");
      addListener(EnumSet.of(DBLifecyclePhase.BACKUP), new H2BackupListener<T>());
   }

}
