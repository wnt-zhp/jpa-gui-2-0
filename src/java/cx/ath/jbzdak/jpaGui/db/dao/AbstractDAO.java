package cx.ath.jbzdak.jpaGui.db.dao;

import static cx.ath.jbzdak.jpaGui.Utils.isIdNull;
import cx.ath.jbzdak.jpaGui.db.DBManager;
import cx.ath.jbzdak.jpaGui.db.dao.annotations.LifecyclePhase;
import javax.persistence.EntityManager;

import java.text.Normalizer.Form;
import java.util.NoSuchElementException;

/**
 * Domyślne dao. Zarządza pojedyńczą encją i ma przypisanego
 * {@link #entityManager}  Tak na prawdę zarządza trazakcjami i tyle robi.
 * Powiązane z {@link Form}
 *
 * @author jb
 * @param <T>
 */
public class AbstractDAO<T> implements DAO<T> {

   protected final DBManager manager;

   protected final Class<? extends T> clazz;

   private final CompositeEntityLifecycleListener listener;

   protected EntityManager entityManager;

   protected boolean transactionWideEntityManager;

   private boolean autoCreateEntity = false;

   private RefreshType refreshType = RefreshType.MERGE;

   private T entity;

   private int beginCount = 0;


   /**
    * true jeśli tranzakcja zarządza kto inny
    */
   private boolean transactionManaged;

   public AbstractDAO(DBManager manager, Class<? extends T> clazz) {
      super();
      this.manager = manager;
      this.clazz = clazz;
      listener = new CompositeEntityLifecycleListener(manager, clazz);
   }

   void firePersistenceEVT(LifecyclePhase phase){
      beginTransaction();
      try {
         manager.firePersEvent(entity,  phase, entityManager);
         closeTransaction();
      } catch (RuntimeException e) {
         rollback();
         throw e;
      }
   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#getEntityManager()
   */
   public EntityManager getEntityManager() {
      if (entityManager == null) {
         throw new IllegalStateException();
      }
      return entityManager;
   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#beginTransaction()
   */
   public void beginTransaction() {
      if (transactionManaged)
         return;
      if (entityManager == null || !entityManager.isOpen()) {
         entityManager = manager.createEntityManager();
         transactionWideEntityManager = true;
      } else {
         transactionWideEntityManager = false;
      }
      if (beginCount == 0) {
         entityManager.getTransaction().begin();
         if (entity != null && !isIdNull(getEntity())) {
            entity = refreshType.perform(entityManager, entity, manager);
         }
      }
      beginCount++;
   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#closeTransaction()
   */
   public void closeTransaction() {
      if (transactionManaged)
         return;
      beginCount--;
      if (beginCount == 0) {
         entityManager.getTransaction().commit();
         if (transactionWideEntityManager) {
            entityManager.close();
            entityManager = null;
         }
      }
   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#rollback()
   */
   public void rollback() {
      if(entityManager.getTransaction().isActive()){
         entityManager.getTransaction().rollback();
      }
      beginCount = 0;
      if (transactionWideEntityManager) {
         entityManager.close();
         entityManager = null;
      }
   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#rollbackIfActive()
   */
   public void rollbackIfActive() {
      if (entityManager != null) {
         if (entityManager.getTransaction().isActive()) {
            rollback();
            entityManager = null;
         }
      }
   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#persist()
   */
   public void persist() {
      beginTransaction();
      try {
         firePersistenceEVT(LifecyclePhase.PrePersist);
         entityManager.persist(getEntity());
         closeTransaction();
      } catch (RuntimeException e) {
         rollback();
         throw e;
      }
      firePersistenceEVT(LifecyclePhase.PostPersist);
   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#update()
   */
   public void update() {
      beginTransaction();
       try {
         if (!getEntityManager().contains(getEntity())) {
            throw new IllegalStateException();
         }
         firePersistenceEVT(LifecyclePhase.PreUpdate);
         getEntityManager().flush();
         closeTransaction();
      } catch (RuntimeException e) {
         rollback();
         throw e;
      }
      firePersistenceEVT(LifecyclePhase.PostUpdate);
   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#persistOrUpdate()
   */
   public void persistOrUpdate() {
      beginTransaction();
      try{
         if (!isIdNull(getEntity())) {
            getEntityManager().merge(getEntity());
            getEntityManager().flush();
         } else {
            getEntityManager().persist(getEntity());
         }
         closeTransaction();
      }catch (RuntimeException e){
         rollback();
         throw e;
      }
   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#find(java.lang.Object)
   */
   public void find(Object o) {
      beginTransaction();
      try {
         setEntity(getEntityManager().find(clazz, o));
         if (getEntity() == null) {
            throw new NoSuchElementException();
         }
         firePersistenceEVT(LifecyclePhase.PostLoad);
         closeTransaction();
      } catch (RuntimeException e) {
         rollback();
         throw e;
      }

   }

   public void remove(){
      beginTransaction();
      try{
         firePersistenceEVT(LifecyclePhase.PreRemove);
         entityManager.remove(entity);
         closeTransaction();
      }catch (RuntimeException e){
         rollback();
         throw e;
      }
   }


   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#clearEntity()
   */
   public void clearEntity() {
      setEntity(null);
   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#createEntity()
   */
   public void createEntity() {
      if (getEntity() != null) {
         throw new IllegalStateException();
      }
      try {
         setEntity(clazz.newInstance());
      } catch (InstantiationException e) {
         throw new RuntimeException(e);
      } catch (IllegalAccessException e) {
         throw new RuntimeException(e);
      }
   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#getEntity()
   */
   public T getEntity() {
      if (autoCreateEntity && entity == null) {
         createEntity();
      }
      return entity;
   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#setEntity(T)
   */
   public void setEntity(T entity) {
      this.entity = entity;
      if(beginCount!=0){
         this.entity = refreshType.perform(entityManager, entity, manager);
      }
   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#isAutoCreateEntity()
   */
   public boolean isAutoCreateEntity() {
      return autoCreateEntity;
   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#setAutoCreateEntity(boolean)
   */
   public void setAutoCreateEntity(boolean autoCreateEntity) {
      this.autoCreateEntity = autoCreateEntity;
   }

   public void setEntityManager(EntityManager entityManager) {
      this.entityManager = entityManager;
   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#isTransactionManaged()
   */
   public boolean isTransactionManaged() {
      return transactionManaged;
   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#setTransactionManaged(boolean)
   */
   public void setTransactionManaged(boolean transactionManaged) {
      this.transactionManaged = transactionManaged;
   }

   public RefreshType getRefreshType() {
      return refreshType;
   }

   public void setRefreshType(RefreshType refreshType) {
      this.refreshType = refreshType;
   }

   protected CompositeEntityLifecycleListener getListener() {
      return listener;
   }
}
