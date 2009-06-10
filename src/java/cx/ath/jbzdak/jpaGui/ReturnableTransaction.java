package cx.ath.jbzdak.jpaGui;

import javax.persistence.EntityManager;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-20
 */
public abstract class ReturnableTransaction<T> {

   @SuppressWarnings({"WeakerAccess"})
   protected boolean closeEntityManager = false;

   public abstract T doTransaction(EntityManager entityManager) throws Exception;
}
