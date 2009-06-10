package cx.ath.jbzdak.jpaGui.ui.form;

import cx.ath.jbzdak.jpaGui.db.dao.DAO;


@SuppressWarnings("unchecked")
public class DAOForm<T, BFE extends DAOFormElement> extends AbstractForm<T,BFE> {

   DAO<T> dao;

	T entity;

	public void startEditing(){
		if(entity==null){
			setEntity(dao.getEntity()); //Utworzy dao jeśli trzeba i można
		}
		for(FormElement fe : forms){
			fe.startEditing();
		}
	}

	public void startViewing(){
		if(entity==null){
			setEntity(dao.getEntity()); //Utworzy dao jeśli trzeba i można
		}
		for(FormElement fe : forms){
			fe.startViewing();
		}
	}

	public void rollback(){
		for(FormElement fe : forms){
			fe.rollback();
		}
	}

   public void commit(){
		dao.beginTransaction();
		try{
			if(!checkErrors().isEmpty()){
				return;
			}
			//System.out.println(entity);
			for(BFE fe : forms){
				fe.commit();
			}
			//System.out.println(entity);

			dao.persistOrUpdate();
			dao.closeTransaction();
		}catch (RuntimeException e) {
			try {
				dao.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
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
		if(this.entity != entity){
			this.entity = entity;
			dao.setEntity(entity);
			for(BFE fe : forms){
				fe.setEntity(dao.getEntity());
			}
		}
	}

}
