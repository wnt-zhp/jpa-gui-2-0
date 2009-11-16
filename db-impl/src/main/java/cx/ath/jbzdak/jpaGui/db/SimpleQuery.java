package cx.ath.jbzdak.jpaGui.db;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
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

   public SimpleQuery(EntityManager entityManager, javax.persistence.Query query) {
      this.entityManager = entityManager;
      this.query = query;
   }

   @Override
   public void close() {
      entityManager.close();
   }

   @Override
   public List getResultList() {
      return query.getResultList();
   }

   @Override
   public Object getSingleResult() {
      return query.getSingleResult();
   }

   @Override
   public int executeUpdate() {
      return query.executeUpdate();
   }

   @Override
   public Query setMaxResults(int maxResult) {
      query.setMaxResults(maxResult);
      return this;
   }

   @Override
   public Query setFirstResult(int startPosition) {
      query.setFirstResult(startPosition);
      return this;
   }

   @Override
   public Query setHint(String hintName, Object value) {
      query.setHint(hintName, value);
      return this;
   }

   @Override
   public Query setParameter(String name, Object value) {
      query.setParameter(name, value);
      return this;
   }

   @Override
   public Query setParameter(String name, Date value, TemporalType temporalType) {
      query.setParameter(name, value, temporalType);
      return this;
   }

   @Override
   public Query setParameter(String name, Calendar value, TemporalType temporalType) {
      query.setParameter(name, value, temporalType);
      return this;
   }

   @Override
   public Query setParameter(int position, Object value) {
      query.setParameter(position, value);
      return this;
   }

   @Override
   public Query setParameter(int position, Date value, TemporalType temporalType) {
      query.setParameter(position, value, temporalType);
      return this;
   }

   @Override
   public Query setParameter(int position, Calendar value, TemporalType temporalType) {
      query.setParameter(position, value, temporalType);
      return this;
   }

   @Override
   public Query setFlushMode(FlushModeType flushMode) {
      query.setFlushMode(flushMode);
      return this;
   }
}
