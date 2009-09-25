package cx.ath.jbzdak.jpaGui.db.h2;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-12
 */
public enum H2LogLevel {
   NONE(0), ERROR(1), INFO(2), DEBUG(3), SLF4J(4);


   private final int level;

   H2LogLevel(int level) {
      this.level = level;
   }

   public int getLevel() {
      return level;
   }
}
