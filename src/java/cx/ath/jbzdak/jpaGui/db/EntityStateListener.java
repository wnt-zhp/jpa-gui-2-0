package cx.ath.jbzdak.jpaGui.db;

/**
 * Created by IntelliJ IDEA.
 * User: jb
 * Date: 2009-04-14
 * Time: 23:11:20
 * To change this template use File | Settings | File Templates.
 */
public interface EntityStateListener {

    public void propertyChanged(Object entityId, String propertyName,  Object oldValue, Object newValue);

    public void entityChanged(Object entityId, Object oldValue, Object newValue);
    
}
