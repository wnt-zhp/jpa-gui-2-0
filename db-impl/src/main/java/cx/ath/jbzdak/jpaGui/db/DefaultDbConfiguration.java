package cx.ath.jbzdak.jpaGui.db;

import java.util.List;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-12
 */
public abstract class DefaultDbConfiguration<DBM extends DBManager, LM extends LifecycleAdministrator<DBM, ?, ?>>
        implements DBConfiguration<DBM>{

   protected LM lifecycleManager;

   protected DBM dbManager;

   public void setLifecycleManager(LM lifecycleManager) {
      this.lifecycleManager = lifecycleManager;
      if(dbManager!=null){
         lifecycleManager.setDBManager(dbManager);
      }
   }

   public void setDbManager(DBM dbManager) {
      this.dbManager = dbManager;
      if(lifecycleManager!=null){
         lifecycleManager.setDBManager(dbManager);
      }
   }

   @Override
   public DBM getDBManager() {
      return dbManager;
   }

   @Override
   public List<?> mayGoToPhase(DBLifecyclePhase phase) {
      return lifecycleManager.mayGoToPhase(phase);
   }

   @Override
   public void startDB() throws Exception {
      lifecycleManager.startDB();
   }

   @Override
   public void closeDB() throws Exception {
      lifecycleManager.closeDB();
   }

   @Override
   public void backupDB() throws Exception {
      lifecycleManager.backupDB();
   }
}
