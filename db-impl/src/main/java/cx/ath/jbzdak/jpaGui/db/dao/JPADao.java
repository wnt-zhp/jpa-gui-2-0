package cx.ath.jbzdak.jpaGui.db.dao;

import cx.ath.jbzdak.jpaGui.db.DBManager;
import cx.ath.jbzdak.jpaGui.db.dao.annotations.LifecyclePhase;

import javax.persistence.EntityManager;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.Normalizer.Form;

import static cx.ath.jbzdak.jpaGui.utils.DBUtils.*;
import cx.ath.jbzdak.common.AbstractPropertySupported;

/**
 * Domyślne dao. Zarządza pojedyńczą encją i ma przypisanego
 * {@link #entityManager}  Tak na prawdę zarządza trazakcjami i tyle robi.
 * Powiązane z {@link Form}
 *
 * @author jb
 * @param <T>
 */
//TODO Wysyłanie eventów po komicte tranzakcji
public class JPADao<T> implements DAO<T> {

   protected final DBManager<EntityManager> manager;

   protected final Class<? extends T> clazz;

   protected final PropertyChangeSupport support = new PropertyChangeSupport(this);

   protected EntityManager entityManager;

   protected boolean transactionWideEntityManager;

   private boolean autoCreateEntity = false;

   private JPARefreshType refreshType = JPARefreshType.MERGE;

   private T bean;

   private int beginCount = 0;

   /**
    * true jeśli tranzakcja zarządza kto inny
    */
   private boolean transactionManaged;

   public JPADao(DBManager<EntityManager> manager, Class<? extends T> clazz) {
      super();
      this.manager = manager;
      this.clazz = clazz;
   }

