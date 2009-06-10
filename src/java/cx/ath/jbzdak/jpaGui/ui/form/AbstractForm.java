package cx.ath.jbzdak.jpaGui.ui.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-24
 */
public abstract class AbstractForm<T,FE extends FormElement> implements Form<FE> {

   List<FE> forms = new ArrayList<FE>();

   List<Validator> validators = new ArrayList<Validator>();

   T editedObject;

   public T getEditedObject() {
      return editedObject;
   }

   public void setEditedObject(T editedObject) {
      this.editedObject = editedObject;
   }

   public void addValidator(Validator v){
      validators.add(v);
   }

   public boolean add(FE e) {
      return forms.add(e);
   }

   public boolean addAll(Collection<? extends FE> c) {
      return forms.addAll(c);
   }

   public boolean remove(FE o) {
      return forms.remove(o);
   }

   public List<Object> checkErrors(){
      List<Object> errors = new ArrayList<Object>();
      for(Validator v : validators){
         Object error = v.validate(getEditedObject(), forms);
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
}
