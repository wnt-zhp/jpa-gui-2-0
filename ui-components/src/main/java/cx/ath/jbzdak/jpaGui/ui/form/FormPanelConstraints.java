package cx.ath.jbzdak.jpaGui.ui.form;

import cx.ath.jbzdak.jpaGui.ClassHandler;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.map.DefaultedMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"WeakerAccess"})
public class FormPanelConstraints {

	private static final Map<String, Map<String, String>> defaultConstraints;

	private static final ClassHandler<Map<String, Map<String, String>>> rendererConstraints;

	static {
		 Map<String, Map<String, String>> c = new HashMap<String, Map<String,String>>();
		 c.put("default", Collections.unmodifiableMap(createDefaultConstraints()));
		 c.put("compact", Collections.unmodifiableMap(createCompactConstraints()));
       c.put("large", Collections.unmodifiableMap(createLargeConstraints()));
		 defaultConstraints = Collections.unmodifiableMap(c);
		 rendererConstraints = new ClassHandler<Map<String,Map<String,String>>>();
		 rendererConstraints.put(Object.class, defaultConstraints);
	}

	public static Map<String, String> getConstraints(String layout, Class<?> rendererClass){
		return rendererConstraints.getHandler(rendererClass).get(layout);
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Map<String, String>> createComponentConstraints(){
		return DefaultedMap.decorate(new HashMap(), new Transformer(){
			@Override
			public Object transform(Object input) {
				return defaultConstraints;
			}
		});

	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> createEmptyConstraints(){
		return DefaultedMap.decorate(new HashMap(10), "");
	}


	public static Map<String, String> createDefaultConstraints(){
		Map<String, String> emptyConstraints = createEmptyConstraints();
		emptyConstraints.put("layout", "fillx, hidemode 3, gap 2 2");
		emptyConstraints.put("columns", "[min!|fill, grow|min!|min!]");
		//emptyConstraints.put("rows", "");
		//emptyConstraints.put("nameLabel", "");
		//emptyConstraints.put("renderer", "");
		//emptyConstraints.put("errorBtn", "");
		//emptyConstraints.put("errorLabel", "newline, span 3");
		return emptyConstraints;
	}

   public static Map<String, String> createLargeConstraints(){
		Map<String, String> emptyConstraints = createDefaultConstraints();
		emptyConstraints.put("columns", "[|fill, grow||]");
		//emptyConstraints.put("rows", "");
		//emptyConstraints.put("nameLabel", "");
		//emptyConstraints.put("renderer", "");
		//emptyConstraints.put("errorBtn", "");
		//emptyConstraints.put("errorLabel", "newline, span 3");
		return emptyConstraints;
	}

	public static Map<String, String> createCompactConstraints(){
		Map<String, String> emptyConstraints =  createDefaultConstraints();
		emptyConstraints.put("columns", "[fill, grow|min!|min!]");
		//emptyConstraints.put("rows", "");
		emptyConstraints.put("nameLabel", "cell 0 0");
		emptyConstraints.put("renderer", "cell 0 1, gpx 500, growx");
		emptyConstraints.put("errorBtn", "cell 1 1");
      emptyConstraints.put("helpBtn", "cell 1 1");
		emptyConstraints.put("errorLabel", "cell 0 2 3 1");
		return emptyConstraints;
	}

}
