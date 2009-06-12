package cx.ath.jbzdak.jpaGui.app;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.iterators.TransformIterator;
import org.apache.commons.collections.map.CompositeMap;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public class CompositeConfigurationSource implements ConfigurationSource{

    private final SourceHolder readIterators = new SourceHolder();

    private final SourceHolder writeIterators = new SourceHolder();

    public void addConfigSource(ConfigurationSource cs, Integer proprity){
        readIterators.add(cs, proprity);
        if(!cs.isReadOnly()){
            writeIterators.add(cs);
        }
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public Map<String, ? extends ConfigEntry> getConfiguration() {
        return new CompositeMap((Map[]) readIterators.sources.toArray());
    }

    @Override
    public Set<String> getSupportedKeys() {
        Set<String> supportedKeys = new HashSet<String>();
        for(ConfigurationSource cs : readIterators){
            supportedKeys.addAll(cs.getSupportedKeys());
        }
        return  supportedKeys;
    }

    @Override
    public boolean isKeyWritable(String keyName) {
        for(ConfigurationSource cs : readIterators){
           if(!cs.isKeyWritable(keyName)){
            return true;
           }
        }
        return false;
    }

    @Override
    public boolean isReadOnly() {
        for(ConfigurationSource cs : writeIterators){
           if(!cs.isReadOnly()){
               return false;
           }
        }
        return true;
    }

    @Override
    public void saveConfiguration(String key, Object value) {
        for(ConfigurationSource cs : writeIterators){
             if(!cs.isKeyWritable(key)){
                 cs.saveConfiguration(key, value);
             }
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("readIterators", readIterators).
                append("writeIterators", writeIterators).
                toString();
    }
}

class SourceHolder implements Iterable<ConfigurationSource>{

    final List<Map.Entry<Integer, ConfigurationSource>> sources = new ArrayList<Map.Entry<Integer, ConfigurationSource>>();

    @SuppressWarnings({"ClassWithoutToString"})
    private final Comparator<Map.Entry<Integer, ConfigurationSource>> comparator = new Comparator<Map.Entry<Integer, ConfigurationSource>>() {
        @SuppressWarnings({"MethodWithMultipleReturnPoints"})
        @Override
        public int compare(Map.Entry<Integer, ConfigurationSource> o1, Map.Entry<Integer, ConfigurationSource> o2) {
            if(o1.getKey()==null){
                return o2.getKey()==null?0:-1;
            }
            if(o2.getKey()==null){
                return 1;
            }
            return o1.getKey() - o2.getKey();
        }
        
    };

    public void add(ConfigurationSource source){
        add(source, null);
    }

    public void add(ConfigurationSource source, Integer priority){
        sources.add(new AbstractMap.SimpleImmutableEntry<Integer, ConfigurationSource>(priority, source));
        Collections.sort(sources,comparator);        
    }

    @SuppressWarnings({"ClassWithoutToString", "unchecked"})
    @Override
    public Iterator<ConfigurationSource> iterator() {
        return (Iterator<ConfigurationSource>)new TransformIterator(sources.iterator(), new Transformer() {
            @Override
            public Object transform(Object input) {
                return ((Map.Entry<?,ConfigurationSource>)input).getValue();
            }
        });
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("sources", sources).
                toString();
    }
}
