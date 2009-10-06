package cx.ath.jbzdak.jpaGui.db;

import javax.persistence.EntityManager;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-11
 */
@SuppressWarnings({"EmptyClass"})
public abstract class JPAReturnableTransaction<T> extends ReturnableTransaction<EntityManager, T>{
}
