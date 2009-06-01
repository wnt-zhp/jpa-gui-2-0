package cx.ath.jbzdak.jpaGui.beanFormatter;

import cx.ath.jbzdak.jpaGui.ClassHandler;
import org.apache.commons.math.util.MathUtils;

import java.text.SimpleDateFormat;

public class RendererHandler {

	public static final ClassHandler<Renderer> createDefaultRenderers(){
		ClassHandler<Renderer> result = new ClassHandler<Renderer>();
		result.put(Object.class, new ObjectRenderer());
		result.put(Number.class, new NumberRenderer());
		result.put(Integer.class, new IntegerRenderer());
		result.put(java.util.Date.class, new DateRenderer());
		return result;
	}


	public static class ObjectRenderer implements Renderer{
		@Override
		public String render(Object object) {
			return object.toString();
		}
	}

	public static  class DateRenderer implements Renderer{
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		@Override
		public String render(Object object) {
			return object==null?"Nieznana data":format.format(object);
		}
	}

	public static  class NumberRenderer implements Renderer{
		@Override
		public String render(Object object) {
			return object==null?"Nieznana liczba ":( ""+ MathUtils.round(((Number)object).doubleValue(), 2));
		}
	}


	public static  class IntegerRenderer implements Renderer{
		@Override
		public String render(Object object) {
			return object==null?"Nieznana liczba ":""+ ((Number)object).intValue();
		}
	}

}
