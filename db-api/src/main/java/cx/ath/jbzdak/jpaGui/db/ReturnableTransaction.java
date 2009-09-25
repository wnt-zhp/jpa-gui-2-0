package cx.ath.jbzdak.jpaGui.db;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-20
 */
public abstract class ReturnableTransaction<PROVIDER, T> {

   @SuppressWarnings({"WeakerAccess"})
   protected boolean closeEntityManager = false;

   @SuppressWarnings({"RedundantThrows"})
   public abstract T doTransaction(PROVIDER entityManager) throws Exception;
}
