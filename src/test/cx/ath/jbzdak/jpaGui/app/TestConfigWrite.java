package cx.ath.jbzdak.jpaGui.app;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
@SuppressWarnings({"ALL"})
public class TestConfigWrite {
       Map<String, ConfigEntry> sourceConfiguration = new HashMap<String, ConfigEntry>();
   {
      sourceConfiguration.put("value", DefaultConfigEntry.createStringEntry("value", "CB.value"));
      sourceConfiguration.put("intValue", DefaultConfigEntry.createConfigEntry("intValue", 2));
      sourceConfiguration.put("foo.value", DefaultConfigEntry.createConfigEntry("foo.value", "Foo.value"));
      sourceConfiguration.put("foo.dblValue", DefaultConfigEntry.createConfigEntry("foo.dblValue", "2.5"));
      sourceConfiguration.put("foo.baz.value", DefaultConfigEntry.createConfigEntry("foo.baz.value", "Baz.value"));
   }

   TestSource configSource = new TestSource();
   {
      configSource.setConfiguration(sourceConfiguration);
   }

   TestConfigRead.ConfigurationBean bean = new TestConfigRead.ConfigurationBean();

   @Before
   public void before(){
      bean.setValue("x1");
      bean.setIntValue(1);
      bean.getFoo().setValue("x2");
      bean.getFoo().setDblValue(2.5);
      TestConfigRead.Baz baz = new TestConfigRead.Baz();
      baz.setValue("x3");
      bean.getFoo().setBaz(baz);
      ConfigurationPackager.saveConfigurationFromBean(configSource, bean);
   }

   @Test
   public void test1(){
      Assert.assertEquals("x1", configSource.savedValues.get("value"));    
   }

   @Test
   public void noSet(){
      Assert.assertEquals(null, configSource.savedValues.get("foo.value"));
   }

   @Test
   public void test2(){
      Assert.assertEquals(1, configSource.savedValues.get("intValue"));    
   }

   @Test
   public void test3(){
      Assert.assertEquals(2.5, configSource.savedValues.get("foo.dblValue"));
   }

   @Test
      public void test4(){
         Assert.assertEquals("x3", configSource.savedValues.get("foo.baz.value"));
      }




   public static class TestSource extends SimpleConfigSource{

      Map<String, Object> savedValues = new HashMap<String, Object>();


      @Override
      public void saveConfiguration(String key, Object value) throws UnsupportedOperationException {
         savedValues.put(key, value);
      }

      @Override
      public boolean isReadOnly() {
         return false;
      }

      @Override
      public boolean isKeyWritable(String keyName) {
         return !"foo.value".equals(keyName);
      }
   }

}
