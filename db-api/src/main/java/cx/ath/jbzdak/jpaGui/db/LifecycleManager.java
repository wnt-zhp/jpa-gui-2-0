package cx.ath.jbzdak.jpaGui.db;

import java.util.List;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-12
 */
public interface LifecycleManager<T extends DBManager> {

   public List<?> mayGoToPhase(DBLifecyclePhase phase);

   public void startDB() throws Exception;

   public void closeDB() throws Exception;

   public void backupDB(Object... parameters) throws Exception;

   public void readBackup(Object... parameters) throws Exception;   
}
