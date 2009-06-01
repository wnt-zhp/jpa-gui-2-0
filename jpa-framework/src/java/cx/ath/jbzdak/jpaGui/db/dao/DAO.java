package cx.ath.jbzdak.jpaGui.db.dao;

import javax.persistence.EntityManager;

public interface DAO<T> {

	public EntityManager getEntityManager();

	public void setEntityManager(EntityManager entityManager);

	public void beginTransaction();

	public void closeTransaction();

	public void rollback();

	public void rollbackIfActive();

	public void persist();

	public void update();

	public void persistOrUpdate();

	public void find(Object o);

	public void clearEntity();

	public void createEntity();

	public T getEntity();

	public void setEntity(T entity);

	public boolean isAutoCreateEntity();

	public void setAutoCreateEntity(boolean autoCreateEntity);

	public boolean isTransactionManaged();

	public void setTransactionManaged(boolean transactionManaged);


   RefreshType getRefreshType();

   void setRefreshType(RefreshType refreshType);

   void remove(T entity);
}
