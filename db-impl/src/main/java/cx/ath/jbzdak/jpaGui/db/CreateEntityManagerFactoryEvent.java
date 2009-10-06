package cx.ath.jbzdak.jpaGui.db;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-11
 */
public class CreateEntityManagerFactoryEvent extends DefaultLifecycleListener<JpaDbManager, HibernateDBConfiguration>{
   public CreateEntityManagerFactoryEvent() {
      super(0, "CREATE_ENTITY_MANAGER_FACTORY");
   }


   @Override
   public void executePhase() {
      dbManager.entityManagerFactory = lifecycleAdministartor.getEjb3Configuration().buildEntityManagerFactory();
      lifecycleAdministartor.setEjb3Configuration(null);
   }

}
