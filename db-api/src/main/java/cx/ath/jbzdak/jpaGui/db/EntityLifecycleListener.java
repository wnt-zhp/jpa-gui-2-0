package cx.ath.jbzdak.jpaGui.db;

import cx.ath.jbzdak.jpaGui.db.dao.annotations.LifecyclePhase;

import javax.persistence.EntityManager;

public interface EntityLifecycleListener {

	public void lifecycleEvent(LifecyclePhase phase, Object entity, EntityManager manager);

   public boolean listensToPhase(LifecyclePhase phase);

}
