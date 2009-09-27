package cx.ath.jbzdak.jpaGui.db;

import cx.ath.jbzdak.jpaGui.ConfigurationException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-11
 */
public  class DefaultLifecycleAdministrator<T extends JpaDbManager, USER_OBJECT, L extends DefaultLifecycleAdministrator<T, USER_OBJECT, L>>
        implements LifecycleAdministrator<T, USER_OBJECT, L> {

   private final Map<DBLifecyclePhase, List<LifecycleListener<? super T, ? super L>>> lifecycleListenerMap
           = new ConcurrentHashMap<DBLifecyclePhase, List<LifecycleListener<? super T, ? super L>>>();

   private volatile T dbManager;

   protected final Lock lock = new ReentrantLock();

   protected final Set<String> insertedPacks = new HashSet<String>();

   private USER_OBJECT userObject;

   private Map<String, Object> userConfiguration = new ConcurrentHashMap<String, Object>();

   private boolean databaseStarted;

   public void goToPhase(DBLifecyclePhase phase, Object... parameters) throws Exception{
      lock.lock();
      try{
         for(LifecycleListener<? super T, ? super L> ll :lifecycleListenerMap.get(phase)){
            ll.executePhase(dbManager, (L) this, parameters);
         }
      }finally {
         lock.unlock();
      }
   }

   public List<?> mayGoToPhase(DBLifecyclePhase phase){
      lock.lock();
      List<Object> results = new ArrayList<Object>();
      try{
         for(LifecycleListener<? super  T, ? super L> ll :lifecycleListenerMap.get(phase)){
            Object result = ll.mayGoToPhase(dbManager, phase);
            if(result!=null){
               results.add(result);
            }
         }
      }finally {
         lock.unlock();
      }
      return results;
   }

   @Override
   public void addListener(EnumSet<DBLifecyclePhase> phases, LifecycleListener<? super T, ? super L> listener) {
      lock.lock();
      try{
         for(DBLifecyclePhase phase : phases){
            List<LifecycleListener<? super T, ? super L>> listeners = lifecycleListenerMap.get(phase);
            if(listeners == null){
               listeners = new CopyOnWriteArrayList<LifecycleListener<? super T, ? super L>>();
               lifecycleListenerMap.put(phase, listeners);
            }
            if(!listeners.contains(listener)){
               listeners.add(listener);
               Collections.sort(listeners, new ListenerComparator());
            }
         }
      }finally {
         lock.unlock();
      }
   }

   @Override
   public void addListenerPack(LifecycleListenerPack<T, L> listenerPack) {
      lock.lock();
      try{
         if(insertedPacks.contains(listenerPack.getName())){
            throw new ConfigurationException("Already inserted pack '" + listenerPack.getName() + "'");
         }
         for(String neededPack : listenerPack.getNeededPacks()){
            if(!insertedPacks.contains(neededPack)){
               throw new ConfigurationException("Pack '" + listenerPack.getName() + "[" + listenerPack + "]' needs pack"
                       + " '" + neededPack + "' which is unaviable");
            }
         }
         for(Map.Entry<EnumSet<DBLifecyclePhase>, LifecycleListener<? super T, ? super L>> entry : listenerPack.getListeners()){
            addListener(entry.getKey(), entry.getValue());
         }
         insertedPacks.addAll(listenerPack.getName());
      }finally {
         lock.unlock();
      }
   }


   @Override
   public void startDB() throws Exception {
      if(databaseStarted){
         return;
      }
      lock.lock();
      try{
         goToPhase(DBLifecyclePhase.PRE_START);
         goToPhase(DBLifecyclePhase.START);
         if(!(Boolean) userConfiguration.get("isDBInitialized")){
            goToPhase(DBLifecyclePhase.SHEMA_CREATE);
         }
         if((Boolean) userConfiguration.get("schemaNeedsUpdate")){
            goToPhase(DBLifecyclePhase.SHEMA_UPDATE);
         }
         goToPhase(DBLifecyclePhase.DB_SETUP);
         databaseStarted = true;
      }finally {
         lock.unlock();
      }
   }

   @Override
   public void backupDB(Object... parameters) throws Exception {
      lock.lock();
      try{
         boolean wasStarted = databaseStarted;
         if(!wasStarted){
            startDB();
         }
         goToPhase(DBLifecyclePhase.PRE_BACKUP, parameters);
         goToPhase(DBLifecyclePhase.BACKUP, parameters);
         goToPhase(DBLifecyclePhase.POST_BACKUP, parameters);
         if(!wasStarted){
            closeDB();
         }
      }finally {
         lock.unlock();
      }
   }

   @Override
   public void readBackup(Object... parameters) throws Exception {
      lock.lock();
      try{
         boolean wasStarted = databaseStarted;
         if(wasStarted){
            closeDB();
         }
         goToPhase(DBLifecyclePhase.PRE_BACKUP, parameters);
         goToPhase(DBLifecyclePhase.BACKUP, parameters);
         goToPhase(DBLifecyclePhase.POST_BACKUP, parameters);
         if(wasStarted){
            startDB();
         }
      }finally {
         lock.unlock();
      }
   }

   @Override
   public void closeDB() throws Exception {
      if(!databaseStarted){
         return;
      }
      lock.lock();
      try{
         goToPhase(DBLifecyclePhase.CLOSE);
         databaseStarted = false;
      }finally {
         lock.unlock();
      }
   }

   public Map<String, Object> getUserConfiguration() {
      return userConfiguration;
   }

   public void setUserConfiguration(Map<String, Object> userConfiguration) {
      this.userConfiguration = userConfiguration;
   }

   public USER_OBJECT getUserObject() {
      return userObject;
   }

   public void setUserObject(USER_OBJECT userObject) {
      this.userObject = userObject;
   }

   @Override
   public void setDBManager(T dbManager) {
      this.dbManager = dbManager;
   }


   public void setDbManager(T dbManager) {
      this.dbManager = dbManager;
   }

   public boolean isDatabaseStarted() {
      return databaseStarted;
   }
}