   /**
    * Does nothing. . . May be overriden.
    * @param phase
    */
   protected void firePersistenceEVT(LifecyclePhase phase){

   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#getEntityManager()
   */
   EntityManager getEntityManager() {
      if (entityManager == null) {
         throw new IllegalStateException();
      }
      return entityManager;
   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#beginTransaction()
   */
   @Override
   public boolean beginTransaction() {
      if (transactionManaged) {
         return false;
      }
      if(isAutoCreateEntity() && bean == null){
         createBean();
      }
      if (entityManager == null || !entityManager.isOpen()) {
         //noinspection deprecation,deprecation
         entityManager = manager.createProvider();
         transactionWideEntityManager = true;
      } else {
         transactionWideEntityManager = false;
      }
      if (beginCount == 0) {
         entityManager.getTransaction().begin();
         if (getBean() != null && !isIdNull(getBean())) {
            try {
               bean = refreshType.perform(entityManager, getBean(), manager);
            } catch (RuntimeException e) {
               entityManager.getTransaction().rollback();
               throw e;
            }
         }
      }
      return beginCount++ == 0;
   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#commitTransaction()
   */
   @Override
   public boolean commitTransaction() {
      if(beginCount<=0){
         throw new IllegalStateException("Cant commit unopened transaction");
      }
      if (transactionManaged) {
         return false;
      }
      beginCount--;
      if (beginCount == 0) {
         entityManager.getTransaction().commit();
         if (transactionWideEntityManager) {
            entityManager.close();
            entityManager = null;
         }
      }
      return  beginCount==0;
   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#rollback()
   */
   @Override
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
   @Override
   public boolean rollbackIfActive() {
      if (entityManager != null) {
         if (entityManager.getTransaction().isActive()) {
            rollback();
            entityManager = null;
            beginCount = 0;
            return true;
         }
      }
      return false;
   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#persist()
   */
   @Override
   public void persist() {
      beginTransaction();
      try {
         if(!isIdNull(getBean())){
            throw new IllegalStateException("Trying to persist already persisted bean");
         }
         firePersistenceEVT(LifecyclePhase.PrePersist);
         entityManager.persist(getBean());
         commitTransaction();
      } catch (RuntimeException e) {
         rollback();
         throw e;
      }
      firePersistenceEVT(LifecyclePhase.PostPersist);
   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#update()
   */
   @Override
   public void update() {
      beginTransaction();
       try {
         if (!getEntityManager().contains(getBean())) {
            throw new IllegalStateException();
         }
         firePersistenceEVT(LifecyclePhase.PreUpdate);
         getEntityManager().flush();
         commitTransaction();
      } catch (RuntimeException e) {
         rollback();
         throw e;
      }
      firePersistenceEVT(LifecyclePhase.PostUpdate);
   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#persistOrUpdate()
   */
   @Override
   public void persistOrUpdate() {
        if (willAutoSetId(getBean())) {
          persist();
        } else {
           if(entityManager.find(clazz, getId(getBean()))==null){
              persist();
           }else{
              update();
           }
        }
   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#find(java.lang.Object)
   */
   @Override
   public void find(Object o) {
      if(o==null){
         throw new IllegalArgumentException("Id cant be null");
      }
      beginTransaction();
      try {
         T bean =getEntityManager().find(clazz, o);
         if(bean==null){
            throw new IllegalArgumentException("Can't find bean");
         }
         setBean(bean);
         firePersistenceEVT(LifecyclePhase.PostLoad);
         commitTransaction();
      } catch (RuntimeException e) {
         rollback();
         throw e;
      }

   }

   @Override
   public void remove(){
      beginTransaction();
      try{
         firePersistenceEVT(LifecyclePhase.PreRemove);
         entityManager.remove(getBean());
         commitTransaction();
      }catch (RuntimeException e){
         rollback();
         throw e;
      }
   }


   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#clearEntity()
   */
   @Override
   public void clearEntity() {
      setBean(null);
   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#createBean()
   */
   @Override
   public void createBean() {
      if (bean != null) {
         throw new IllegalStateException();
      }
      try {
         setBean(clazz.newInstance());
      } catch (InstantiationException e) {
         throw new RuntimeException(e);
      } catch (IllegalAccessException e) {
         throw new RuntimeException(e);
      }
   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#getBean()
   */
   @Override
   public T getBean() {
      if (autoCreateEntity && bean == null) {
         createBean();
      }
      return bean;
   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#setBean(T)
   */
   @Override
   public void setBean(T entity) {
      T oldEntity = this.bean;
      this.bean = entity;
      if(beginCount!=0){
         this.bean = refreshType.perform(entityManager, entity, manager);
      }
      support.firePropertyChange("bean", oldEntity, entity);
   }

//   @Override
//   public void refresh(RefreshType refreshType) {
//      JPARefreshType jpaRefreshType = JPARefreshType.map(refreshType);
//      beginTransaction();
//      try{
//         setBean(jpaRefreshType.perform(getEntityManager(), getBean(), manager));
//      }finally {
//         commitTransaction();
//      }
//   }
   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#isAutoCreateEntity()
   */
   @Override
   public boolean isAutoCreateEntity() {
      return autoCreateEntity;
   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#setAutoCreateEntity(boolean)
   */
   @Override
   public void setAutoCreateEntity(boolean autoCreateEntity) {
      this.autoCreateEntity = autoCreateEntity;
   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#isTransactionManaged()
   */
   @Override
   public boolean isTransactionManaged() {
      return transactionManaged;
   }

   /* (non-Javadoc)
   * @see cx.ath.jbzdak.zarlok.db.dao.DAO#setTransactionManaged(boolean)
   */
   @Override
   public void setTransactionManaged(boolean transactionManaged) {
      this.transactionManaged = transactionManaged;
   }

   @Override
   public RefreshType getRefreshType() {
      return refreshType.getRefreshType();
   }

   @Override
   public void setRefreshType(RefreshType refreshType) {
      this.refreshType = JPARefreshType.map(refreshType);
   }

   public void addPropertyChangeListener(PropertyChangeListener listener) {
      support.addPropertyChangeListener(listener);
   }

   public void removePropertyChangeListener(PropertyChangeListener listener) {
      support.removePropertyChangeListener(listener);
   }

   public PropertyChangeListener[] getPropertyChangeListeners() {
      return support.getPropertyChangeListeners();
   }

   public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
      support.addPropertyChangeListener(propertyName, listener);
   }

   public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
      support.removePropertyChangeListener(propertyName, listener);
   }

   public PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
      return support.getPropertyChangeListeners(propertyName);
   }

   public boolean hasListeners(String propertyName) {
      return support.hasListeners(propertyName);
   }
}
