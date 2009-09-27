package cx.ath.jbzdak.jpaGui.db.h2;

import cx.ath.jbzdak.jpaGui.db.DBLifecyclePhase;
import cx.ath.jbzdak.jpaGui.db.DBManager;
import cx.ath.jbzdak.jpaGui.db.LifecycleAdministrator;
import cx.ath.jbzdak.jpaGui.db.lifecycleListenerPack.DefaultLifecycleListenerPack;

import java.util.Collections;
import java.util.EnumSet;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-13
 */
public class H2CleanDbPack<T extends DBManager, LM extends LifecycleAdministrator> extends DefaultLifecycleListenerPack<T, LM>{

   public H2CleanDbPack() {
      super(Collections.<String>emptySet(), "clean-db");
      addListener(EnumSet.of(DBLifecyclePhase.CLEAR_DB_CONTENTS), new H2CleanDb<T>());
   }
}
