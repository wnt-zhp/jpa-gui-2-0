package cx.ath.jbzdak.jpaGui.db;

import cx.ath.jbzdak.jpaGui.db.dao.annotations.LifecyclePhase;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;


public interface AdministrativeDBManager extends DBManager{

   public List<EntityLifecycleListener> getListener(Class clazz);

   public void addListener(Class clazz, EntityLifecycleListener listener);

   public void firePersEvent(Object entity, LifecyclePhase phase);

   /**
    *
    * @param entity
    * @param phase
    * @param manager może być nullem, wtedy manager tworzy EntityManagera
    * na potrzeby tego wywołania
    */
   public void firePersEvent(Object entity, LifecyclePhase phase, EntityManager manager);

   <T> List<T> fireEventOnQueryResult(List<T> list, EntityManager manager);

   void addLifecycleHook(DBLifecycleHook hook);

   boolean removeLifecycleHook(DBLifecycleHook hook);

   Set<DBLifecycleHook> getHooks();
}
