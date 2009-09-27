package cx.ath.jbzdak.jpaGui.db.lifecycleListenerPack;

import cx.ath.jbzdak.jpaGui.db.*;

import java.util.*;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-12
 */
public class DefaultLifecycleListenerPack<T extends DBManager, L extends LifecycleAdministrator> implements LifecycleListenerPack<T, L>{

   private final Set<String> name;

   private final Set<String> neededPacks;

   private final List<Map.Entry<EnumSet<DBLifecyclePhase>, LifecycleListener<? super T, ? super L>>> listeners
           = new ArrayList<Map.Entry<EnumSet<DBLifecyclePhase>, LifecycleListener<? super T, ? super L>>>();

   protected void addListener(EnumSet<DBLifecyclePhase> phases, LifecycleListener<? super T, ? super L> list){
      listeners.add(new AbstractMap.SimpleImmutableEntry<EnumSet<DBLifecyclePhase>, LifecycleListener<? super T, ? super L>>(phases, list));
   }

   public DefaultLifecycleListenerPack(Set<String> neededPacks, String...  names) {
      this.name = new HashSet<String>(Arrays.asList(names));
      this.neededPacks = neededPacks;
   }

   public Set<String> getName() {
      return name;
   }

   public Set<String> getNeededPacks() {
      return neededPacks;
   }

   public List<Map.Entry<EnumSet<DBLifecyclePhase>, LifecycleListener<? super T, ? super L>>> getListeners() {
      return listeners;
   }
}
