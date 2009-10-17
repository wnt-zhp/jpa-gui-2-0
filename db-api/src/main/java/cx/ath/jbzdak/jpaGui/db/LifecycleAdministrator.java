package cx.ath.jbzdak.jpaGui.db;

import java.util.EnumSet;
import java.util.Map;

/**
 *
 * Implementations of these objects can manage the database state.
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-11
 */
public interface LifecycleAdministrator<T extends DBManager, USER_OBJECT> extends LifecycleManager<T> {

   public void goToPhase(DBLifecyclePhase phase, Object... parameters) throws Exception;

   public void addListener(EnumSet<DBLifecyclePhase> phases, LifecycleListener listener);

   public void addListenerPack(LifecycleListenerPack listenerPack);

   public USER_OBJECT getUserObject();

   public void setUserObject(USER_OBJECT userObject);

   /**
    * User configuration. Currently following keys are used:
    * <ul>
    *    <li> backup-file file to which  backup will be written </li>
    *    <li> read-backup-file file from which backup will be read </li>
    *    <li> ejb3-configuration configuration object from hibernate </li>
    * </ul>
    *
    * @return configuration
    */
   public Map<String, Object> getUserConfiguration();

   public void setDBManager(T dbManager);
}
