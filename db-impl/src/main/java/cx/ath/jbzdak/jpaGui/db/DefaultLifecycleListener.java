package cx.ath.jbzdak.jpaGui.db;

import edu.umd.cs.findbugs.annotations.OverrideMustInvoke;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-11
 */
public abstract class DefaultLifecycleListener<DBM extends DBManager, LA extends LifecycleAdministrator> extends LifecycleListener {


   protected DBLifecyclePhase phase;

   protected DBM dbManager;

   protected Object[] params;

   protected LA lifecycleAdministartor;


   public DefaultLifecycleListener(int priority, String name) {
      super(priority, name);
   }

   public void setPhase(DBLifecyclePhase phase) {
      this.phase = phase;
   }

   public void setDbManager(DBM dbManager) {
      this.dbManager = dbManager;
   }

   public void setParams(Object[] params) {
      this.params = params;
   }


   public void setLifecycleAdministartor(LA lifecycleAdministartor) {
      this.lifecycleAdministartor = lifecycleAdministartor;
   }

   @Override
   public Object mayGoToPhase() {
      return null;
   }


   @Override
   @OverrideMustInvoke
   public void clear() {
      params = null;
      lifecycleAdministartor = null;
      dbManager = null;
      phase = null; 
   }
}
