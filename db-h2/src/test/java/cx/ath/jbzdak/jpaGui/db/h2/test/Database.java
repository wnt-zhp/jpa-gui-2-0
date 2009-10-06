package cx.ath.jbzdak.jpaGui.db.h2.test;

import cx.ath.jbzdak.jpaGui.db.Hbm2ddl;
import cx.ath.jbzdak.jpaGui.db.h2.DefaultH2Configuration;
import cx.ath.jbzdak.jpaGui.db.h2.H2Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.rmi.server.UID;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-10-04
 */
public class Database {

   private Logger log = LoggerFactory.getLogger("Test");

   DefaultH2Configuration h2Configuration = new DefaultH2Configuration(false);

   private void prepareConfig(){
      File file = new File(Properties.properties.getProperty("db.dir"), new UID().toString().replaceAll("\\:", ""));
      log.debug("database file is " + file.getAbsolutePath());
      h2Configuration.setDatbaseUri(file.toURI());
      h2Configuration.setSchemaAutoCreate(Hbm2ddl.UPDATE);
   }

   public Database() {
      prepareConfig();
   }

   public H2Configuration getH2Configuration() {
      return h2Configuration;
   }
}
