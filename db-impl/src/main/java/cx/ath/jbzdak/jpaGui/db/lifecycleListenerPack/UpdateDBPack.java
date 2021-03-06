package cx.ath.jbzdak.jpaGui.db.lifecycleListenerPack;

import cx.ath.jbzdak.jpaGui.db.DBLifecyclePhase;
import cx.ath.jbzdak.jpaGui.db.DBManager;
import cx.ath.jbzdak.jpaGui.db.DefaultLifecycleAdministrator;
import cx.ath.jbzdak.jpaGui.db.DefaultLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-10-17
 */
public abstract class UpdateDBPack  extends  DefaultLifecycleListenerPack<DBManager, DefaultLifecycleAdministrator>   {

   private static final Logger LOGGER = LoggerFactory.getLogger(UpdateDBPack.class);

   protected final NavigableSet<Integer> versions;

   /**
    * Return script updating database to specified version, from
    * version that is preceding this version in {@link #versions}.
    * @param version version to update
    * @return script
    */
   abstract Reader getScriptUpdatingTo(Integer version);

   public UpdateDBPack(Collection<Integer> versions) {
      super(Arrays.asList("get-jdbc-url"), "update-schema");
      this.versions = new TreeSet<Integer>(versions);

      addListener(EnumSet.of(DBLifecyclePhase.SHEMA_UPDATE), new Listener());
   }

   private class Listener extends DefaultLifecycleListener<DBManager, DefaultLifecycleAdministrator> {

      private Listener() {
         super(0,"UPDATE_SCHEMA");
      }

      @Override
      public void executePhase() throws Exception {
         LOGGER.info("Updating schema");
         String url = (String) lifecycleAdministartor.getUserConfiguration().get("jdbc-url");
         Properties connectionProperties = (Properties) lifecycleAdministartor.getUserConfiguration().get("jdbc-properties");
         Connection conn= null;
         try{
            if(connectionProperties == null){
               conn = DriverManager.getConnection(url);
            }else{
               conn = DriverManager.getConnection(url, connectionProperties);
            }
            conn.setAutoCommit(false);
            Integer currentVersion = lifecycleAdministartor.getDbVersion();
            currentVersion=currentVersion==null?0:currentVersion;
            NavigableSet<Integer> appropriateVersions = versions.tailSet(currentVersion, false);
            appropriateVersions.remove(currentVersion);
            if(appropriateVersions.isEmpty()){
//               LOGGER.warn("Fired schema update, but there are no update scripts. Either shema needs no update (so you need " +
//                       "to unset DefaultLifecycteAdministrator#schemaNeedsUpdate or you need to include apropriate scripts");
               LOGGER.info("Schema up to date");
               return;
            }
            for(Integer version : appropriateVersions){
               LOGGER.debug("Updating schema from version {} to {}", currentVersion, version);
               JDBCUtils.executeScript(conn, getScriptUpdatingTo(version));
               currentVersion = version;
            }
            conn.commit();
            LOGGER.info("Updating done");
         }catch (Exception e){
            LOGGER.warn("Updating failed due to exception", e);
            if(conn!=null){
               conn.rollback();
            }
            throw e;
         }finally {
            if(conn!=null){
               conn.close();
            }
         }
      }
   }
}
