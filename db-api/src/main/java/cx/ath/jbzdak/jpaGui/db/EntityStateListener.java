package cx.ath.jbzdak.jpaGui.db;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 * Date: 2009-04-14
 */
public interface EntityStateListener{

    public void propertyChanged(Object entityId, String propertyName,  Object oldValue, Object newValue);

    public void entityChanged(Object entityId, Object oldValue, Object newValue);
    
}
