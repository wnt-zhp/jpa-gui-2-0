package cx.ath.jbzdak.jpaGui.db;

import org.hibernate.cfg.Environment;
import org.hibernate.ejb.Ejb3Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-12
 */
public class HibernateDBConfiguration<DBM extends JpaDbManager,  USER_OBJECT>
   extends DefaultLifecycleAdministrator<DBM, USER_OBJECT>
{

   private final Map<String, String> hibernateProperties = new HashMap<String, String>();

   private String persistrenceUnit;

   private Ejb3Configuration ejb3Configuration;

   public HibernateDBConfiguration() {
      
   }

   public void setPersistrenceUnit(String persistrenceUnit) {
      this.persistrenceUnit = persistrenceUnit;
   }

   public void setEjb3Configuration(Ejb3Configuration ejb3Configuration) {
      this.ejb3Configuration = ejb3Configuration;
   }

   public Map<String, String> getHibernateProperties() {
      return hibernateProperties;
   }

   public String getPersistrenceUnit() {
      return persistrenceUnit;
   }

   public Ejb3Configuration getEjb3Configuration() {
      return ejb3Configuration;
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
      hibernateProperties.put(Environment.DIALECT, dialect);
   }

   public void setShowSql(Boolean show){
      hibernateProperties.put("hibernate.show_sql", show.toString());
   }

   public void setFormatSql(Boolean format){
       hibernateProperties.put("hibernate.format_sql", format.toString());
   }

     public void setSchemaAutoCreate(Hbm2ddl hbm2ddl){
       hibernateProperties.put(Environment.HBM2DDL_AUTO, hbm2ddl.getValue());
   }

}
