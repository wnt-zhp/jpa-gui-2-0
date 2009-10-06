package cx.ath.jbzdak.jpaGui.db.dao.annotations;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 * Date: 2009-04-15
 */
public @interface LifecycleListener {
    LifecyclePhase[] value();
}
