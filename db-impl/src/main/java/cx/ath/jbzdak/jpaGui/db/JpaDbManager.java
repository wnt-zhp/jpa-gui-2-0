package cx.ath.jbzdak.jpaGui.db;

import cx.ath.jbzdak.jpaGui.Utils;
import cx.ath.jbzdak.jpaGui.db.dao.DAO;
import cx.ath.jbzdak.jpaGui.db.dao.JPADao;
import org.apache.commons.collections.Factory;
import org.slf4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-11
 */
public class JpaDbManager implements DBManager<EntityManager>{

   private static final Logger LOGGER = Utils.makeLogger();

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

   @Override
   public void executeNativeStatement(final String statement) {
      /*
         Well it's a h2 hack. Basically some statements must be executed as a query not as a
         statement. (Especially SCRIPT TO foo.zip. So if executeUpdate fails we execute this stement
         as query. It has to be done in another transaction since after an exception current transaction
         is marked as rollback only. 
       */
      try{
         JPATransaction.execute(this, new JPAReturnableTransaction<Boolean>(){
            @Override
            public Boolean doTransaction(EntityManager entityManager) throws Exception {
               LOGGER.debug("Executing statement: '" + statement + "'");
               try {
                  entityManager.createNativeQuery(statement).executeUpdate();
                  return Boolean.TRUE;
               } catch (Exception e) {
                  return Boolean.FALSE;
               }
            }
         });
      }catch (RuntimeException e){
         JPATransaction.execute(this, new JPAReturnableTransaction<Boolean>(){
            @Override
            public Boolean doTransaction(EntityManager entityManager) throws Exception {
               LOGGER.debug("Executing statement: '" + statement + "'");
               entityManager.createNativeQuery(statement).getResultList();
               return Boolean.TRUE;
            }
         });
      }
   }
}
