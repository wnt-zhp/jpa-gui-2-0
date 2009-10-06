package cx.ath.jbzdak.jpaGui.db.dao;

import cx.ath.jbzdak.jpaGui.db.AdministrativeDBManager;
import cx.ath.jbzdak.jpaGui.db.EntityLifecycleListener;
import cx.ath.jbzdak.jpaGui.db.dao.annotations.LifecyclePhase;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 * Date: 2009-04-14
 * Time: 23:51:00
 */
public class CompositeEntityLifecycleListener implements EntityLifecycleListener{

    private final AdministrativeDBManager dbManager;

    private final Class entityClazz;

    public CompositeEntityLifecycleListener(AdministrativeDBManager dbManager, Class entityClazz) {
        this.dbManager = dbManager;
        this.entityClazz = entityClazz;
    }

    private List<EntityLifecycleListener> getListeners(){
        return dbManager.getListener(entityClazz);
    }

    @Override
    public boolean listensToPhase(LifecyclePhase phase) {
        for(EntityLifecycleListener listener : getListeners()){
            if(listener.listensToPhase(phase)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void lifecycleEvent(LifecyclePhase phase, Object entity, EntityManager manager) {
        for(EntityLifecycleListener listener : getListeners()){
            listener.lifecycleEvent(phase, entity, manager);
        }
    }
}
