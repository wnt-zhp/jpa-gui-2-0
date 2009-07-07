package cx.ath.jbzdak.jpaGui.ui.form;

import cx.ath.jbzdak.jpaGui.BeanHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-24
 */
public abstract class AbstractForm<T,FE extends FormElement<?, ? super T, ?>> implements Form<FE, T> {

   protected final List<FE> forms = new ArrayList<FE>();

   private final List<Validator> validators = new ArrayList<Validator>();

   private BeanHolder<T> beanHolder;

   public void addValidator(Validator v){
      validators.add(v);
   }

   public boolean add(FE e) {
      e.setBeanHolder(beanHolder);
      return forms.add(e);
   }

   public boolean addAll(Collection<? extends FE> c) {
      for(FE fe : c){
         add(fe);
      }
      return true;
   }

   public boolean remove(FE o) {
      if(forms.contains(o)){
         o.setBeanHolder(null);
      }
      return forms.remove(o);
   }

   public List<Object> checkErrors(){
      List<Object> errors = new ArrayList<Object>();
      for(Validator v : validators){
         Object error = v.validate(beanHolder.getBean(), forms);
         if(error!=null){
            errors.add(error);
         }
      }
      for(FE fe : forms){
         if(fe.getErrorMessage()!=null){
            errors.add(fe.getErrorMessage());
         }
      }
      return errors;
   }

   public List<FE> getForms() {
      return Collections.unmodifiableList(forms);
   }

   public List<Validator> getValidators() {
      return Collections.unmodifiableList(validators);
   }

   public void setBeanHolder(BeanHolder<T> beanHolder) {
      this.beanHolder = beanHolder;
      for(FE fe : forms){
         fe.setBeanHolder(beanHolder);
      }
   }
}
