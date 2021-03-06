package cx.ath.jbzdak.jpaGui.app;

import cx.ath.jbzdak.jpaGui.Utils;
import cx.ath.jbzdak.jpaGui.utils.FileCall;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.iterators.EnumerationIterator;
import org.apache.commons.collections.iterators.TransformIterator;
import org.slf4j.Logger;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public class PropertyConfigurationSource implements ConfigurationSource{

   private static final Logger LOGGER = Utils.makeLogger();

   private final Properties properties;

   private final File propertiesFile;

   private boolean readOnly;

   public PropertyConfigurationSource(Properties properties) {
      this.properties = properties;
      this.propertiesFile = null;
   }

   private boolean isXML(){
      return propertiesFile.getName().endsWith(".xml");
   }

   public PropertyConfigurationSource(File propertiesFile) {
      this.propertiesFile = propertiesFile;
      this.properties = new Properties();
      if(propertiesFile.exists()){
         try {
            FileCall.read(propertiesFile, new FileCall.FileRead() {
               @Override
               public void read(FileInputStream fileInputStream) throws IOException {
                  if(isXML()){
                     properties.loadFromXML(fileInputStream);
                  }else{
                     properties.load(new InputStreamReader(fileInputStream, Charset.forName("UTF-8")));
                  }
               }
            });
         } catch (InvalidPropertiesFormatException e){
            LOGGER.warn("Invalid property format");
         } catch (IOException e) {
            LOGGER.warn("Error while loading property based coinfiguration");
         }

      }
   }

   public PropertyConfigurationSource(Properties properties, File propertiesFile) {
      this.properties = properties;
      this.propertiesFile = propertiesFile;
   }

   private ResourceBundle resourceBundle;

   public ResourceBundle getResourceBundle() {
      return resourceBundle;
   }

   public void setResourceBundle(ResourceBundle resourceBundle) {
      this.resourceBundle = resourceBundle;
   }

   @Override
   public Map<String, ? extends ConfigEntry> getConfiguration() {
      return new AbstractMap<String, DefaultConfigEntry>(){
         @Override
         public Set<Entry<String, DefaultConfigEntry>> entrySet() {
            return new AbstractSet<Entry<String, DefaultConfigEntry>>() {
               @Override
               @SuppressWarnings({"unchecked"})
               public Iterator<Entry<String, DefaultConfigEntry>> iterator() {
                  return new TransformIterator(new EnumerationIterator(properties.propertyNames()), new Transformer() {
                     @Override
                     public Object transform(Object input) {
                        String name = (String) input;
                        return new SimpleImmutableEntry(name, DefaultConfigEntry.createStringEntry(name, properties.getProperty(name), resourceBundle));
                     }
                  });
               }

               @Override
               public int size() {
                  return properties.size();
               }
            };
         }
      } ;
   }

   @Override
   public Set<String> getSupportedKeys() {
      return new AbstractSet<String>(){
         @Override
         @SuppressWarnings({"unchecked"})
         public Iterator<String> iterator() {
               return new EnumerationIterator(properties.propertyNames());
         }

         @Override
         public int size() {
            return properties.size();
         }
      };
   }

   @Override
   public boolean isKeyWritable(String keyName) {
      return !isReadOnly();
   }

   @Override
   public boolean isReadOnly() {
      return propertiesFile == null || (!propertiesFile.canWrite()) || readOnly;
   }

   public void setReadOnly(boolean readOnly) {
      this.readOnly = readOnly;
   }

   @Override
   public void saveConfiguration(String key, Object value) throws UnsupportedOperationException {
      if(!isKeyWritable(key)){
         throw new UnsupportedOperationException("Cant write this key, is properties file set?");
      }
      properties.setProperty(key, String.valueOf(value));
      try{
         FileCall.write(propertiesFile, new FileCall.FileWrite() {
            @Override
            public void write(FileOutputStream fileOutputStream) throws IOException {
               if(isXML()){
                  properties.storeToXML(fileOutputStream, "");
               }else{
                  properties.store(new OutputStreamWriter(fileOutputStream, Charset.forName("UTF-8")), "");
               }
            }
         });
      }catch (IOException e){
         throw new GenericRuntimeUserException(e);
      }
   }
}
