package cx.ath.jbzdak.jpaGui.beanFormatter;

import cx.ath.jbzdak.jpaGui.ClassHandler;
import org.apache.commons.collections.map.DefaultedMap;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class RendererProvider {

	private final Map<String, Renderer> renderers = createDefaultRenderers();

	private final ClassHandler<Renderer> classBoundRenderers = RendererHandler.createDefaultRenderers();

	@SuppressWarnings("unchecked")
	private Map<String, Renderer>  createDefaultRenderers(){
		Map<String, Renderer> renderers = DefaultedMap.decorate(new HashMap(), new Renderer(){

			@Override
			public String render(Object object) {
				if(object==null){
					return "";
				}
				return classBoundRenderers.getHandler(object.getClass()).render(object);
			}
		});
		renderers.put("weekday", new WeekdayRenderer());
		renderers.put("firstUppercase", new FirstUppercase());
		return renderers;
	}


	public Renderer getRenderer(String name){
		return renderers.get(name);
	}

	public void addRenderer(String name, Renderer r){
		renderers.put(name, r);
	}


	public void addRenderer(Class<?> clazz, Renderer r){
		classBoundRenderers.put(clazz, r);
	}

	public static class WeekdayRenderer implements Renderer{

		@Override
		public String render(Object object) {
			try {
				SimpleDateFormat format = new SimpleDateFormat("EEEE");
				return format.format(object);
			} catch (RuntimeException e) {
				throw new RuntimeException("Error while formatting: '" + object + "'", e);
			}
		}

	}

	public static class FirstUppercase implements Renderer{

		@Override
		public String render(Object object) {
			String str = object==null?"":object.toString();
			if(str.length()==0){
				return "";
			}
			return Character.toUpperCase(str.charAt(0)) + str.substring(1);
		}

	}



}
