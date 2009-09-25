package cx.ath.jbzdak.jpaGui.db;

import cx.ath.jbzdak.jpaGui.Utils;

import javax.persistence.EntityManager;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-11
 */
public abstract class JPATransaction extends Transaction<EntityManager>{


   public static void execute(EntityManager manager, Transaction transaction){
      final boolean wasActive = manager.getTransaction().isActive();
      if(!wasActive){
         manager.getTransaction().begin();
      }
      try{
         transaction.doTransaction(manager);
         if(!wasActive){
            manager.getTransaction().commit();
         }
      }catch (Exception e) {
         try{
            if(manager.getTransaction().isActive())
               manager.getTransaction().rollback();
         }catch (Exception re) {
            Utils.makeLogger(3).warn("", re);
         }
         if (e instanceof RuntimeException) {
            throw (RuntimeException) e;
         }
         throw new TransactionException(e);
      }finally{
         if(transaction.closeEntityManager){
            manager.clear();
            manager.close();
         }
      }
   }

   @SuppressWarnings({"WeakerAccess"})
   public static <T> T execute(EntityManager manager, ReturnableTransaction<EntityManager, T> transaction){
      final boolean wasActive = manager.getTransaction().isActive();
      if(!wasActive){
         manager.getTransaction().begin();
      }
      try{
         T result = transaction.doTransaction(manager);
         if(!wasActive){
            manager.getTransaction().commit();
         }
         return result;
      }catch (Exception e) {
         try{
            if(manager.getTransaction().isActive())
               manager.getTransaction().rollback();
         }catch (Exception re) {
            Utils.makeLogger(3).warn("", re);
         }
         if(e instanceof RuntimeException){
            throw (RuntimeException) e;
         }
         throw new TransactionException(e);
      }finally{
         if(transaction.closeEntityManager){
            manager.clear();
            manager.close();
         }
      }
   }


	public static void execute(DBManager<EntityManager> manager, Transaction transaction){
		transaction.closeEntityManager= true;
		execute(manager.createProvider(), transaction);
	}

   public static <T> T execute(DBManager<EntityManager> manager, ReturnableTransaction<EntityManager, T> transaction){
		transaction.closeEntityManager= true;
		return execute(manager.createProvider(), transaction);
	}

}
