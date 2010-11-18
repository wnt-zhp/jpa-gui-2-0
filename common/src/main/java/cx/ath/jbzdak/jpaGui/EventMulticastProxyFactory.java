package cx.ath.jbzdak.jpaGui;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * User: Jacek Bzdak jbdak@gmail.com
 * Date: May 2, 2010
 */
public class EventMulticastProxyFactory {

   public static final String ADD_LISTENER_METHOD_NAME = "addProxyListener",
      REMOVE_LISTENER_METHOD_NAME  = "removeProxylistener";

   public static Object createProxy(Class lClass){
     return createProxy(lClass, Thread.currentThread().getContextClassLoader());
   }

   public static Object createProxy(Class lClass, ClassLoader classLoader){
      if(!lClass.isInterface()){
         throw new IllegalArgumentException();
      }

      return  Proxy.newProxyInstance(classLoader, new Class<?>[]{EventMulticastProxy.class, lClass}, new MyInvocationHandler(lClass));
   }

   private static class MyInvocationHandler<L> implements InvocationHandler {

      private final Class interfaceClass;

      List<Object> listeners = new CopyOnWriteArrayList<Object>();

      private MyInvocationHandler(Class interfaceClass) {
         this.interfaceClass = interfaceClass;
      }

      private void checkArgs(String methodName, Object[] args){
         if(args.length!=1){
            throw new InvalidParameterException("Method " + methodName + " called with wrong number of parameters. Expected one parameter");
         }
         if(args[0] == null){
            throw new InvalidParameterException("Invalid parameter null listeners not accepted");
         }
         if(!interfaceClass.isInstance(args[0])){
            throw new InvalidParameterException("Trying to add or remove instance of wrong type");
         }
      }


      private Method getMethod(Class listenerClass, Method originalMethod, Object[] args){
         try {
            return listenerClass.getDeclaredMethod(originalMethod.getName(), originalMethod.getParameterTypes());
         } catch (NoSuchMethodException e) {
            throw new UnsupportedOperationException("One of listeners cant handle method" + originalMethod);
         }
      }

      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
         if(ADD_LISTENER_METHOD_NAME.equals(method.getName())){
            checkArgs(ADD_LISTENER_METHOD_NAME, args);
            listeners.add(args[0]);
            return null;
         }
         if(REMOVE_LISTENER_METHOD_NAME.equals(method.getName())){
            checkArgs(REMOVE_LISTENER_METHOD_NAME, args);
            return listeners.remove(args[0]);
         }
         for(Object listener : listeners){
            Method m = getMethod(listener.getClass(), method, args);
            m.invoke(listener, args);
         }
         return null;
      }
   }
}
