package cx.ath.jbzdak.jpaGui.ui.form;

import cx.ath.jbzdak.jpaGui.Utils;
import cx.ath.jbzdak.jpaGui.db.dao.DAO;
import org.slf4j.Logger;


@SuppressWarnings("unchecked")
public class DAOForm<T, FE extends FormElement<?, T, ?> > extends AbstractForm<T,FE> {

   private static final Logger LOGGER = Utils.makeLogger();

   private DAO<T> dao;

	@Override
   public void startEditing(){
		for(FormElement fe : forms){
			fe.startEditing();
		}
	}

	@Override
   public void startViewing(){
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
				return;
			}
			for(FE fe : forms){
				fe.commit();
			}
			dao.persistOrUpdate();
			dao.commitTransaction();
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
}
