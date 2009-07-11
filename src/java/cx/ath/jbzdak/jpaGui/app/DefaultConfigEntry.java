package cx.ath.jbzdak.jpaGui.app;

import edu.umd.cs.findbugs.annotations.Nullable;
import static org.apache.commons.lang.StringUtils.isEmpty;

import java.util.*;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-22
 */
public class DefaultConfigEntry<T> implements ConfigEntry<T>{

   @Nullable
   private Validator validator;

   private boolean single;

   private Class<T> valueClass;

   private String name;

   private String shortDescription;

   private String longDescription;

   private final List<T> values = new ArrayList<T>();

   @SuppressWarnings({"SameParameterValue"})
   public static ConfigEntry<String> createStringEntry(String name, String value, String shortDescription, String longDescription){
      DefaultConfigEntry<String> result = new DefaultConfigEntry<String>();
      if(isEmpty(name)){
         throw new IllegalArgumentException("Nie można użyuwać pustej nazwy dla ConfigEntryu");
      }
      result.name = name;
      result.shortDescription = shortDescription;
      result.longDescription = longDescription;
      result.values.add(value);
      result.single = true;
      result.validator = null;
      result.valueClass = String.class;
      return result;
   }

   public static ConfigEntry<String> createStringEntry(String name, String value, ResourceBundle bundle){
      DefaultConfigEntry<String> result = new DefaultConfigEntry<String>();
      if(isEmpty(name)){
         throw new IllegalArgumentException("Nie można użyuwać pustej nazwy dla ConfigEntryu");
      }
      result.name = name;
      String shordDescKey = name + ".shortDescription";
      result.shortDescription = bundle.containsKey(shordDescKey)?bundle.getString(shordDescKey):null;
      String longDescKey = name + ".longDescription";
      result.longDescription = bundle.containsKey(longDescKey)?bundle.getString(longDescKey):null;
      result.values.add(value);
      result.single = true;
      result.validator = null;
      result.valueClass = String.class;
      return result;
   }
   public static ConfigEntry<String> createStringEntry(String name, String value){
      return createStringEntry(name, value, null, null);
   }

   public boolean isSingle() {
      return single;
   }

   public Class<T> getValueClass() {
      return valueClass;
   }

   public String getName() {
      return name;
   }

   public String getShortDescription() {
      return shortDescription;
   }

   public String getLongDescription() {
      return longDescription;
   }

   @Override
   public T getSingleValue() {
      if(!single){
         throw new NonUniqueException();
      }
      if(values.size()==0){
         throw new NoSuchElementException();
      }
      return values.get(0);
   }

   @Override
   public List<T> getValues() {
      return Collections.unmodifiableList(values);
   }

   @Override
   public void setValues(List<T> values) throws ValidationException {
      if (validator!=null) {
         for (T value : values) {
            validator.validate(value);
         }
      }
      this.values.clear();
      this.values.addAll(values);
   }

   @Override
   public void setSingleValue(T t) throws ValidationException {
      if (!single) {
         throw new IllegalStateException("Can't add single value to not single entry");
      }
      if (validator!=null) {
         validator.validate(t);
      }
      if(values.size()==0){
         values.add(t);
         return;
      }
      values.set(0, t);
   }

   void setShortDescription(String shortDescription) {
      this.shortDescription = shortDescription;
   }

   void setLongDescription(String longDescription) {
      this.longDescription = longDescription;
   }

   void setValidator(Validator validator) {
      this.validator = validator;
   }

   void setSingle(boolean single) {
      this.single = single;
   }

   void setValueClass(Class<T> valueClass) {
      this.valueClass = valueClass;
   }

   void setName(String name) {
      this.name = name;
   }
}
