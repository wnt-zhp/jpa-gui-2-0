package cx.ath.jbzdak.jpaGui.db;

import cx.ath.jbzdak.jpaGui.db.dao.DAO;

import javax.persistence.EntityManager;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-11
 */
public interface DBManager {
   @Deprecated
   public EntityManager createEntityManager();

   public void executeTransaction(Transaction transaction);

   public void executeTransaction(ReturnableTransaction transaction);

   public <T> DAO<T> getDao(Class<T> clazz);

   public <T> DAO<T> getDao(T entity);

}
