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
public class TestConfigRead {

   Map<String, ConfigEntry> sourceConfiguration = new HashMap<String, ConfigEntry>();
   {
      sourceConfiguration.put("value", DefaultConfigEntry.createStringEntry("value", "CB.value"));
      sourceConfiguration.put("intValue", DefaultConfigEntry.createConfigEntry("intValue", 2));
      sourceConfiguration.put("foo.value", DefaultConfigEntry.createConfigEntry("foo.value", "Foo.value"));
      sourceConfiguration.put("foo.dblValue", DefaultConfigEntry.createConfigEntry("foo.dblValue", "2.5"));
      sourceConfiguration.put("foo.baz.value", DefaultConfigEntry.createConfigEntry("foo.baz.value", "Baz.value"));
   }

   TestConfigSource configSource = new TestConfigSource();
   {
     configSource.setConfiguration(sourceConfiguration);
   }

   ConfigurationBean bean;

   @Before
   public void initTest(){
      bean = ConfigurationPackager.getFromConfiguration(configSource, ConfigurationBean.class);
   }

   @Test
   public void testRead1(){
      Assert.assertEquals("CB.value", bean.getValue());
   }

   @Test
   public void testRead2(){
      Assert.assertEquals(2,  bean.getIntValue());
   }

   @Test
   public void testRead3(){
      Assert.assertEquals("Foo.value",  bean.getFoo().getValue());
   }

   @Test
   public void testRead4(){
      Assert.assertEquals(2.5,  bean.getFoo().getDblValue(), 0.0001);
   }

   @Test
   public void testRead5(){
      Assert.assertEquals("Baz.value",  bean.getFoo().getBaz().getValue());
   }

   public static class TestConfigSource extends SimpleConfigSource{

      @Override
      public void saveConfiguration(String key, Object value) throws UnsupportedOperationException {
         super.saveConfiguration(key, value);    //To change body of overridden methods use File | Settings | File Templates.
      }


   }

   public static  class ConfigurationBean{

      String value;

      int intValue;

      Foo foo = new Foo();

      public String getValue() {
         return value;
      }

      public void setValue(String value) {
         this.value = value;
      }

      public int getIntValue() {
         return intValue;
      }

      public void setIntValue(int intValue) {
         this.intValue = intValue;
      }

      public Foo getFoo() {
         return foo;
      }

      public void setFoo(Foo foo) {
         this.foo = foo;
      }
   }

   public static  class Foo{
      String value;

      public String getValue() {
         return value;
      }

      public void setValue(String value) {
         this.value = value;
      }

      public Double getDblValue() {
         return dblValue;
      }

      public void setDblValue(Double dblValue) {
         this.dblValue = dblValue;
      }

      public Baz getBaz() {
         return baz;
      }

      public void setBaz(Baz baz) {
         this.baz = baz;
      }

      double dblValue;

      Baz baz = new Baz();
   }

   public static class Baz{
      public String getValue() {
         return value;
      }

      public void setValue(String value) {
         this.value = value;
      }

      String value;

   }
}
