package cx.ath.jbzdak.jpaGui.db.dao;

import cx.ath.jbzdak.jpaGui.db.DBManager;
import cx.ath.jbzdak.jpaGui.utils.DBUtils;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

import javax.persistence.EntityManager;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-11
 */
public enum JPARefreshType {
   NONE(RefreshType.NONE) {
      @Override
      public <T> T perform(@Nullable EntityManager manager,@Nullable  T entity,@Nullable DBManager<EntityManager> dbManager) {
         return entity;
      }},
   MERGE(RefreshType.MERGE) {
      @Override
      public <T> T perform(@NonNull EntityManager manager,@NonNull  T entity,@NonNull DBManager<EntityManager> dbManager) {
         if(!(DBUtils.isIdNull(entity) || manager.contains(entity))){
            return manager.merge(entity);
         }
         return entity;
      }},
   FIND(RefreshType.FIND) {
      @Override
      public <T> T perform(EntityManager manager, T entity, DBManager<EntityManager> dbManager) {
         entity =  manager.find((Class<? extends T>) entity.getClass(), DBUtils.getId(entity));
         return entity;
      }};

   public static JPARefreshType map(RefreshType refreshType){
      switch (refreshType){
         case FIND:
            return FIND;
         case MERGE:
            return MERGE;
         case NONE:
            return NONE;
         default:
            throw new IllegalStateException();
      }
   };

   JPARefreshType(RefreshType refreshType) {
      this.refreshType = refreshType;
   }

   private final RefreshType refreshType;

   public abstract <T> T perform(@NonNull EntityManager manager, @NonNull T entity, @NonNull DBManager<EntityManager> dbManager);

   public RefreshType getRefreshType() {
      return refreshType;
   }
}
