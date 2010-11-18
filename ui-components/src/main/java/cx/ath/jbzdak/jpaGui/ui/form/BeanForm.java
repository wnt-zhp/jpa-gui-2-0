package cx.ath.jbzdak.jpaGui.ui.form;

import cx.ath.jbzdak.jpaGui.BeanHolder;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: Mar 16, 2010
 */
public class BeanForm<B,FE extends FormElement<?, ? super B, ?>, BH extends BeanHolder<? extends B>>
        extends AbstractBeanAwareForm<B, FE, BH>{
   @Override
   public void commit() {
      for (FE form : forms) {
         form.commit();
      }
   }

   @Override
   public void rollback() {
      for (FE form : forms) {
         form.rollback();
      }
   }

   @Override
   public void startEditing() {
      for (FE form : forms) {
         form.startEditing();
      }
   }

   @Override
   public void startViewing() {
      for (FE form : forms) {
         form.startViewing();
      }
   }
}
