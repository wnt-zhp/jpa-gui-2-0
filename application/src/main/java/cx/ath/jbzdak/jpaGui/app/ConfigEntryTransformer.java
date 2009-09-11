package cx.ath.jbzdak.jpaGui.app;

import org.jdesktop.beansbinding.Converter;

import java.util.ArrayList;
import java.util.List;

/**
 * Nieużywana nietestowana.
 *
 * Docelowo ma pomagać w zapisywaniu configEntrych w bazie danych.
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-22
 */
public class ConfigEntryTransformer extends Converter<ConfigEntry, PersistableConfigEntry>{

   @Override
   public PersistableConfigEntry convertForward(ConfigEntry configEntry) {
      throw new UnsupportedOperationException();
   }

   @Override
   public ConfigEntry convertReverse(PersistableConfigEntry entry) {
      try {
         DefaultConfigEntry result = new DefaultConfigEntry();
         result.setName(entry.getEntryName());
         result.setSingle(entry.isSingle());
         result.setValueClass(Class.forName(entry.getValueClassName()));
         result.setShortDescription(entry.getShortDescription());
         result.setLongDescription(entry.getLongDesctiption());
         Validator validator =(Validator) Class.forName(entry.getValueClassName()).newInstance();
         result.setValidator(validator);
         if(entry.isSingle()){
            result.setSingleValue(validator.parseValue(entry.getValue()));
         }else{
            List values = new ArrayList();
            for(String value : entry.getValue().split(";")){
               values.add(validator.parseValue(value));
            }
            result.setValues(values);
         }
         return result;
      } catch (RuntimeException e){
        throw e;
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }
}
