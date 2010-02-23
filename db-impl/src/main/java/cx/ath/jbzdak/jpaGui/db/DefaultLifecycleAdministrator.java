package cx.ath.jbzdak.jpaGui.db;

import cx.ath.jbzdak.jpaGui.ConfigurationException;
import edu.umd.cs.findbugs.annotations.CheckForNull;
import net.jcip.annotations.NotThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-11
 */
public  class DefaultLifecycleAdministrator<T extends JpaDbManager, USER_OBJECT>
        implements LifecycleAdministrator<T, USER_OBJECT> {


   protected final Lock lock = new ReentrantLock();

   private static final Logger log = LoggerFactory.getLogger(DefaultLifecycleAdministrator.class);

   @SuppressWarnings({"MapReplaceableByEnumMap"})
   private final Map<DBLifecyclePhase, List<LifecycleListener>> lifecycleListenerMap
           = new ConcurrentHashMap<DBLifecyclePhase, List<LifecycleListener>>();
   {
      lock.lock();
      for(DBLifecyclePhase phase : DBLifecyclePhase.values()){
         lifecycleListenerMap.put(phase, new ArrayList<LifecycleListener>());
      }
      lock.unlock();
   }

   private volatile T dbManager;

   protected final Set<String> insertedPacks = new HashSet<String>();

   private USER_OBJECT userObject;

   private Map<String, Object> userConfiguration = new ConcurrentHashMap<String, Object>();

   private volatile boolean databaseStarted;

   private volatile boolean databaseInitialized = true;

   private volatile Integer dbVersion;

   private final Set<PropertySetter> setters;

   {
      setters = new HashSet<PropertySetter>();
      setters.add(new PropertySetter("lifecycleAdministrator", LifecycleAdministrator.class){
         @Override
         Object getValue() {
            return DefaultLifecycleAdministrator.this;
         }
      });
      setters.add(new PropertySetter("dbManager", DBManager.class){
         @Override
         Object getValue() {
            return dbManager;
         }
      });
      setters.add(new PropertySetter("userObject", Object.class){
         @Override
         Object getValue() {
            return userObject;
         }
      });
      setters.add(new PropertySetter("userConfiguration", Map.class) {
         @Override
         Object getValue() {
            return userConfiguration;
         }
      });
   }


   private void setLLProps(LifecycleListener lifecycleListener){
      for(PropertySetter setter : setters){
         setter.setOnObject(lifecycleListener);
      }
   }

    private void setLLProps(LifecycleListener lifecycleListener, DBLifecyclePhase phase, @CheckForNull Object[] params){
      setLLProps(lifecycleListener);
      new DefaultSetter("phase", phase).setOnObject(lifecycleListener);
      new DefaultSetter("params", params).setOnObject(lifecycleListener);
   }


    @Override
   public void goToPhase(DBLifecyclePhase phase, Object... parameters) throws Exception{
      lock.lock();
      log.info("Executing DBPhase {}", phase.name());
      try{
         for(LifecycleListener ll :lifecycleListenerMap.get(phase)){
            setLLProps(ll, phase, parameters);
            log.debug("Executing lifecycle listener {}", ll.getName());
            ll.executePhase();
            ll.clear();
         }
      }finally {
         lock.unlock();
      }
   }

    @Override
   public List<?> mayGoToPhase(DBLifecyclePhase phase){
      List<Object> results = new ArrayList<Object>();
      lock.lock();
      try{
         for(LifecycleListener ll :lifecycleListenerMap.get(phase)){
            setLLProps(ll, phase, null);
            Object result = ll.mayGoToPhase();
            ll.clear();
            if(result!=null){
               results.add(result);
            }
         }
      }finally {
         lock.unlock();
      }
      return results;
   }

   @SuppressWarnings({"CollectionDeclaredAsConcreteClass"})
   @Override
   public void addListener(EnumSet<DBLifecyclePhase> phases, LifecycleListener listener) {
      lock.lock();
      try{
         for(DBLifecyclePhase phase : phases){
            List<LifecycleListener> listeners = lifecycleListenerMap.get(phase);
            if(listeners == null){
               listeners = new ArrayList<LifecycleListener>();
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
   public void addListenerPack(LifecycleListenerPack listenerPack) {
      lock.lock();
      try{
         for(String name : listenerPack.getName()){
            if(insertedPacks.contains(name)){
               throw new ConfigurationException("Already inserted pack '" + name + "'");
            }
         }
         for(String neededPack : listenerPack.getNeededPacks()){
            if(!insertedPacks.contains(neededPack)){
               throw new ConfigurationException("Pack '" + listenerPack.getName() + "[" + listenerPack + "]' needs pack"
                       + " '" + neededPack + "' which is unaviable");
            }
         }
         for(Map.Entry<EnumSet<DBLifecyclePhase>, LifecycleListener> entry : listenerPack.getListeners()){
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
         throw new IllegalStateException();
      }
      lock.lock();
      try{
         goToPhase(DBLifecyclePhase.PRE_START);
         goToPhase(DBLifecyclePhase.START);
         if(!databaseInitialized){
            goToPhase(DBLifecyclePhase.SHEMA_CREATE);
         }
         goToPhase(DBLifecyclePhase.SHEMA_UPDATE);
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
         startDB();
         goToPhase(DBLifecyclePhase.PRE_READ_BACKUP, parameters);
         goToPhase(DBLifecyclePhase.READ_BACKUP, parameters);
         goToPhase(DBLifecyclePhase.POST_READ_BACKUP, parameters);
         closeDB();
         if(wasStarted){
            startDB();
         }
      }finally {
         lock.unlock();
      }
   }

   @Override
   public void clearDB() throws Exception {
      lock.lock();
      try{
         boolean wasStarted = databaseStarted;
         if(!wasStarted){
            startDB();
         }
         goToPhase(DBLifecyclePhase.CLEAR_DB_CONTENTS);
         if(!wasStarted){
            closeDB();
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

   @Override
   public Map<String, Object> getUserConfiguration() {
      return userConfiguration;
   }

   public void setUserConfiguration(Map<String, Object> userConfiguration) {
      this.userConfiguration = userConfiguration;
   }

   @Override
   public USER_OBJECT getUserObject() {
      return userObject;
   }

   @Override
   public void setUserObject(USER_OBJECT userObject) {
      this.userObject = userObject;
   }

   @Override
   public void setDBManager(T dbManager) {
      this.dbManager = dbManager;
   }

   public boolean isDatabaseStarted() {
      return databaseStarted;
   }

   public T getDbManager() {
      return dbManager;
   }

   public boolean isDatabaseInitialized() {
      return databaseInitialized;
   }

   public void setDatabaseInitialized(boolean databaseInitialized) {
      this.databaseInitialized = databaseInitialized;
   }

   public Integer getDbVersion() {
      return dbVersion;
   }

   public void setDbVersion(Integer dbVersion) {
      this.dbVersion = dbVersion;
   }

   @NotThreadSafe
   private abstract static class PropertySetter{
      private final String propertyName;

      private final String setterName;

      private final Class clazz;

      protected PropertySetter(String propertyName) {
         this(propertyName, null);
      }

      protected PropertySetter(String propertyName, Class clazz) {
         this.propertyName = propertyName;
         this.setterName = "set" + propertyName.substring(0,1).toUpperCase() + propertyName.substring(1);
         this.clazz = clazz;
      }

      abstract Object getValue();

      boolean invokeMethod(Method m, Object o){
         try {
            m.invoke(o, getValue());
            return true; //Done
         } catch (InvocationTargetException e) {
            throw new RuntimeException("Cant set property '"  + propertyName + "' on Object", e);
         } catch (IllegalAccessException e) {
            throw new RuntimeException("Cant set property '"  + propertyName + "' on Object, setter '"+ m.getName() +"' is unaccessible" , e);
         } catch (IllegalArgumentException e){
            throw new RuntimeException("Cant set property '"  + propertyName + "' on Object, setter '"+ m.getName() +" cant accepr argument of class " + o.getClass().getSimpleName() );
         }
      }

      public void setOnObject(Object o){
         //We're searching for perfect math for method
         Class clazz = this.clazz!=null?this.clazz:getValue().getClass();
         try {
            invokeMethod(o.getClass().getMethod(setterName, clazz), o);
            return;
         } catch (NoSuchMethodException e) {
            //OK nie znaleźliśmy metody z poprawną nazwą.
         }
         //Now were searching for imperfect match.
         List<Method> objectMethods = Arrays.asList(Object.class.getMethods());
         Method result = null;
         for(Method m : o.getClass().getMethods()){
            if(m.getParameterTypes().length==1 && m.getParameterTypes()[0].isAssignableFrom(clazz) && !objectMethods.contains(m)){
               if(result==null){
                  result = m;
               }else{
                  throw new IllegalStateException("Cant set property '"  + propertyName + "' on Object cant find unique setter method on " + o);
               }
            }
         }
         if(result != null){
            invokeMethod(result, o);
         }

      }
   }

   private static class DefaultSetter extends PropertySetter{

      private DefaultSetter(String propertyName, Object value) {
         super(propertyName, value.getClass());
         this.value = value;
      }

      Object value;

      @Override
      public Object getValue() {
         return value;
      }
   }


}
