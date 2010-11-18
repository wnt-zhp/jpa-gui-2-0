package cx.ath.jbzdak.jpaGui;

import java.lang.reflect.Proxy;

/**
 * User: Jacek Bzdak jbdak@gmail.com
 * Date: May 2, 2010
 */
public interface EventMulticastProxy<L> {

   public void addProxyListener(L listener);

   public boolean removeProxylistener(L listener);


}
