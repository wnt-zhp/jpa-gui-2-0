package cx.ath.jbzdak.jpaGui.db.h2.test;

import cx.ath.jbzdak.jpaGui.Utils;
import cx.ath.jbzdak.jpaGui.db.DBManager;
import cx.ath.jbzdak.jpaGui.db.JPATransaction;
import cx.ath.jbzdak.jpaGui.db.dao.DAO;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;

import javax.persistence.EntityManager;
import java.io.File;
import java.rmi.server.UID;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-10-04
 */
public class H2Test {

   private static final Logger LOGGER = Utils.makeLogger();

   Database config;


   @BeforeClass
   public static void beforeClass(){
      LOGGER.info("Directory '.' is '" + new File(".").getAbsolutePath() + "'" );
   }

   @Before
   public void before(){
      config = new Database();
   }

   @Test
   public void testOpenDB() throws Exception{
      config.getH2Configuration().startDB();
   }

   @Test
   public void testSaveEntity()  throws Exception{
      config.getH2Configuration().startDB();
      DBManager manager = config.getH2Configuration().getDbManager();
      DAO<FooBean> dao = manager.getDao(FooBean.class);
      dao.createEntity();
      dao.getBean().setName("foo");
      dao.persist();

   }

   @Test
   public void testDBStop()  throws Exception{
      config.getH2Configuration().startDB();
      config.getH2Configuration().closeDB();
   }

   public Long createTestBean(String name){
      DBManager manager = config.getH2Configuration().getDbManager();
      DAO<FooBean> dao = manager.getDao(FooBean.class);
      dao.beginTransaction();
      dao.createEntity();
      dao.getBean().setName("foo");
      dao.persist();
      dao.commitTransaction();
      return  dao.getBean().getId();
   }

   public void assertBean(Long id, String name){
      DBManager manager = config.getH2Configuration().getDbManager();
      DAO<FooBean> dao = manager.getDao(FooBean.class);
      dao.find(id);
      Assert.assertEquals("Odczytany bean jest inny od zapisanego !", name, dao.getBean().getName());
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
         DAO<FooBean> dao = manager.getDao(FooBean.class);
         dao.beginTransaction();
         dao.createEntity();
         dao.getBean().setName("foo");
         dao.persist();
         dao.commitTransaction();
         id = dao.getBean().getId();
         config.getH2Configuration().closeDB();
      }

      {
         config.getH2Configuration().startDB();
         DBManager manager = config.getH2Configuration().getDbManager();
         DAO<FooBean> dao = manager.getDao(FooBean.class);
         dao.find(id);
         Assert.assertEquals("Odczytany bean jest inny od zapisanego !","foo", dao.getBean().getName());
      }
   }

   @Test
   public void testTransactions() throws Exception {
      config.getH2Configuration().startDB();
      JPATransaction.execute(config.getH2Configuration().getDbManager(), new JPATransaction() {
         @Override
         public void doTransaction(EntityManager entityManager) throws Exception {
            FooBean bean = new FooBean();
            bean.setName("baz");
            entityManager.persist(bean);
         }
      });
      JPATransaction.execute(config.h2Configuration.getDbManager(), new JPATransaction() {
         @Override
         public void doTransaction(EntityManager entityManager) throws Exception {
            Assert.assertEquals("Odczytany bean jest inny od zapisanego !","baz", entityManager.find(FooBean.class, 1L).getName());
         }
      });
      config.getH2Configuration().closeDB();
   }


   @Test
   public void testBackup() throws Exception {
      config.getH2Configuration().startDB();
      Long id = createTestBean("foo");
      File file = new File(Properties.properties.getProperty("db.dir"), new UID().toString().replaceAll("[:\\-]", "") + ".sql");
      config.getH2Configuration().backupDB(file);
      config.getH2Configuration().closeDB();
      config = new Database();
      config.getH2Configuration().readBackup(file);
      config.getH2Configuration().startDB();
      assertBean(id, "foo");
   }
}
