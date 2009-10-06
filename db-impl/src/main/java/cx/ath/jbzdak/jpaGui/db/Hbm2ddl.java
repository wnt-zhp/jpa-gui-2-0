package cx.ath.jbzdak.jpaGui.db;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-10-04
 */
public enum Hbm2ddl {
   CREATE("create"), VALIDATE("validate"), CREATE_DROP("create-drop"), UPDATE("update"); 

   private final String value;

   Hbm2ddl(String value) {
      this.value = value;
   }

   public String getValue() {
      return value;
   }
}
