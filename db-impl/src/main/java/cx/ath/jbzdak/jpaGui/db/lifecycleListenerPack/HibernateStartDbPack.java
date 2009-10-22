package cx.ath.jbzdak.jpaGui.db.lifecycleListenerPack;

import cx.ath.jbzdak.jpaGui.db.*;

import java.util.Collections;
import java.util.EnumSet;

/**
 * Does not depend on any backup packs.
 *
 * Provides following backup packs: start-db, stop-db.
 *
 * Uses following configuration keys:
 * <ul>
 * <li>"ejb3-configuration" -- key is valid from 0 in PRE_START phase to 0 in START phase.</li>
 * <li> "persistence-unit" -- key that is read on 0 in PRE_START and
 *
 * Workflow: on 0 in PRE_START set ejb3-configuration (that is
 *
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-12
 */
public class HibernateStartDbPack<T extends JpaDbManager, L extends DefaultLifecycleAdministrator> extends DefaultLifecycleListenerPack<T, L> {

   public HibernateStartDbPack() {
      super(Collections.<String>emptySet(), "start-db", "stop-db", "get-jdbc-url", "init-jdbc-url");
      addListener(EnumSet.of(DBLifecyclePhase.PRE_START), new CreateEJB3Confihuration());
      addListener(EnumSet.of(DBLifecyclePhase.DB_SETUP), new CreateEntityManagerFactoryEvent());
      addListener(EnumSet.of(DBLifecyclePhase.CLOSE), new CloseEntityManagerFactory<T, L>());
   }


  
}
