package cx.ath.jbzdak.jpaGui.db.dao.annotations;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 * Date: 2009-04-15
 * Time: 00:11:35
 * To change this template use File | Settings | File Templates.
 */
public @interface LifecycleListener {
    LifecyclePhase[] value();
}
