package cx.ath.jbzdak.jpaGui.db.lifecycleListenerPack;

import cx.ath.jbzdak.jpaGui.Factory;
import cx.ath.jbzdak.jpaGui.db.DBLifecyclePhase;
import cx.ath.jbzdak.jpaGui.db.DBManager;
import cx.ath.jbzdak.jpaGui.db.DefaultLifecycleListener;
import cx.ath.jbzdak.jpaGui.db.LifecycleAdministrator;

import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Properties;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-10-17
 */
public class InitializeDBPack extends  DefaultLifecycleListenerPack<DBManager, LifecycleAdministrator>  {

   public InitializeDBPack(final Factory<Reader> initializeScript) {
      super(new HashSet<String>(Arrays.asList("get-jdbc-url", "init-jdbc")), "init-db-schema");
      addListener(EnumSet.of(DBLifecyclePhase.PRE_START), new DefaultLifecycleListener<DBManager, LifecycleAdministrator>(2000, "INITIALIZE_DB") {
         @Override
         public void executePhase() throws Exception {
            String url = (String) lifecycleAdministartor.getUserConfiguration().get("jdbc-url");
            Properties connectionProperties = (Properties) lifecycleAdministartor.getUserConfiguration().get("jdbc-url");
            Connection conn= null;
            Statement s = null;
            try{
               if(connectionProperties == null){
                  conn = DriverManager.getConnection(url);
               }else{
                  conn = DriverManager.getConnection(url, connectionProperties);
               }
               SQLUtils.executeStatement(conn, initializeScript.make());
            }finally {
               if(conn!=null){
                  conn.close();
               }
            }
         }
      } );
   }
}
