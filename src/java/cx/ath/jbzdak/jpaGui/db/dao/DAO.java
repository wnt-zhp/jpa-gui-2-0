package cx.ath.jbzdak.jpaGui.db.dao;

import cx.ath.jbzdak.jpaGui.BeanHolder;

public interface DAO<T> extends BeanHolder<T> {

   /**
    * Opens db transaction (if transaction was closed),
    * and increments transaction counter. </br>
    * In any way: after call to this method all properties
    * of bean are readable, and writeable.
    */
	public boolean beginTransaction();

   /**
    * Decrements transaction counter and if it is zero commits transaction.
    * Afrer this call entity may or may not be in persistence context, if transaction
    * was commited - it will not be, if this call terminanted parent transaction it should
    * also detach entity.
    */
	public boolean commitTransaction();

   /**
    * Rollbacks transaction (and all parent transactions)
    */
	public void rollback();

   /**
    * Rollbacks transaction if it is active.
    */
	public boolean rollbackIfActive();

   /**
    * Persists bean to database. 
    */
	public void persist();

	public void update();

   /**
    * 
    */
	public void persistOrUpdate();

	public void find(Object o);

	public void clearEntity();

	public void createEntity();

   void remove();

	public boolean isAutoCreateEntity();

	@SuppressWarnings({"SameParameterValue"})
   public void setAutoCreateEntity(boolean autoCreateEntity);

	public boolean isTransactionManaged();

	public void setTransactionManaged(boolean transactionManaged);

   RefreshType getRefreshType();

   void setRefreshType(RefreshType refreshType);

}
