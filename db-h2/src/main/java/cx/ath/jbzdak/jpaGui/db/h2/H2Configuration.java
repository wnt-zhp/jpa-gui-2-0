package cx.ath.jbzdak.jpaGui.db.h2;

import cx.ath.jbzdak.jpaGui.db.DefaultLifecycleAdministrator;
import cx.ath.jbzdak.jpaGui.db.HibernateDBConfiguration;
import cx.ath.jbzdak.jpaGui.db.JpaDbManager;
import cx.ath.jbzdak.jpaGui.db.lifecycleListenerPack.HibernateStartDbPack;
import org.h2.Driver;
import org.h2.api.DatabaseEventListener;
import org.hibernate.dialect.H2Dialect;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-12
 */
public class H2Configuration<DBM extends JpaDbManager, HLM extends DefaultLifecycleAdministrator<DBM, ? extends H2Configuration>>
        extends HibernateDBConfiguration<DBM, HLM, H2Configuration>{

   public static final String jdbcUrlPrefix = "jdbc:h2:";

   public Map<String, String> connectionProperties = new HashMap<String, String>();

   public URI datbaseUri;

   public H2Configuration(boolean suppresDefaults) {
      if(!suppresDefaults){
         setDefaults();
         lifecycleManager.addListenerPack(new HibernateStartDbPack<DBM>());
         lifecycleManager.addListenerPack(new H2CleanDbPack<DBM>());
         lifecycleManager.addListenerPack(new H2BackupPack<DBM>());
      }
   }

   private void setDefaults(){
      setDialect(H2Dialect.class.getCanonicalName());
      setDriverClassName(Driver.class.getCanonicalName());
      setAutoMixedMode(true);
   }

   private void updateJDBCURL(){
      StringBuilder url = new StringBuilder(jdbcUrlPrefix);
      if("file".equals(datbaseUri.getScheme())){
         url.append(new File(datbaseUri).getAbsolutePath());
      }else{
         url.append(datbaseUri.toASCIIString());
      }
      for(Map.Entry<String, String> property : connectionProperties.entrySet()){
         url.append(property.getKey());
         url.append("=");
         url.append(property.getValue());
         url.append(";");
      }
      setJDBCUrl(url.toString());
   }

   public void setDatbaseUri(URI datbaseUri) {
      this.datbaseUri = datbaseUri;
      updateJDBCURL();
   }

   public void setDatbaseFile(File datbaseFolder) {
      setDatbaseUri(datbaseFolder.toURI());
   }

   public void setLogLevel(H2LogLevel logLevel){
      connectionProperties.put("TRACE_LEVEL_FILE", String.valueOf(logLevel.getLevel()));
      updateJDBCURL();
   }

   public void setSysoutLevel(H2LogLevel logLevel){
      if(logLevel== H2LogLevel.SLF4J){
         throw new IllegalArgumentException("Cant set level" + logLevel + " for system out. Use for normal logging (setLogLevel)");
      }
      connectionProperties.put("TRACE_LEVEL_SYSTEM_OUT", String.valueOf(logLevel.getLevel()));
      updateJDBCURL();
   }

   public void setEventListener(Class<? extends DatabaseEventListener> clazz){
      connectionProperties.put("DATABASE_EVENT_LISTENER", "'" + clazz.getCanonicalName() + "'");
      updateJDBCURL();
   }

   public void setIgnoreCase(Boolean ignoreCase){
      connectionProperties.put("IGNORE_CASE", ignoreCase.toString().toUpperCase());
      updateJDBCURL();
   }

   public void setDBCloseDelay(Integer closeDelay){
      connectionProperties.put("DB_CLOSE_DELAY", closeDelay.toString());
      updateJDBCURL();
   }

   public void setCloseOnExit(Boolean closeOnExit){
      connectionProperties.put("DB_CLOSE_ON_EXIT", closeOnExit.toString().toUpperCase());
      updateJDBCURL();
   }

   public void setAutoMixedMode(Boolean autoMixedEnabled){
      connectionProperties.put("AUTO_SERVER", autoMixedEnabled.toString().toUpperCase());
      updateJDBCURL();
   }

   public void setTransactionIsolation(H2TransactionIsolation isolation){
      connectionProperties.put("DB_CLOSE_DELAY", String.valueOf(isolation.getNumber()));
      updateJDBCURL();
   }

   public void setCacheSize(Integer size){
      connectionProperties.put("CACHE_SIZE", size.toString());
      updateJDBCURL();
   }



}

