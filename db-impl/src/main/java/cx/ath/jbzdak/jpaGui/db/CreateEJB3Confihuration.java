package cx.ath.jbzdak.jpaGui.db;

import edu.umd.cs.findbugs.annotations.Nullable;
import org.hibernate.ejb.Ejb3Configuration;

import java.util.Map;

public class CreateEJB3Confihuration<T extends JpaDbManager> extends DefaultLifecycleListener<T> {
      public CreateEJB3Confihuration() {
         super(1000, "CREATE_EJB3_CONFIGURATION");
      }

      @Override
         public void doTask(@Nullable T t, @Nullable Object... o) throws Exception {
         LifecycleAdministrator<T, ?> administrator = (LifecycleAdministrator<T,?>) o[1];

         Ejb3Configuration ejb3Configuration = new Ejb3Configuration();
         ejb3Configuration.configure((String) administrator.getUserConfiguration().get("persistenceUnit"), (Map) administrator.getUserConfiguration().get("hibernateProperties"));
         administrator.getUserConfiguration().put("ejb3Configuration", ejb3Configuration);

      }
   }