package cx.ath.jbzdak.jpaGui.db;

import org.hibernate.ejb.Ejb3Configuration;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-11
 */
public class CreateEntityManagerFactoryEvent extends DefaultLifecycleListener<JpaDbManager, DefaultLifecycleAdministrator>{
   public CreateEntityManagerFactoryEvent() {
      super(0, "CREATE_ENTITY_MANAGER_FACTORY");
   }


   @Override
   public void executePhase(JpaDbManager manager, DefaultLifecycleAdministrator administrator, Object... params) {
      Ejb3Configuration ejb3Configuration = (Ejb3Configuration) administrator.getUserConfiguration().get("ejb3-configuration");
      manager.entityManagerFactory = ejb3Configuration.buildEntityManagerFactory();
      administrator.getUserConfiguration().remove("ejb3-configuration");
   }

}
