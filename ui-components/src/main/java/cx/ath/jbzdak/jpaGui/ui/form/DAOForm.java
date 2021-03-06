package cx.ath.jbzdak.jpaGui.ui.form;

import org.slf4j.Logger;

import cx.ath.jbzdak.jpaGui.Utils;
import cx.ath.jbzdak.jpaGui.db.dao.DAO;


@SuppressWarnings("unchecked")
public class DAOForm<B, FE extends FormElement<?, B, ?> > extends AbstractBeanAwareForm<B,FE, DAO<? extends B>>{

   private static final Logger LOGGER = Utils.makeLogger();


	@Override
   public void startEditing(){
      beanHolder.beginTransaction();      
		for(FormElement fe : forms){
			fe.startEditing();
		}
	}

	@Override
   public void startViewing(){
      beanHolder.beginTransaction();
      try{
         for(FormElement fe : forms){
            fe.startViewing();
         }
      }finally {
         beanHolder.rollback();
      }
	}

	@Override
   public void rollback(){
      beanHolder.rollback();
		for(FormElement fe : forms){
			fe.rollback();
		}
	}

   @Override
   public void commit(){
		try{
			if(!checkErrors().isEmpty()){
				throw new IllegalStateException();
			}
			for(FE fe : forms){
				fe.commit();
			}
			beanHolder.persistOrUpdate();
			beanHolder.commitTransaction();
		}catch (RuntimeException e) {
			try {
				beanHolder.rollback();
			} catch (Exception e1) {
				LOGGER.warn("Exception while rolling back",e );
			}
			throw e;
		}
	}

}
