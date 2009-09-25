package cx.ath.jbzdak.jpaGui.db;

import edu.umd.cs.findbugs.annotations.Nullable;

public class CloseEntityManagerFactory<T extends JpaDbManager> extends DefaultLifecycleListener<T> {
      public CloseEntityManagerFactory() {
         super(1, "CLOSE_EMF");
      }

   @Override
         public void doTask(@Nullable T t, @Nullable Object... o) throws Exception {
         t.entityManagerFactory.close();
      }
   }