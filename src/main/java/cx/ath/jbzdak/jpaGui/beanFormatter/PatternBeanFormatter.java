package cx.ath.jbzdak.jpaGui.beanFormatter;

import cx.ath.jbzdak.jpaGui.Utils;
import static cx.ath.jbzdak.jpaGui.Utils.makeLogger;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.map.LazyMap;
import static org.apache.commons.lang.StringUtils.isBlank;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.beansbinding.ObjectProperty;
import org.jdesktop.beansbinding.Property;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternBeanFormatter {

	private static final Pattern itemPattern = Pattern.compile("(?:\\{(\\#)(\\d+)?,?([\\w,]+)?\\})?\\{((\\d+)?\\.?([^{}\\#]*))\\}");

	private static final Pattern elPropPattern = Pattern.compile("\\#\\{[^\\{\\}]*\\}");

	private static final Logger logger = makeLogger();

	@SuppressWarnings("unchecked")
	private static Map<String, Property<Object,Object>> cretePropertyCache(){
		Transformer factory = new Transformer(){
			@Override
			public Object transform(Object input) {
				if(input==null || isBlank(input.toString())){
					return ObjectProperty.create();
				}
				String prop = input.toString();
				if(elPropPattern.matcher(prop).matches()){
					return ELProperty.create(prop);
				}else{
					return BeanProperty.create(prop);
				}
			}
		};
		return LazyMap.decorate(new HashMap(), factory);
	}

	public static String formatMessage(String message, Object... parameteters){
		return formatMessage(message, null, new RendererProvider(), parameteters);
	}

	private static String formatMessage(String message, Map<String, Property<Object, Object>> propertyCache, RendererProvider renderers, Object... parameteters){
	Matcher m = itemPattern.matcher(message);
		if(propertyCache == null){
			propertyCache = cretePropertyCache();
		}
		StringBuffer result = new StringBuffer();
		while(m.find()){
			int index = 0;
			int idxGroup;
			String propertyKey;
			String rendererNames = m.group(3);
			rendererNames=rendererNames==null?"":rendererNames;
			Property<Object, Object> p;
			if(m.group(2)!=null){
				idxGroup = 2;
				propertyKey = "#{" + m.group(4) + "}";
			}else{
				idxGroup = 5;
				propertyKey = m.group(6);
			}
			if(m.group(idxGroup)!=null){
				try{
					index = Integer.parseInt(m.group(idxGroup));
				}catch (NumberFormatException e) {
					logger.debug("Error while parsing message, couldnt parse parameter",e);
				}
			}
			//System.out.println("PROPERTY" + propertyKey);
			p = propertyCache.get(propertyKey);
			Object value = null;
			try {
				value = p.getValue(parameteters[index]);
			} catch (UnsupportedOperationException e) {
				logger.warn("Error while formatting message. Property is '{}' " +
						"parameter is '{}', message is '{}", Utils.wrap(p, parameteters[index], e.getMessage()));
         }
			value=value==null?"":value;
			for(String r : rendererNames.split(",")){
				Renderer rend = renderers.getRenderer(r);
				value = rend.render(value);
			}
			m.appendReplacement(result, Matcher.quoteReplacement(value.toString()));
		}
		m.appendTail(result);
		return result.toString();
	}

	private final String message;

	private final Map<String, Property<Object,Object>> propertiesCache;

	private final RendererProvider provider;

	public PatternBeanFormatter(String message) {
		super();
		this.message = message;
		this.propertiesCache  = cretePropertyCache();
		this.provider = new RendererProvider();
	}

	public PatternBeanFormatter(String message,
			Map<String, Property<Object, Object>> propertiesCache) {
		super();
		this.message = message;
		this.propertiesCache = propertiesCache;
		this.provider = new RendererProvider();
	}

	public PatternBeanFormatter(String message,
			Map<String, Property<Object, Object>> propertiesCache,
			RendererProvider provider) {
		super();
		this.message = message;
		this.propertiesCache = propertiesCache;
		this.provider = provider;
	}

	public String format(Object... parameters){
		return formatMessage(message, propertiesCache, provider, parameters);
	}



}
