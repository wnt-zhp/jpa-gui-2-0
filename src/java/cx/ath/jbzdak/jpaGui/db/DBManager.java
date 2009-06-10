package cx.ath.jbzdak.jpaGui.db;

import cx.ath.jbzdak.jpaGui.db.dao.DAO;
import cx.ath.jbzdak.jpaGui.db.dao.annotations.LifecyclePhase;
import javax.persistence.EntityManager;

import java.util.List;
import java.util.Set;


public interface DBManager {

	public EntityManager createEntityManager();

    public List<EntityLifecycleListener> getListener(Class clazz);

    public void addListener(Class clazz, EntityLifecycleListener listener);

    public <T> DAO<T> getDao(Class<T> clazz);

    public <T> DAO<T> getDao(T entity);

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
