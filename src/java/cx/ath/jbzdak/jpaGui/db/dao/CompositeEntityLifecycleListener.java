package cx.ath.jbzdak.jpaGui.db.dao;

import cx.ath.jbzdak.jpaGui.db.DBManager;
import cx.ath.jbzdak.jpaGui.db.EntityLifecycleListener;
import cx.ath.jbzdak.jpaGui.db.dao.annotations.LifecyclePhase;
import javax.persistence.EntityManager;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 * Date: 2009-04-14
 * Time: 23:51:00
 * To change this template use File | Settings | File Templates.
 */
public class CompositeEntityLifecycleListener implements EntityLifecycleListener{

    private final DBManager dbManager;

    private final Class entityClazz;

    public CompositeEntityLifecycleListener(DBManager dbManager, Class entityClazz) {
        this.dbManager = dbManager;
        this.entityClazz = entityClazz;
    }

    private List<EntityLifecycleListener> getListeners(){
        return dbManager.getListener(entityClazz);
    }

    public boolean listensToPhase(LifecyclePhase phase) {
        for(EntityLifecycleListener listener : getListeners()){
            if(listener.listensToPhase(phase)){
                return true;
            }
        }
        return false;
    }

    public void lifecycleEvent(LifecyclePhase phase, Object entity, EntityManager manager) {
        for(EntityLifecycleListener listener : getListeners()){
            listener.lifecycleEvent(phase, entity, manager);
        }
    }
}
