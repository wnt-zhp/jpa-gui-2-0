package cx.ath.jbzdak.jpaGui.ui.form;

import cx.ath.jbzdak.jpaGui.Utils;
import cx.ath.jbzdak.jpaGui.db.dao.DAO;
import org.slf4j.Logger;


@SuppressWarnings("unchecked")
public class DAOForm<T, BFE extends DAOFormElement> extends AbstractForm<T,BFE> {

   private static final Logger LOGGER = Utils.makeLogger();

   private DAO<T> dao;

	@Override
   public void startEditing(){
		if(getEntity()==null){
			setEntity(dao.getEntity()); //Utworzy dao jeśli trzeba i można
		}
		for(FormElement fe : forms){
			fe.startEditing();
		}
	}

	@Override
   public void startViewing(){
		if(getEntity()==null){
			setEntity(dao.getEntity()); //Utworzy dao jeśli trzeba i można
		}
		for(FormElement fe : forms){
			fe.startViewing();
		}
	}

	@Override
   public void rollback(){
		for(FormElement fe : forms){
			fe.rollback();
		}
	}

   @Override
   public void commit(){
		dao.beginTransaction();
		try{
			if(!checkErrors().isEmpty()){
				throw new IllegalStateException();
			}
			for(BFE fe : forms){
            fe.refreshEntity(dao.getEntity());
				fe.commit();
			}
			dao.persistOrUpdate();
			dao.closeTransaction();
		}catch (RuntimeException e) {
			try {
				dao.rollback();
			} catch (Exception e1) {
				LOGGER.warn("Exception while rolling back",e );
			}
			throw e;
		}
	}

   public DAO<T> getDao() {
		return dao;
	}

	public void setDao(DAO<T> dao) {
		this.dao = dao;
	}

	public T getEntity() {
		return dao.getEntity();
	}

	public void setEntity(T entity) {
		dao.setEntity(entity);
      for(BFE fe : forms){
         fe.setEntity(dao.getEntity());
      }
	}

}
