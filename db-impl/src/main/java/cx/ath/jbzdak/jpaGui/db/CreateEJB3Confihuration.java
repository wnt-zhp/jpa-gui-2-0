package cx.ath.jbzdak.jpaGui.db;

import org.hibernate.cfg.Environment;
import org.hibernate.ejb.Ejb3Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class CreateEJB3Confihuration<T extends JpaDbManager> extends DefaultLifecycleListener<T, HibernateDBConfiguration> {

   private static final Logger LOGGER = LoggerFactory.getLogger(CreateEJB3Confihuration.class);

   public CreateEJB3Confihuration() {
      super(1000, "CREATE_EJB3_CONFIGURATION");
   }

   @Override
   public void executePhase() {
      Ejb3Configuration ejb3Configuration = new Ejb3Configuration();
      ejb3Configuration.configure(lifecycleAdministartor.getPersistrenceUnit(), lifecycleAdministartor.getHibernateProperties());
      for(Map.Entry<String, String> entry : (Iterable<? extends Map.Entry<String, String>>) lifecycleAdministartor.getHibernateProperties().entrySet()){
         ejb3Configuration.setProperty(entry.getKey(), entry.getValue());
      }
      LOGGER.info("Starting database via url: {}", lifecycleAdministartor.getHibernateProperties().get("hibernate.connection.url"));
      lifecycleAdministartor.setEjb3Configuration(ejb3Configuration);
      lifecycleAdministartor.getUserConfiguration().put("jdbc-url", lifecycleAdministartor.getHibernateProperties().get(Environment.URL));
      if(lifecycleAdministartor.getHibernateProperties().containsKey(Environment.USER)){
         Map<String, String> jdbcProperties = new HashMap<String, String>();
         jdbcProperties.put("user", (String) lifecycleAdministartor.getHibernateProperties().get(Environment.USER));
         if(lifecycleAdministartor.getHibernateProperties().containsKey(Environment.PASS)){
            jdbcProperties.put("password", (String) lifecycleAdministartor.getHibernateProperties().get(Environment.PASS));
         }
         lifecycleAdministartor.getUserConfiguration().put("jdbc-url", jdbcProperties);
      }


   }
}