package cx.ath.jbzdak.jpaGui.db;

import edu.umd.cs.findbugs.annotations.Nullable;
import org.hibernate.ejb.Ejb3Configuration;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-11
 */
public class CreateEntityManagerFactoryEvent extends DefaultLifecycleListener<JpaDbManager>{
   public CreateEntityManagerFactoryEvent() {
      super(0, "CREATE_ENTITY_MANAGER_FACTORY");
   }

   @Override
   public void doTask(@Nullable JpaDbManager jpaDbManager, @Nullable Object... o) throws Exception {
      DefaultLifecycleAdministrator manager = (DefaultLifecycleAdministrator) o[0];
      Ejb3Configuration ejb3Configuration = (Ejb3Configuration) manager.getUserConfiguration().get("ejbConfiguration");
      jpaDbManager.entityManagerFactory = ejb3Configuration.buildEntityManagerFactory();
   }
}
