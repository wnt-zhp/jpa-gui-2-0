package cx.ath.jbzdak.jpaGui.ui.form;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-24
 */
public class SimpleForm<FE extends FormElement<?, T, ?>,T> extends AbstractForm<T,FE>{
   @Override
   public void startEditing() {
      for (FE fe : forms) {
         fe.startEditing();
      }
   }

   @Override
   public void startViewing() {
      for (FE fe : forms) {
          fe.startViewing();
      }
   }

   @Override
   public void rollback() {
      for (FE fe : forms) {
         fe.rollback();
      }
   }

   @Override
   public void commit() {
      if(!checkErrors().isEmpty()){
				return;
      }
      for(FE fe : forms){
         fe.commit();
      }
   }
}







