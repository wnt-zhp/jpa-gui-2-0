package cx.ath.jbzdak.jpaGui.db;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-11
 */
public interface Query extends javax.persistence.Query{

   /**
    * Closes query and associated EntityManager 
    */
   void close();
}
