package cx.ath.jbzdak.jpaGui.db;

import cx.ath.jbzdak.jpaGui.db.dao.DAO;
import cx.ath.jbzdak.jpaGui.db.dao.JPADao;
import org.apache.commons.collections.Factory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-11
 */
public class JpaDbManager implements DBManager<EntityManager>{

   protected EntityManagerFactory entityManagerFactory;

   private final Map<Class, Factory> daoFactories = new ConcurrentHashMap<Class, Factory>();

   @Override
   public EntityManager createProvider() {
      return entityManagerFactory.createEntityManager();
   }

   @Override
   public void executeTransaction(Transaction<EntityManager> entityManagerTransaction) {
      JPATransaction.execute(this, entityManagerTransaction);
   }

   @Override
   public <T> T executeTransaction(ReturnableTransaction<EntityManager, T> entityManagerTReturnableTransaction) {
      return JPATransaction.execute(this, entityManagerTReturnableTransaction);
   }

   @Override
   public <T> DAO<T> getDao(Class<T> clazz){
      Factory f = daoFactories.get(clazz);
      if(f!=null){
         return (DAO<T>) f.create();
      }
      return new JPADao<T>(this, clazz);
   }
   @Override
   public <T> DAO<T> getDao(T entity){
      DAO<T> dao = getDao((Class<T>)entity.getClass());
      dao.setBean(entity);
      return dao;
   }

   @Override
   public Query createQuery(String ejbQl) {
      EntityManager entityManager = createProvider();
      return new SimpleQuery(entityManager, entityManager.createQuery(ejbQl));
   }

   @Override
   public Query createNamedQuery(String queryName) {
      EntityManager entityManager = createProvider();
      return new SimpleQuery(entityManager, entityManager.createNamedQuery(queryName));
   }
}
