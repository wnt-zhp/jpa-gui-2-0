package cx.ath.jbzdak.jpaGui;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidParameterException;

/**
 * User: Jacek Bzdak jbdak@gmail.com
 * Date: May 2, 2010
 */
public class EventMulticastProxyTest {

   CounterListener listener1 = new CounterListener();
   CounterListener listener2 = new CounterListener();
   Object proxyObject;
   EventMulticastProxy proxy;
   TestListener proxyListener;


   @Before
   public void  before(){
      proxyObject = EventMulticastProxyFactory.createProxy(TestListener.class);
      listener1 = new CounterListener();
      listener2 = new CounterListener();
      proxyListener = (TestListener) proxyObject;
      proxy = (EventMulticastProxy) proxyObject;
   }

   @Test
   public void testAdd(){
      proxy.addProxyListener(listener1);
      proxy.addProxyListener(listener2);
   }

   @Test
   public void testFullMatch(){
      proxy.addProxyListener(listener1);
      proxy.addProxyListener(listener2);
      proxyListener.actionPerformed(new ActionEvent(this, 10, ""));
      proxyListener.actionPerformed(new ActionEvent(this, 10, ""));
      Assert.assertEquals(2, listener1.ii);
      Assert.assertEquals(2, listener2.ii);
   }

   @Test
   public void testPartialMatch(){
      proxy.addProxyListener(listener1);
      proxy.addProxyListener(listener2);
      proxyListener.actionPerformed(null);
      proxyListener.actionPerformed(null);
      Assert.assertEquals(2, listener1.ii);
      Assert.assertEquals(2, listener2.ii);
   }

   @Test()
   public void testPartialMatch2(){
      proxy.addProxyListener(listener1);
      proxy.addProxyListener(listener2);
      proxyListener.actionPerformed(null, (Float) null);
      Assert.assertEquals(0, listener1.ii);
      Assert.assertEquals(1, listener1.ff, 0.01);
   }

   @Test(expected = InvalidParameterException.class)
   public void testAddWrongType(){
      proxy.addProxyListener(new ActionListener(){
         public void actionPerformed(ActionEvent e) {
         }
      });
   }



   public class CounterListener implements TestListener {


      int ii;

      float ff;


      public void actionPerformed(ActionEvent e) {
         ii++;
      }

      public void actionPerformed(ActionEvent e, Integer r) {
         ii++;
      }


      public void actionPerformed(ActionEvent e, Float z) {
         ff++;
      }

   }

   public interface TestListener extends ActionListener {
      void actionPerformed(ActionEvent e);

      void actionPerformed(ActionEvent e, Integer r);

      void actionPerformed(ActionEvent e, Float z);
   }


}
