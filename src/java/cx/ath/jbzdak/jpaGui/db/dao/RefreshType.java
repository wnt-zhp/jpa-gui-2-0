package cx.ath.jbzdak.jpaGui.db.dao;

import cx.ath.jbzdak.jpaGui.Utils;
import cx.ath.jbzdak.jpaGui.db.DBManager;
import cx.ath.jbzdak.jpaGui.db.dao.annotations.LifecyclePhase;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import javax.persistence.EntityManager;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-20
 */
public enum RefreshType {
   NONE() {
      @Override
      public <T> T perform(@Nullable EntityManager manager,@Nullable  T entity,@Nullable  DBManager dbManager) {
         return entity;
      }},
   MERGE() {
      @Override
      public <T> T perform(@NonNull EntityManager manager,@NonNull  T entity,@NonNull  DBManager dbManager) {
         if(!(Utils.isIdNull(entity) || manager.contains(entity))){
            return manager.merge(entity);
         }
         return entity;
      }},
   FIND() {
      @Override
      public <T> T perform(EntityManager manager, T entity, DBManager dbManager) {
         entity =  manager.find((Class<? extends T>) entity.getClass(), Utils.getId(entity));
         dbManager.firePersEvent(entity, LifecyclePhase.PostLoad, manager);
         return entity;
      }};

   public abstract <T> T perform(@NonNull EntityManager manager, @NonNull T entity, @NonNull DBManager dbManager);
}
