package cx.ath.jbzdak.jpaGui.db;


import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-12
 */
public interface LifecycleListenerPack<T extends DBManager, L extends LifecycleAdministrator> {

   Set<String> getName();

   Set<String> getNeededPacks();

   List<Map.Entry<EnumSet<DBLifecyclePhase>, LifecycleListener<? super T, ? super L>>> getListeners();

}
