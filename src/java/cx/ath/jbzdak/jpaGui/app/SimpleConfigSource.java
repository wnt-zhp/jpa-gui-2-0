package cx.ath.jbzdak.jpaGui.app;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public class SimpleConfigSource implements ConfigurationSource{

    Map<String, ? extends ConfigEntry> configuration;

    public void setConfiguration(Map<String, ? extends ConfigEntry> configuration) {
        this.configuration = configuration;
    }

    @Override
    public Map<String, ? extends ConfigEntry> getConfiguration() {
        return configuration;
    }

    @Override
    public Set<String> getSupportedKeys() {
        return configuration.keySet();
    }

    @Override
    public boolean isKeyWritable(String keyName) {
        return false;
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }

    @Override
    public void saveConfiguration(String key, Object value) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("configuration", configuration).
                toString();
    }
}
