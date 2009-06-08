package cx.ath.jbzdak.zarlok.db;

import cx.ath.jbzdak.jpaGui.task.Task;
import javax.annotation.Nullable;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-06-08
 */
public class SetCollation extends Task<ZarlockDBManager> {
   public SetCollation() {
      super(-1000, "Ustaw kollacje");
   }

   @Override
   public void doTask(@Nullable ZarlockDBManager manager, @Nullable Object... o) throws Exception {
      manager.sendStatement("SET DATABASE COLLATION \"Polish\";");
   }
}
