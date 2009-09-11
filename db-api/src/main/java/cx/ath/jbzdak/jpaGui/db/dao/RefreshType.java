package cx.ath.jbzdak.jpaGui.db.dao;

import cx.ath.jbzdak.jpaGui.db.AdministrativeDBManager;
import cx.ath.jbzdak.jpaGui.db.dao.annotations.LifecyclePhase;
import cx.ath.jbzdak.jpaGui.utils.DBUtils;
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
      public <T> T perform(@Nullable EntityManager manager,@Nullable  T entity,@Nullable AdministrativeDBManager dbManager) {
         return entity;
      }},
   MERGE() {
      @Override
      public <T> T perform(@NonNull EntityManager manager,@NonNull  T entity,@NonNull AdministrativeDBManager dbManager) {
         if(!(DBUtils.isIdNull(entity) || manager.contains(entity))){
            return manager.merge(entity);
         }
         return entity;
      }},
   FIND() {
      @Override
      public <T> T perform(EntityManager manager, T entity, AdministrativeDBManager dbManager) {
         entity =  manager.find((Class<? extends T>) entity.getClass(), DBUtils.getId(entity));
         dbManager.firePersEvent(entity, LifecyclePhase.PostLoad, manager);
         return entity;
      }};

   public abstract <T> T perform(@NonNull EntityManager manager, @NonNull T entity, @NonNull AdministrativeDBManager dbManager);
}
