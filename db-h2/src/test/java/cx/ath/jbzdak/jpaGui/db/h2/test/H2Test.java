package cx.ath.jbzdak.jpaGui.db.h2.test;

import cx.ath.jbzdak.jpaGui.db.DBManager;
import cx.ath.jbzdak.jpaGui.db.dao.DAO;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-10-04
 */
public class H2Test {


   TestDatabase config;

   @Before
   public void before(){
      config = new TestDatabase();
   }

   @Test
   public void testOpenDB() throws Exception{
      config.getH2Configuration().startDB();
   }

   @Test
   public void testSaveEntity()  throws Exception{
      config.getH2Configuration().startDB();
      DBManager manager = config.getH2Configuration().getDbManager();
      DAO<TestBean> dao = manager.getDao(TestBean.class);
      dao.createEntity();
      dao.getBean().setName("foo");
      dao.persist();

   }

   @Test
   public void testDBStop()  throws Exception{
      config.getH2Configuration().startDB();
      config.getH2Configuration().closeDB();
   }

   /**
    * Trzeba dopisać synchronizacje bazy danych przy zamykaniu bazy.
    * @throws Exception
    */
   @Test
   public void testDBPersistence()  throws Exception{
      Long id;
      {
         config.getH2Configuration().startDB();
         DBManager manager = config.getH2Configuration().getDbManager();
         DAO<TestBean> dao = manager.getDao(TestBean.class);
         dao.beginTransaction();
         dao.createEntity();
         dao.getBean().setName("foo");
         dao.persist();
         dao.commitTransaction();
         id = dao.getBean().getId();         
         Object o = "sss";
         config.getH2Configuration().closeDB();
      }

      {
         config.getH2Configuration().startDB();
         DBManager manager = config.getH2Configuration().getDbManager();
         DAO<TestBean> dao = manager.getDao(TestBean.class);
         dao.find(id);
         Assert.assertEquals("foo", dao.getBean().getName());
      }
   }
}
