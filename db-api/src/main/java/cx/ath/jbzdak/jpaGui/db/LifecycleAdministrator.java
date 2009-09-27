package cx.ath.jbzdak.jpaGui.db;

import java.util.EnumSet;
import java.util.Map;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-11
 */
public interface LifecycleAdministrator<T extends DBManager, USER_OBJECT, L extends LifecycleAdministrator> extends LifecycleManager<T> {

   public void goToPhase(DBLifecyclePhase phase, Object... parameters) throws Exception;

   public void addListener(EnumSet<DBLifecyclePhase> phases, LifecycleListener<? super T, ? super L> listener);

   public void addListenerPack(LifecycleListenerPack<T, L> listenerPack);

   public USER_OBJECT getUserObject();

   public void setUserObject(USER_OBJECT userObject);

   public Map<String, Object> getUserConfiguration();
}
