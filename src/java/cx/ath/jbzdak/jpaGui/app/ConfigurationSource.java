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

   //void saveConfiguration(ConfigEntry entry) throws ConfigurationException;

   //void saveConfiguration(List<? extends ConfigEntry> entry) throws ConfigurationException;

   void saveConfiguration(String key, Object value);
}
