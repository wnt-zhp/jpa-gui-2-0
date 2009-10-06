package cx.ath.jbzdak.jpaGui.db.h2.test;

import java.io.IOException;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-10-04
 */
public class Properties {
   public static java.util.Properties properties = new java.util.Properties();
   static{
      try {
         properties.load(Properties.class.getResourceAsStream("/test.properties"));
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}
