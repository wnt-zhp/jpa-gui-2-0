package cx.ath.jbzdak.jpaGui.db;

import static cx.ath.jbzdak.jpaGui.Utils.makeLogger;
import cx.ath.jbzdak.jpaGui.db.dao.annotations.LifecycleListener;
import cx.ath.jbzdak.jpaGui.db.dao.annotations.LifecyclePhase;
import javax.persistence.EntityManager;
import org.apache.commons.collections.Factory;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.DefaultedMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.slf4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class DefaultEntityLifecycleListener implements EntityLifecycleListener{

   private static final Logger LOGGER = makeLogger();

   private final MultiMap methods = MultiValueMap.decorate(DefaultedMap.decorate(new EnumMap(LifecyclePhase.class), new Factory(){
        public Object create() {
            return new ArrayList();
        }
    })) ;

    public DefaultEntityLifecycleListener(Class<?> entityClass) {
		super();
       for(Method m : entityClass.getMethods()){
          LifecycleListener listener = m.getAnnotation(LifecycleListener.class);
          if(listener==null) continue;
          if(m.getParameterTypes().length!=1 || ! EntityManager.class.isAssignableFrom(m.getParameterTypes()[0])){
             LOGGER.warn("Method {} is annotated with LifecycleListener, but its parameters are wrong" ,m);
             continue;
          }
          for(LifecyclePhase phase : listener.value()){
             methods.put(phase, m);
          }
       }
    }

	public static Method getListenerMethod(Class<? extends Annotation> annotationClass, Class<?> entityClass){
		for(Method m : entityClass.getMethods()){
			if(m.getAnnotation(annotationClass)!=null){
				if(m.getParameterTypes().length==1 &&
						m.getParameterTypes()[0].isAssignableFrom(EntityManager.class)){
					return m;
				}
			}
		}
		return null;
	}

	private void invokeWrap(Method m, Object obj, Object... args){
		try {
			m.invoke(obj, args);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

    public void lifecycleEvent(LifecyclePhase phase, Object entity, EntityManager manager) {
       for(Method m : (List<Method>)methods.get(phase)){
            invokeWrap(m, entity, manager);
        }
    }

    public boolean listensToPhase(LifecyclePhase phase) {
        return ((List)methods.get(phase)).size()!=0;
    }
}
