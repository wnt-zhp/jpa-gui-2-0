package cx.ath.jbzdak.jpaGui.app;

import edu.umd.cs.findbugs.annotations.Nullable;
import static org.apache.commons.lang.StringUtils.isEmpty;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.*;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-22
 */
public class DefaultConfigEntry<T> implements ConfigEntry<T>{

   private boolean single;

   private Class<T> valueClass;

   private String name;

   private String shortDescription;

   private String longDescription;

   private final List<T> values = new ArrayList<T>();

   public static <T> ConfigEntry<T> createConfigEntry(String name, T value, Class<T> valueClass,  String shortDescription, String longDescription){
       DefaultConfigEntry<T> result = new DefaultConfigEntry<T>();
       if(isEmpty(name)){
          throw new IllegalArgumentException("Nie można użyuwać pustej nazwy dla ConfigEntryu");
       }
       if(valueClass == null){
          throw new IllegalArgumentException("Can't set null valueClass in Config entry");
       }
       result.name = name;
       result.shortDescription = shortDescription;
       result.longDescription = longDescription;
       result.values.add(value);
       result.single = true;
       result.valueClass = valueClass;
       return result;
    }

   public static <T> ConfigEntry<T> createConfigEntry(String name, T value, String shortDescription, String longDescription){
      if(value == null){
         throw new IllegalAccessError("Value cant be null if valueClass is unspecified");
      }
      return createConfigEntry(name, value, (Class<T>) value.getClass(), shortDescription, longDescription);   
  }

      public static <T> ConfigEntry<T> createConfigEntry(String name, T value){
      if(value == null){
         throw new IllegalAccessError("Value cant be null if valueClass is unspecified");
      }
      return createConfigEntry(name, value, (Class<T>) value.getClass(), null, null);   
  }

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
      result.valueClass = String.class;
      return result;
   }



   public static ConfigEntry<String> createStringEntry(String name, String value, @Nullable ResourceBundle bundle){
      String shortDescription = null, longDescription = null;
      if(bundle != null){
         String shordDescKey = name + ".shortDescription";
         shortDescription = bundle.containsKey(shordDescKey)?bundle.getString(shordDescKey):null;
         String longDescKey = name + ".longDescription";
         longDescription = bundle.containsKey(longDescKey)?bundle.getString(longDescKey):null;
      }
      return createConfigEntry(name, value, String.class, shortDescription, longDescription);
   }
   public static ConfigEntry<String> createStringEntry(String name, String value){
      return createStringEntry(name, value, null, null);
   }

   @Override
   public boolean isSingle() {
      return single;
   }

   @Override
   public Class<T> getValueClass() {
      return valueClass;
   }

   @Override
   public String getName() {
      return name;
   }

   @Override
   public String getShortDescription() {
      return shortDescription;
   }

   @Override
   public String getLongDescription() {
      return longDescription;
   }

   @Override
   public T getSingleValue() {
      if(!single){
         throw new NonUniqueException();
      }
      if(values.isEmpty()){
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

      this.values.clear();
      this.values.addAll(values);
   }

   @Override
   public void setSingleValue(T t) throws ValidationException {
      if (!single) {
         throw new IllegalStateException("Can't add single value to not single entry");
      }
      if(values.isEmpty()){
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

   void setSingle(boolean single) {
      this.single = single;
   }

   void setValueClass(Class<T> valueClass) {
      this.valueClass = valueClass;
   }

   void setName(String name) {
      this.name = name;
   }


    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("values", values).
                append("longDescription", longDescription).
                append("single", single).
                append("valueClass", valueClass).
                append("name", name).
                append("shortDescription", shortDescription).
                toString();
    }
}
