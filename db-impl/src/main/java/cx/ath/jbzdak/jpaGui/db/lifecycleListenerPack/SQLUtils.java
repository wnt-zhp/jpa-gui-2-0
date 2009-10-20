package cx.ath.jbzdak.jpaGui.db.lifecycleListenerPack;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-10-17
 */
class SQLUtils {

   static void executeStatement(Connection conn, Reader read)throws SQLException, IOException{
      Statement s = null;
      try{
         StringWriter stringWriter = new StringWriter();
         char[] buf = new char[1024];
         int charNum;
         while((charNum = read.read(buf))!=0){
            stringWriter.write(buf, 0, charNum);
         }
         s = conn.createStatement();
         s.execute(stringWriter.toString());
      }finally {
         if(s!=null)
            s.close();
      }

   }
}
