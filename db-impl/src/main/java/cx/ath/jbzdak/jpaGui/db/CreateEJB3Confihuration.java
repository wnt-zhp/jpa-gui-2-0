package cx.ath.jbzdak.jpaGui.db;

import org.hibernate.ejb.Ejb3Configuration;

public class CreateEJB3Confihuration<T extends JpaDbManager> extends DefaultLifecycleListener<T, HibernateDBConfiguration> {
      public CreateEJB3Confihuration() {
         super(1000, "CREATE_EJB3_CONFIGURATION");
      }

   @Override
   public void executePhase() {
      Ejb3Configuration ejb3Configuration = new Ejb3Configuration();
      ejb3Configuration.configure(lifecycleAdministartor.getPersistrenceUnit(), lifecycleAdministartor.getHibernateProperties());
      lifecycleAdministartor.setEjb3Configuration(new Ejb3Configuration());}
}