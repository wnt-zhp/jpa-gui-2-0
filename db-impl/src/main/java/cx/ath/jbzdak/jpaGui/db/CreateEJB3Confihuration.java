package cx.ath.jbzdak.jpaGui.db;

import org.hibernate.ejb.Ejb3Configuration;

import java.util.Map;

public class CreateEJB3Confihuration<T extends JpaDbManager> extends DefaultLifecycleListener<T, LifecycleAdministrator> {
      public CreateEJB3Confihuration() {
         super(1000, "CREATE_EJB3_CONFIGURATION");
      }

   @Override
   public void executePhase(T manager, LifecycleAdministrator administrator, Object... params) {
      HibernateDBConfiguration
      Ejb3Configuration ejb3Configuration = new Ejb3Configuration();
      ejb3Configuration.configure((String) administrator.getUserConfiguration().get("persistence-unit"), (Map) administrator.getUserConfiguration().get("hibernate-properties"));
      administrator.getUserConfiguration().put("ejb3-configuration", ejb3Configuration);
   }
}