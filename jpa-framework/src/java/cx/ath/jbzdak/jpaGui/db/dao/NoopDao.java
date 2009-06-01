package cx.ath.jbzdak.jpaGui.db.dao;

import javax.persistence.EntityManager;

public class NoopDao<T> implements DAO<T> {

	private T entity;

	private boolean transactionWideEntityManager;

	private boolean autoCreateEntity = false;

	private boolean transactionManaged;


	public T getEntity() {
		return entity;
	}


	public boolean isTransactionWideEntityManager() {
		return transactionWideEntityManager;
	}


	public boolean isAutoCreateEntity() {
		return autoCreateEntity;
	}


	public boolean isTransactionManaged() {
		return transactionManaged;
	}


	public void setEntity(T entity) {
		this.entity = entity;
	}


	public void setTransactionWideEntityManager(boolean transactionWideEntityManager) {
		this.transactionWideEntityManager = transactionWideEntityManager;
	}


	public void setAutoCreateEntity(boolean autoCreateEntity) {
		this.autoCreateEntity = autoCreateEntity;
	}


	public void setTransactionManaged(boolean transactionManaged) {
		this.transactionManaged = transactionManaged;
	}


	@Override
	public void beginTransaction() {}


	@Override
	public void clearEntity() {}


	@Override
	public void closeTransaction() {}


	@Override
	public void createEntity() {}


	@Override
	public void find(Object o) {}


	@Override
	public EntityManager getEntityManager() {return null;}


	@Override
	public void persist() {}


   @Override
   public RefreshType getRefreshType() {
      return null;
   }

   @Override
   public void setRefreshType(RefreshType refreshType) {

   }

   @Override
   public void remove(T entity) {
   }

   @Override
	public void persistOrUpdate() {}


	@Override
	public void rollback() {}


	@Override
	public void rollbackIfActive() {}


	@Override
	public void update() {}


	@Override
	public void setEntityManager(EntityManager entityManager) {	}


}
