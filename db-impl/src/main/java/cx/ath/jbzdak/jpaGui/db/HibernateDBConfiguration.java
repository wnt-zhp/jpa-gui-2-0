package cx.ath.jbzdak.jpaGui.db;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-12
 */
public class HibernateDBConfiguration<DBM extends JpaDbManager, LM extends DefaultLifecycleAdministrator<DBM, ? extends USER_OBJECT, LM>, USER_OBJECT>
        extends DefaultDbConfiguration<DBM, LM>{

   protected Map<String, String> hibernateProperties = new HashMap<String, String>();

   public HibernateDBConfiguration() {
      lifecycleManager.addListener(EnumSet.of(DBLifecyclePhase.PRE_START), new DefaultLifecycleListener<DBM, LifecycleAdministrator>(0, "FEED_PROPERTIES"){

         @Override
         public void executePhase(DBM manager, LifecycleAdministrator administrator, Object[] params) {
            administrator.getUserConfiguration().put("hibernateProperties", hibernateProperties);
         }
      });
   }

   public void setJDBCUrl(String url){
      hibernateProperties.put("hibernate.connection.url", url);
   }

   public void setUsername(String username){
      hibernateProperties.put("hibernate.connection.username", username);
   }

   public void setPassword(String password){
      hibernateProperties.put("hibernate.connection.password", password);
   }

   public void setDriverClassName(String driverClassName){
      hibernateProperties.put("hibernate.connection.driver_class", driverClassName);
   }

   public void setDialect(String dialect){
      hibernateProperties.put("hibernate.dialect", dialect);
   }

   public void setShowSql(Boolean show){
      hibernateProperties.put("hibernate.show_sql", show.toString());
   }

   public void setFormatSql(Boolean format){
       hibernateProperties.put("hibernate.format_sql", format.toString());
   }

   public void setPersistenceUnit(){

   }

}
