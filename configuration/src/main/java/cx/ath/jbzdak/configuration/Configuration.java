package cx.ath.jbzdak.configuration;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-12
 */
public class Configuration {

   private static final Configuration CONFIGURATION = new Configuration();

   public static Configuration getConfiguration() {
      return CONFIGURATION;
   }

   public Configuration() {
   }

   public Mode getMode(){
      return Mode.DEVELOPEMENT;
   }
   
}
