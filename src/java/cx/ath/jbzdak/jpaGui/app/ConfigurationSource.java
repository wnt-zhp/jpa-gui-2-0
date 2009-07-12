package cx.ath.jbzdak.jpaGui.app;

import java.util.Map;
import java.util.Set;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-23
 */
public interface ConfigurationSource {

   Map<String, ? extends ConfigEntry> getConfiguration();

   Set<String> getSupportedKeys();

   boolean isKeyWritable(String keyName);

   boolean isReadOnly();

   void saveConfiguration(String key, Object value) throws UnsupportedOperationException;

}
