package cx.ath.jbzdak.jpaGui.db.dao;

import cx.ath.jbzdak.common.PropertySupported;
import cx.ath.jbzdak.common.AbstractPropertySupported;

public class NoopDao<T> extends AbstractPropertySupported implements DAO<T> {

	private T entity;

	private boolean transactionWideEntityManager;

	private boolean autoCreateEntity = false;

	private boolean transactionManaged;


	@Override
   public T getBean() {
		return entity;
	}


	public boolean isTransactionWideEntityManager() {
		return transactionWideEntityManager;
	}


	@Override
   public boolean isAutoCreateEntity() {
		return autoCreateEntity;
	}


	@Override
   public boolean isTransactionManaged() {
		return transactionManaged;
	}


	@Override
   public void setBean(T entity) {
		this.entity = entity;
	}


	public void setTransactionWideEntityManager(boolean transactionWideEntityManager) {
		this.transactionWideEntityManager = transactionWideEntityManager;
	}


	@Override
   public void setAutoCreateEntity(boolean autoCreateEntity) {
		this.autoCreateEntity = autoCreateEntity;
	}


	@Override
   public void setTransactionManaged(boolean transactionManaged) {
		this.transactionManaged = transactionManaged;
	}


	@Override
	public boolean beginTransaction() {return false;}


	@Override
	public void clearEntity() {}


	@Override
	public boolean commitTransaction() {return true;}


	@Override
	public void createBean() {}


	@Override
	public void find(Object o) {}


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
   public void remove() {
   }

   @Override
	public void persistOrUpdate() {}


	@Override
	public void rollback() {}


	@Override
	public boolean rollbackIfActive() {return  true;}


	@Override
	public void update() {}

}
