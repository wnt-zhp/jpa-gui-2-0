package cx.ath.jbzdak.jpaGui.db.h2;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-12
 */
public enum H2TransactionIsolation {
   READ_UNCOMMITED(0), READ_COMMITED(3), SERIALIZABLE(1);

   private int number;

   H2TransactionIsolation(int number) {
      this.number = number;
   }

   public int getNumber() {
      return number;
   }
}
