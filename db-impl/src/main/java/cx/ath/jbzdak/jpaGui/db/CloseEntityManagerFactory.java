package cx.ath.jbzdak.jpaGui.db;

public class CloseEntityManagerFactory<T extends JpaDbManager, L extends LifecycleAdministrator> extends DefaultLifecycleListener<T, L> {
      public CloseEntityManagerFactory() {
         super(1, "CLOSE_EMF");
      }


   @Override
   public void executePhase() throws Exception {
      dbManager.entityManagerFactory.close();
   }

}