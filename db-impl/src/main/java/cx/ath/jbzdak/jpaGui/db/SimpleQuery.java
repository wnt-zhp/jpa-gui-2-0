package cx.ath.jbzdak.jpaGui.db;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-12
 */
public class SimpleQuery implements cx.ath.jbzdak.jpaGui.db.Query{

   private final EntityManager entityManager;

   private final javax.persistence.Query query;

   public SimpleQuery(EntityManager entityManager, Query query) {
      this.entityManager = entityManager;
      this.query = query;
   }

   @Override
   public void close() {
      entityManager.close();
   }

   public List getResultList() {
      return query.getResultList();
   }

   public Object getSingleResult() {
      return query.getSingleResult();
   }

   public int executeUpdate() {
      return query.executeUpdate();
   }

   public Query setMaxResults(int maxResult) {
      return query.setMaxResults(maxResult);
   }

   public Query setFirstResult(int startPosition) {
      return query.setFirstResult(startPosition);
   }

   public Query setHint(String hintName, Object value) {
      return query.setHint(hintName, value);
   }

   public Query setParameter(String name, Object value) {
      return query.setParameter(name, value);
   }

   public Query setParameter(String name, Date value, TemporalType temporalType) {
      return query.setParameter(name, value, temporalType);
   }

   public Query setParameter(String name, Calendar value, TemporalType temporalType) {
      return query.setParameter(name, value, temporalType);
   }

   public Query setParameter(int position, Object value) {
      return query.setParameter(position, value);
   }

   public Query setParameter(int position, Date value, TemporalType temporalType) {
      return query.setParameter(position, value, temporalType);
   }

   public Query setParameter(int position, Calendar value, TemporalType temporalType) {
      return query.setParameter(position, value, temporalType);
   }

   public Query setFlushMode(FlushModeType flushMode) {
      return query.setFlushMode(flushMode);
   }
}
