package cx.ath.jbzdak.jpaGui.db.h2;

import cx.ath.jbzdak.jpaGui.db.HibernateDBConfiguration;
import cx.ath.jbzdak.jpaGui.db.JpaDbManager;
import cx.ath.jbzdak.jpaGui.db.lifecycleListenerPack.HibernateStartDbPack;
import cx.ath.jbzdak.jpaGui.db.lifecycleListenerPack.ReadBackupFile;
import org.h2.Driver;
import org.h2.api.DatabaseEventListener;
import org.hibernate.dialect.H2Dialect;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * By default installs following listener packs:
 *
 * <ul>
 *    <li>{@link cx.ath.jbzdak.jpaGui.db.lifecycleListenerPack.HibernateStartDbPack}</li>
 *    <li>{@link cx.ath.jbzdak.jpaGui.db.h2.H2CleanDbPack}</li>
 *    <li>{@link cx.ath.jbzdak.jpaGui.db.lifecycleListenerPack.ReadBackupFile}/li>
 *    <li>{@link cx.ath.jbzdak.jpaGui.db.h2.H2BackupPack}</li>
 * </ul>
 *
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-12
 */
public class H2Configuration<DBM extends JpaDbManager, USER_OBJECT>
        extends HibernateDBConfiguration<DBM, USER_OBJECT>{

   public static final String jdbcUrlPrefix = "jdbc:h2:";

   public Map<String, String> connectionProperties = new HashMap<String, String>();

   public URI datbaseUri;

   @SuppressWarnings({"OverriddenMethodCallDuringObjectConstruction"})
   public H2Configuration(boolean suppresDefaults) {
      if(!suppresDefaults){
         setDefaults();
         addListenerPack(new HibernateStartDbPack<DBM, H2Configuration>());
         addListenerPack(new H2CleanDbPack<DBM, H2Configuration>());
         addListenerPack(new ReadBackupFile<DBM,H2Configuration>());
         addListenerPack(new H2BackupPack<DBM, H2Configuration>());
      }
   }

   protected void setDefaults(){
      setDialect(H2Dialect.class.getCanonicalName());
      setDriverClassName(Driver.class.getCanonicalName());
      setAutoMixedMode(true);
   }

   protected void updateJDBCURL(){
      if(datbaseUri == null){
         return;
      }
      StringBuilder url = new StringBuilder(jdbcUrlPrefix);
      if("file".equals(datbaseUri.getScheme())){
         url.append(new File(datbaseUri).getAbsolutePath());
      }else{
         url.append(datbaseUri.toASCIIString());
      }
      for(Map.Entry<String, String> property : connectionProperties.entrySet()){
         url.append(";");
         url.append(property.getKey());
         url.append("=");
         url.append(property.getValue());         
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
         throw new IllegalArgumentException("Cant set level" + H2LogLevel.SLF4J + " for system out. Use for normal logging (setLogLevel)");
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

