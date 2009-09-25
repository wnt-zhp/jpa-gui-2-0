package cx.ath.jbzdak.jpaGui.db;

import java.util.List;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-12
 */
public interface DBConfiguration<T extends DBManager> {

   public T getDBManager();

   public List<?> mayGoToPhase(DBLifecyclePhase phase);

   public void startDB() throws Exception;

   public void closeDB() throws Exception;

   public void backupDB() throws Exception;
}
