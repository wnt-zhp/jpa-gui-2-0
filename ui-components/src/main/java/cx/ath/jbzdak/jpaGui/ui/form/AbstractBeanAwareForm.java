package cx.ath.jbzdak.jpaGui.ui.form;

import cx.ath.jbzdak.jpaGui.BeanHolder;
import cx.ath.jbzdak.jpaGui.BeanHolderAware;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-07-11
 */
public abstract class AbstractBeanAwareForm<B,FE extends FormElement<?, ? super B, ?>, BH extends BeanHolder<? extends B>>
        extends AbstractForm<B, FE> implements BeanHolderAware<B, BH>{
   
   protected BH beanHolder;

   @Override
   public boolean add(FE e) {
      setBeanHolder(e, beanHolder);
      return super.add(e);
   }

    @Override
    public boolean remove(FE fe) {
       if(super.remove(fe)){
         setBeanHolder(fe, null);
          return true;
       }
       return false;
   }



   protected void setBeanHolder(FE e, BH beanHolder){
      if (e instanceof BeanHolderAware) {
         BeanHolderAware beanHolderAware = (BeanHolderAware) e;
         beanHolderAware.setBeanHolder(beanHolder);
      }
   }

   @Override
   public void setBeanHolder(BH beanHolder) {
      this.beanHolder = beanHolder;
      for(FE fe : forms){
         setBeanHolder(fe, beanHolder);
      }
   }

   @Override
   protected List<Object> validate() {
      List<Object> errors = new ArrayList<Object>();
       for(Validator v : validators){
         if (v instanceof BeanHolderAware) {
            BeanHolderAware beanHolderAware = (BeanHolderAware) v;
            beanHolderAware.setBeanHolder(beanHolder);
         }
         Object error = v.validate(forms);
         if(error!=null){
            errors.add(error);
         }
      }
      return  errors;
   }
}


