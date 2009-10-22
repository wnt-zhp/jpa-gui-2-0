package cx.ath.jbzdak.jpaGui.db.lifecycleListenerPack;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-10-21
 */
class JDBCUtils {
   public static interface ConnectionClosure<T>{
      T executeConnection(Connection connection)throws SQLException;
   }

   public static interface StatementClosure<T>{
      T executeStatement(Statement statement)throws SQLException;
   }

   public static <T> T executeAndClose(Connection connection, ConnectionClosure<T> closure) throws SQLException {
      try{
         return closure.executeConnection(connection);
      }finally {
         connection.close();
      }
   }

   public static <T> T executeStatement(Connection connection, StatementClosure<T> statementClosure) throws SQLException{
      Statement s = connection.createStatement();
      try{
         return statementClosure.executeStatement(s);
      }finally {
         s.close();
      }
   }


   static void executeScript(Connection conn, Reader read)throws SQLException, IOException {
      StringWriter stringWriter = new StringWriter();
      char[] buf = new char[1024];
      int charNum;
      while((charNum = read.read(buf))!=-1){
         stringWriter.write(buf, 0, charNum);
      }
      executeScript(conn, stringWriter.toString());
   }

   static void executeScript(Connection conn, String read) throws SQLException, IOException {
      boolean autoCommit = conn.getAutoCommit();
      try{
         conn.setAutoCommit(false);
         for(final String statementString : read.split(";")){
            if(statementString.trim().length()!=0){
               executeStatement(conn, new StatementClosure<Object>() {
                  @Override
                  public Object executeStatement(Statement statement) throws SQLException {
                     statement.execute(statementString);
                     return null;
                  }
               });
            }
         }
      }finally {
         conn.commit();
         conn.setAutoCommit(autoCommit);
      }
   }
}
