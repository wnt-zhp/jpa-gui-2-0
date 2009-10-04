package cx.ath.jbzdak.jpaGui.db.lifecycleListenerPack;

import cx.ath.jbzdak.jpaGui.db.*;

import java.util.*;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-12
 */
public class DefaultLifecycleListenerPack<T extends DBManager, L extends LifecycleAdministrator> implements LifecycleListenerPack{

   private final Set<String> name;

   private final Set<String> neededPacks;

   private final List<Map.Entry<EnumSet<DBLifecyclePhase>, LifecycleListener>> listeners
           = new ArrayList<Map.Entry<EnumSet<DBLifecyclePhase>, LifecycleListener>>();

   protected void addListener(EnumSet<DBLifecyclePhase> phases, LifecycleListener list){
      listeners.add(new AbstractMap.SimpleImmutableEntry<EnumSet<DBLifecyclePhase>, LifecycleListener>(phases, list));
   }

   public DefaultLifecycleListenerPack(Set<String> neededPacks, String...  names) {
      this.name = new HashSet<String>(Arrays.asList(names));
      this.neededPacks = neededPacks;
   }

   @Override
   public Set<String> getName() {
      return name;
   }

   @Override
   public Set<String> getNeededPacks() {
      return neededPacks;
   }

   @Override
   public List<Map.Entry<EnumSet<DBLifecyclePhase>, LifecycleListener>> getListeners() {
      return listeners;
   }
}
