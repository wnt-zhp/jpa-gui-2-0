package cx.ath.jbzdak.jpaGui.db.h2;

import cx.ath.jbzdak.jpaGui.db.DBLifecyclePhase;
import cx.ath.jbzdak.jpaGui.db.DBManager;
import cx.ath.jbzdak.jpaGui.db.LifecycleAdministrator;
import cx.ath.jbzdak.jpaGui.db.lifecycleListenerPack.DefaultLifecycleListenerPack;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-13
 */
public class H2BackupPack<T extends DBManager, LM extends LifecycleAdministrator> extends DefaultLifecycleListenerPack<T, LM>{

   public H2BackupPack() {
      super(new HashSet<String>(Arrays.asList("clean-db",  "backup-read-filename")), "backup");
      addListener(EnumSet.of(DBLifecyclePhase.BACKUP), new H2BackupListener<T, LM>());
      addListener(EnumSet.of(DBLifecyclePhase.READ_BACKUP), new H2BackupRead<T>());
   }

}
