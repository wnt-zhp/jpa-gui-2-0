package cx.ath.jbzdak.jpaGui.db.dao;

import cx.ath.jbzdak.jpaGui.Utils;
import cx.ath.jbzdak.jpaGui.db.DBManager;
import cx.ath.jbzdak.jpaGui.db.dao.annotations.LifecyclePhase;
import javax.persistence.EntityManager;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-20
 */
public enum RefreshType {
   NONE() {
      @Override
      <T> T perform(EntityManager manager, T entity, DBManager dbManager) {
         return entity;
      }},
   MERGE() {
      @Override
      <T> T perform(EntityManager manager, T entity, DBManager dbManager) {
         if(!(Utils.isIdNull(entity) || manager.contains(entity))){
            return manager.merge(entity);
         }
         return entity;
      }},
   FIND() {
      @Override
      <T> T perform(EntityManager manager, T entity, DBManager dbManager) {
         entity =  manager.find((Class<? extends T>) entity.getClass(), Utils.getId(entity));
         dbManager.firePersEvent(entity, LifecyclePhase.PostLoad, manager);
         return entity;
      }};

   abstract <T> T perform(EntityManager manager, T entity, DBManager dbManager);
}
