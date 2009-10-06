package cx.ath.jbzdak.jpaGui.db.h2;

import cx.ath.jbzdak.jpaGui.db.DBLifecyclePhase;
import cx.ath.jbzdak.jpaGui.db.DefaultLifecycleListener;
import cx.ath.jbzdak.jpaGui.db.JpaDbManager;

import java.util.EnumSet;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-10-04
 */
public class DefaultH2Configuration extends H2Configuration<JpaDbManager, Void>{

   public DefaultH2Configuration(boolean suppresDefaults) {
      super(suppresDefaults);
   }

   @Override
   protected void setDefaults() {
      super.setDefaults();
      addListener(EnumSet.of(DBLifecyclePhase.PRE_START), new DefaultLifecycleListener<JpaDbManager, DefaultH2Configuration>(-100, "CREATE_DB_MANAGER") {
         @Override
         public void executePhase() throws Exception {
            lifecycleAdministartor.setDBManager(new JpaDbManager());
         }
      } );
   }
}
