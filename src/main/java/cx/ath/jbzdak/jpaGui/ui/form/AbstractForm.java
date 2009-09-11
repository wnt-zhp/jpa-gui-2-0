package cx.ath.jbzdak.jpaGui.ui.form;

import cx.ath.jbzdak.jpaGui.FormAware;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-24
 */
public abstract class AbstractForm<B,FE extends FormElement<?, ? super B, ?>> implements Form<B, FE> {

   protected final List<FE> forms = new ArrayList<FE>();
   protected final List<Validator> validators = new ArrayList<Validator>();

   public void addValidator(Validator<? super B> v){
      validators.add(v);
   }


   public List<Validator> getValidators() {
      return Collections.unmodifiableList(validators);
   }

   protected void setForm(FE e, Form form){
      if (e instanceof FormAware) {
         FormAware formAware = (FormAware) e;
         formAware.setForm(form);
      }
   }

   public boolean add(FE e) {
      setForm(e, this);
      return forms.add(e);
   }

   public void addAll(Collection<? extends FE> c) {
      for(FE fe : c){
         add(fe);
      }
   }

   public boolean remove(FE fe) {
      if(forms.remove(fe)){
         setForm(fe, null);
         return true;
      }
      return false;
   }

   protected List<Object> validate(){
      List<Object> errors = new ArrayList<Object>();
       for(Validator v : validators){
                Object error = v.validate(forms);
         if(error!=null){
            errors.add(error);
         }
      }
      return errors;
   }

   public List<Object> checkErrors(){
      List<Object> errors = validate();
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


}
