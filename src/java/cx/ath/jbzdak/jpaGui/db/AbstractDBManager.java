package cx.ath.jbzdak.jpaGui.db;

import cx.ath.jbzdak.jpaGui.db.dao.CompositeEntityLifecycleListener;
import cx.ath.jbzdak.jpaGui.db.dao.DAO;
import cx.ath.jbzdak.jpaGui.db.dao.DAOImpl;
import cx.ath.jbzdak.jpaGui.db.dao.annotations.LifecyclePhase;
import org.apache.commons.collections.Factory;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

import javax.persistence.EntityManager;
import java.util.*;

public abstract class AbstractDBManager implements AdministrativeDBManager {

   private final Map<Class, Factory> daoFactories = new HashMap();

   private final MultiMap entityListeners = MultiValueMap.decorate(new HashMap(), ArrayList.class);

   private final Set<DBLifecycleHook> hooks = new HashSet<DBLifecycleHook>();

   public List<EntityLifecycleListener> getListener(Class clazz){
      if(!entityListeners.containsKey(clazz)){
         entityListeners.put(clazz, new DefaultEntityLifecycleListener(clazz));
      }
      return (List<EntityLifecycleListener>) entityListeners.get(clazz);
   }

   @SuppressWarnings({"WeakerAccess"})
   public EntityLifecycleListener getCompoundListener(Class clazz){
      return new CompositeEntityLifecycleListener(this, clazz);
   }

   public void addListener(Class clazz, EntityLifecycleListener listener){
      if(!entityListeners.containsKey(clazz)){
         entityListeners.put(clazz, new DefaultEntityLifecycleListener(clazz));
      }
      entityListeners.put(clazz, listener);
   }

   public void registerDaoFactory(Class clazz, Factory factory){
      daoFactories.put(clazz, factory);
   }

   public <T> DAO<T> getDao(Class<T> clazz){
      Factory f = daoFactories.get(clazz);
      if(f!=null){
         return (DAO<T>) f.create();
      }
      return new DAOImpl<T>(this, clazz);
   }

   public <T> DAO<T> getDao(T entity){
      DAO<T> dao = getDao((Class<T>)entity.getClass());
      dao.setBean(entity);
      return dao;
   }

   @Override
   public void firePersEvent(final Object entity, final LifecyclePhase phase, EntityManager manager) {
      final EntityLifecycleListener listener = getCompoundListener(entity.getClass());
      if(!listener.listensToPhase(phase)){
         return;
      }
      final boolean managerCreated = manager==null || !manager.isOpen();
      if(managerCreated){
         manager = createEntityManager();
      }
      Transaction.execute(manager, new Transaction() {
         {
            closeEntityManager = managerCreated;
         }
         @Override
         public void doTransaction(EntityManager entityManager) {
            listener.lifecycleEvent(phase, entity,entityManager);
         }
      });
   }

   public void firePersEvent(final Object entity, final LifecyclePhase phase){
      firePersEvent(entity, phase, null);
   }

   public <T> List<T> fireEventOnQueryResult(List<T> list, EntityManager manager){
      final boolean managerCreated = manager==null || !manager.isOpen();
      if(managerCreated){
         manager = createEntityManager();
      }
      for(final T entity : list){
         if(entity == null) continue;
         final EntityLifecycleListener listener = getCompoundListener(entity.getClass());
         Transaction.execute(manager, new Transaction() {
            @Override
            public void doTransaction(EntityManager entityManager) {
               listener.lifecycleEvent(LifecyclePhase.PostLoad, entity,entityManager);
            }
         });
      }
      if(managerCreated){
         manager.close();
      }
      return list;
   }

   public void addLifecycleHook(DBLifecycleHook hook){
      hooks.add(hook);
   }

   public boolean removeLifecycleHook(DBLifecycleHook hook){
      return hooks.remove(hook);
   }

   public Set<DBLifecycleHook> getHooks(){
      return Collections.unmodifiableSet(hooks);
   }

   public List<Object> fireMayGoToState(DBState state, DBStateChangeReason reason){
      List<Object> results = new ArrayList();
      for(DBLifecycleHook hook : hooks){
         Object message = hook.mayGoToPhase(this, state, reason);
         if(message!=null){
            results.add(message);
         }
      }
      return results;
   }

   public void fireStateWillChange(DBState state){
      for (DBLifecycleHook hook : hooks) {
         hook.goToPhase(this, state);
      }
   }
}
