package cx.ath.jbzdak.jpaGui.db;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-05-06
 */
public class TransactionException extends RuntimeException{
   public TransactionException(Throwable cause) {
      super(cause);
   }
}
