package cx.ath.jbzdak.jpaGui.db.lifecycleListenerPack;

import cx.ath.jbzdak.jpaGui.db.DBLifecyclePhase;
import cx.ath.jbzdak.jpaGui.db.DBManager;
import cx.ath.jbzdak.jpaGui.db.LifecycleAdministrator;
import cx.ath.jbzdak.jpaGui.db.LifecycleListenerPack;

import java.util.*;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-12
 */
public class DefaultLifecycleListenerPack<T extends DBManager> implements LifecycleListenerPack<T>{

   private final Set<String> name;

   private final Set<String> neededPacks;

   private final List<Map.Entry<EnumSet<DBLifecyclePhase>, LifecycleAdministrator.LifecycleListener<? super T>>> listeners
           = new ArrayList<Map.Entry<EnumSet<DBLifecyclePhase>, LifecycleAdministrator.LifecycleListener<? super T>>>();

   protected void addListener(EnumSet<DBLifecyclePhase> phases, LifecycleAdministrator.LifecycleListener<? super T> list){
      listeners.add(new AbstractMap.SimpleImmutableEntry<EnumSet<DBLifecyclePhase>, LifecycleAdministrator.LifecycleListener<? super T>>(phases, list));
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

   public List<Map.Entry<EnumSet<DBLifecyclePhase>, LifecycleAdministrator.LifecycleListener<? super T>>> getListeners() {
      return listeners;
   }
}
