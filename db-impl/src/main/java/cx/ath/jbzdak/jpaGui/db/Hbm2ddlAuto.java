package cx.ath.jbzdak.jpaGui.db;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-11
 */
public enum Hbm2ddlAuto {
   VALIDATE("value"), UPDATE("update"), CREATE("create"), CREATE_DROP("create-drop");

   private final String value;

   Hbm2ddlAuto(String value) {
      this.value = value;
   }

   public String getValue() {
      return value;
   }
}
