package cx.ath.jbzdak.jpaGui.db;

import cx.ath.jbzdak.jpaGui.task.Task;

import java.util.EnumSet;
import java.util.Map;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-11
 */
public interface LifecycleAdministrator<T extends DBManager, USER_OBJECT> extends LifecycleManager<T> {

   public void goToPhase(DBLifecyclePhase phase, Object... parameters) throws Exception;

   public void addListener(EnumSet<DBLifecyclePhase> phases, LifecycleListener<? super T> listener);

   public void addListenerPack(LifecycleListenerPack<T> listenerPack);

   public static abstract class LifecycleListener<T extends DBManager> extends Task<T>{

      protected LifecycleListener() {
      }

      protected LifecycleListener(int priority, String name) {
         super(priority, name);
      }

      public abstract Object mayGoToPhase(T dbManager, DBLifecyclePhase phase);



   }

   public USER_OBJECT getUserObject();

   public void setUserObject(USER_OBJECT userObject);

   public Map<String, Object> getUserConfiguration();
}
