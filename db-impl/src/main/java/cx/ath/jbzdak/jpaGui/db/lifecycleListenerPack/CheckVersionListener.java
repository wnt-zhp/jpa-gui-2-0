package cx.ath.jbzdak.jpaGui.db.lifecycleListenerPack;


import cx.ath.jbzdak.jpaGui.db.DBManager;
import cx.ath.jbzdak.jpaGui.db.DefaultLifecycleListener;
import cx.ath.jbzdak.jpaGui.db.HibernateDBConfiguration;
import org.intellij.lang.annotations.Language;

import java.sql.*;
import java.util.Properties;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-10-21
 */
public class CheckVersionListener extends DefaultLifecycleListener<DBManager, HibernateDBConfiguration> {

   private final String sql;


     public CheckVersionListener(@Language("SQL") String sql) {
      super(2500, "CHECK_VERSION_LISTENER");
      this.sql = sql;
   }

   public CheckVersionListener(@Language("SQL") String sql, int priority) {
      super(priority, "CHECK_VERSION_LISTENER");
      this.sql = sql;
   }

   @Override
   public void executePhase() throws Exception {
      try{
         Object o = JDBCUtils.executeAndClose(getConnection(), new JDBCUtils.ConnectionClosure<Object>() {
            @Override
            public Object executeConnection(Connection connection) throws SQLException {
               return JDBCUtils.executeStatement(connection, new JDBCUtils.StatementClosure<Object>() {
                  @Override
                  public Object executeStatement(Statement statement) throws SQLException {
                     if(statement.execute(sql)){
                        ResultSet resultSet = statement.getResultSet();
                        try{
                           if(resultSet.next()){
                              return statement.getResultSet().getObject(1);
                           }else{
                              return null;
                           }
                        }finally {
                           resultSet.close();
                        }
                     }
                     return null;
                  }
               });
            }
         });
         if(o==null){
            lifecycleAdministartor.setDbVersion(null);
         }
         Integer version = null;
         if (o instanceof String) {
            String s = (String) o;
            version = Integer.valueOf(s);
         }
         if (o instanceof Number) {
            Number number = (Number) o;
            version = Integer.valueOf(number.intValue());
         }
         lifecycleAdministartor.setDbVersion(version);
      }catch (Exception e){
         lifecycleAdministartor.setDatabaseInitialized(false);
         lifecycleAdministartor.setDbVersion(null);
      }
   }

   private Connection getConnection() throws SQLException{
      String url = (String) lifecycleAdministartor.getUserConfiguration().get("jdbc-url");
      Properties connectionProperties = (Properties) lifecycleAdministartor.getUserConfiguration().get("jdbc-properties");
      Connection conn= null;
      if(connectionProperties == null){
         conn = DriverManager.getConnection(url);
      }else{
         conn = DriverManager.getConnection(url, connectionProperties);
      }
      return conn;
   }
}
