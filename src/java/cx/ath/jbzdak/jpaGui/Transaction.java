package cx.ath.jbzdak.jpaGui;

import cx.ath.jbzdak.jpaGui.db.DBManager;
import javax.persistence.EntityManager;

public abstract class Transaction {

	protected boolean closeEntityManager = false;

   public static final void execute(EntityManager manager, Transaction transaction){
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
            RuntimeException runtimeException = (RuntimeException) e;
            throw runtimeException;
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
   public static final <T> T execute(EntityManager manager, ReturnableTransaction<T> transaction){
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


	public static final void execute(DBManager manager, Transaction transaction){
		transaction.closeEntityManager= true;
		execute(manager.createEntityManager(), transaction);
	}

   public static final <T> T execute(DBManager manager, ReturnableTransaction<T> transaction){
		transaction.closeEntityManager= true;
		return execute(manager.createEntityManager(), transaction);
	}


	public abstract void doTransaction(EntityManager entityManager) throws Exception;



}
