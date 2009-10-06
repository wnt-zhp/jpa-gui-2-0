package cx.ath.jbzdak.jpaGui.db.h2.test;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-10-04
 */
public class TestProperties {
   public static Properties properties = new Properties();
   static{
      try {
         properties.load(TestProperties.class.getResourceAsStream("/test.properties"));
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}
