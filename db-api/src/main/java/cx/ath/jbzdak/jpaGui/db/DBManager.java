package cx.ath.jbzdak.jpaGui.db;

import cx.ath.jbzdak.jpaGui.db.dao.DAO;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-11
 */
public interface DBManager<PROVIDER> {
   @Deprecated
   public PROVIDER createProvider();

   public Query createQuery(String ejbQl);

   public Query createNamedQuery(String queryName);

   public void executeNativeStatement(String statement);

   public void executeTransaction(Transaction<PROVIDER> transaction);

   public <T> T executeTransaction(ReturnableTransaction<PROVIDER, T> transaction);

   public <T> DAO<T> getDao(Class<T> clazz);

   public <T> DAO<T> getDao(T entity);


}
