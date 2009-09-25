package cx.ath.jbzdak.jpaGui.db.lifecycleListenerPack;

import cx.ath.jbzdak.jpaGui.db.*;

import java.util.Collections;
import java.util.EnumSet;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-12
 */
public class HibernateStartDbPack<T extends JpaDbManager> extends DefaultLifecycleListenerPack<T> {

   public HibernateStartDbPack() {
      super(Collections.<String>emptySet(), "start-db", "stop-db");
      addListener(EnumSet.of(DBLifecyclePhase.PRE_START), new CreateEJB3Confihuration());
      addListener(EnumSet.of(DBLifecyclePhase.START), new CreateEntityManagerFactoryEvent());
      addListener(EnumSet.of(DBLifecyclePhase.CLOSE), new CloseEntityManagerFactory<T>());
   }


  
}
