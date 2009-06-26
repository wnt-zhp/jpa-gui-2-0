package cx.ath.jbzdak.jpaGui;

import java.util.*;

public class ClassHandler<T> {

	Map<Class<?>, T> contents = new HashMap<Class<?>, T>();

	public T getHandler(Object forObject){
		return getHandler(forObject.getClass());
	}

	public T getHandler(Class<?> clazz){
		Deque<Class<?>> classesToCheck = new ArrayDeque<Class<?>>();
		classesToCheck.add(clazz);
		while(true){
			Class<?> c = classesToCheck.pollFirst();
			if(c==null){
				return null;
			}
			T handler = contents.get(c);
			if(handler!=null){
				return handler;
			}
			for(Class<?> i : c.getInterfaces()){
				classesToCheck.addLast(i);
			}
			if(c.getSuperclass()!=null){
				if(c.getSuperclass() != Object.class){
					classesToCheck.addFirst(c.getSuperclass());
				}else{
					classesToCheck.addLast(Object.class);
				}
			}
		}
	}

	public boolean isEmpty() {
		return contents.isEmpty();
	}

	public T put(Class<?> key, T value) {
		return contents.put(key, value);
	}

	public Collection<T> values() {
		return contents.values();
	}

	public void clear() {
		contents.clear();
	}

	@SuppressWarnings({"SuspiciousMethodCalls"})
    public Object remove(Object key) {
		return contents.remove(key);
	}


}
