package cx.ath.jbzdak.jpaGui.app;

import cx.ath.jbzdak.jpaGui.Utils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.map.LazyMap;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Property;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public class ConfigurationPackager<T> {

   private static final Logger LOGGER = Utils.makeLogger();

   private final ConfigurationSource source;

   private final Class<T> configurationBeanClass;

   private boolean ignorePropertySetException;

   private boolean ignoreConfigSourceException;

   @SuppressWarnings({"unchecked", "ClassWithoutToString"})
   private final
   Map<String, Property<Object, Object>> propertyCache =
           LazyMap.decorate(new HashMap(),new Transformer() {
              @Override
              public Object transform(Object input) {
                 return BeanProperty.create((String)input);
              }
           } );


   public static <T> T getFromConfiguration(ConfigurationSource source, Class<T> beanClass){
      ConfigurationPackager<T> pack = new ConfigurationPackager<T>(source, beanClass);
      pack.setIgnoreConfigSourceException(false);
      pack.setIgnorePropertySetException(false);
      return  pack.createConfigurationBean();
   }

   @SuppressWarnings({"unchecked"})
   public static <T> void saveConfigurationFromBean(ConfigurationSource source, T bean){
      if(bean==null || source == null){
         throw new IllegalArgumentException("Parameters can't be null");
      }
      ConfigurationPackager<T> pack = new ConfigurationPackager<T>(source, (Class<T>) bean.getClass());
      pack.writeConfiguration(bean);
   }

   private ConfigurationPackager(ConfigurationSource source, Class<T> configurationBeanClass) {
      this.source = source;
      this.configurationBeanClass = configurationBeanClass;
   }

   protected T instantiate(){
      try {
         return configurationBeanClass.newInstance();
      } catch (InstantiationException e) {
         throw new RuntimeException(e);
      } catch (IllegalAccessException e) {
         throw new RuntimeException(e);
      }
   }

   public boolean isIgnorePropertySetException() {
      return ignorePropertySetException;
   }

   public void setIgnorePropertySetException(boolean ignorePropertySetException) {
      this.ignorePropertySetException = ignorePropertySetException;
   }

   public boolean isIgnoreConfigSourceException() {
      return ignoreConfigSourceException;
   }

   public void setIgnoreConfigSourceException(boolean ignoreConfigSourceException) {
      this.ignoreConfigSourceException = ignoreConfigSourceException;
   }

   protected Object convertToBean(Object value, ConfigEntry ce, Object configBean, Property property){
      Class targetType = property.getWriteType(configBean);
      if(targetType.isInstance(value)){
         return value;
      }
      return Converter.defaultConvert(value, targetType);
   }

   protected void setProprtty(Object configBean, ConfigEntry<Object> ce){
      Property<Object, Object> property = propertyCache.get(ce.getName());
      try{
         //LOGGER.info("{} property: {}", convertToBean(ce.getSingleValue(), ce, configBean, property).getClass(), property);
         property.setValue(configBean, convertToBean(ce.getSingleValue(), ce, configBean, property));
      }catch (NullPointerException e){
         if(ignorePropertySetException){
            LOGGER.warn("Exception while setting property {} on configuration bean", ce.getName());
            LOGGER.warn("Exception while setting property on configuration bean", e);
         }else{
            throw new RuntimeConfigurationException("Null pointer exception when setting property " +
                    "was something not initialized?", e);
         }
      }catch (RuntimeException e){
         if(ignoreConfigSourceException){
            LOGGER.warn("Exception while setting property {} on configuration bean", ce.getName());
            LOGGER.warn("Exception while setting property on configuration bean", e);
         }else{
            throw e;
         }
      }
   }

   public T createConfigurationBean(){
      T configBean = instantiate();
      for(ConfigEntry<Object> ce : source.getConfiguration().values()){
         setProprtty(configBean, ce);
      }
      return configBean;
   }

   protected Object convertToConfiguration(ConfigEntry ce, Object configBean, Object settedValue){
      Class targetType = ce.getValueClass();
      if(targetType.isInstance(settedValue)){
         return settedValue;
      }
      return Converter.defaultConvert(settedValue, targetType);
   }

   protected void writeBean(Object configBean, ConfigEntry ce){
      Property<Object, Object> property = propertyCache.get(ce.getName());
      Object val ;
      try{
         val = property.getValue(configBean);
         source.saveConfiguration(ce.getName(), val);
      }catch (RuntimeException re){
         if(ignoreConfigSourceException){
            LOGGER.warn("Exception while saving configuration (key {})", ce.getName());
            LOGGER.warn("Exception while saving configuration", re);
         }else{
            throw re;
         }
      }

   }

   public void writeConfiguration(T configBean){
      for(ConfigEntry<Object> ce : source.getConfiguration().values()){
         if(source.isKeyWritable(ce.getName())){
            writeBean(configBean, ce);
         }
      }
   }


}

@SuppressWarnings({"ALL"})
abstract class Converter<S, T> {

    /**
     * Converts a value from the source type to the target type.
     * Can throw a {@code RuntimeException} to indicate a problem
     * with the conversion.
     *
     * @param value the source value to convert
     * @return the value, converted to the target type
     */
    public abstract T convertForward(S value);

    /**
     * Converts a value from the target type to the source type.
     * Can throw a {@code RuntimeException} to indicate a problem
     * with the conversion.
     *
     * @param value the target value to convert
     * @return the value, converted to the source type
     */
    public abstract S convertReverse(T value);

    static final Converter BYTE_TO_STRING_CONVERTER = new Converter() {
        public Object convertForward(Object value) {
            return Byte.toString((Byte)value);
        }

        public Object convertReverse(Object value) {
            return Byte.parseByte((String)value);
        }
    };

    static final Converter SHORT_TO_STRING_CONVERTER = new Converter() {
        public Object convertForward(Object value) {
            return Short.toString((Short)value);
        }

        public Object convertReverse(Object value) {
            return Short.parseShort((String)value);
        }
    };

    static final Converter INT_TO_STRING_CONVERTER = new Converter() {
        public Object convertForward(Object value) {
            return Integer.toString((Integer)value);
        }

        public Object convertReverse(Object value) {
            return Integer.parseInt((String)value);
        }
    };

    static final Converter LONG_TO_STRING_CONVERTER = new Converter() {
        public Object convertForward(Object value) {
            return Long.toString((Long)value);
        }

        public Object convertReverse(Object value) {
            return Long.parseLong((String)value);
        }
    };

    static final Converter FLOAT_TO_STRING_CONVERTER = new Converter() {
        public Object convertForward(Object value) {
            return Float.toString((Float)value);
        }

        public Object convertReverse(Object value) {
            return Float.parseFloat((String)value);
        }
    };

    static final Converter DOUBLE_TO_STRING_CONVERTER = new Converter() {
        public Object convertForward(Object value) {
            return Double.toString((Double)value);
        }

        public Object convertReverse(Object value) {
            return Double.parseDouble((String)value);
        }
    };

    static final Converter CHAR_TO_STRING_CONVERTER = new Converter() {
        public Object convertForward(Object value) {
            return ((Character)value).toString();
        }

        public Object convertReverse(Object value) {
            String strVal = (String)value;

            if (strVal.length() != 1) {
                throw new IllegalArgumentException("String doesn't represent a char");
            }

            return strVal.charAt(0);
        }
    };

    static final Converter BOOLEAN_TO_STRING_CONVERTER = new Converter() {
        public Object convertForward(Object value) {
            return ((Boolean)value).toString();
        }

        public Object convertReverse(Object value) {
            return new Boolean((String)value);
        }
    };

    static final Converter INT_TO_BOOLEAN_CONVERTER = new Converter() {
        public Object convertForward(Object value) {
            if (((Integer)value).intValue() == 0) {
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
        }

        public Object convertReverse(Object value) {
            if (((Boolean)value).booleanValue()) {
                return 1;
            }
            return 0;
        }
    };

    static final Converter BIGINTEGER_TO_STRING_CONVERTER = new Converter() {
        public Object convertForward(Object value) {
            return ((BigInteger)value).toString();
        }

        public Object convertReverse(Object value) {
            return new BigInteger((String)value);
        }
    };

    static final Converter BIGDECIMAL_TO_STRING_CONVERTER = new Converter() {
        public Object convertForward(Object value) {
            return ((BigDecimal)value).toString();
        }

        public Object convertReverse(Object value) {
            return new BigDecimal((String)value);
        }
    };

    static final Object defaultConvert(Object source, Class<?> targetType) {
        Class sourceType = source.getClass();

        if (sourceType == targetType) {
            return source;
        }


        if (targetType == String.class) {
            if (sourceType == Byte.class) {
                return BYTE_TO_STRING_CONVERTER.convertForward(source);
            } else if (sourceType == Short.class || sourceType == short.class) {
                return SHORT_TO_STRING_CONVERTER.convertForward(source);
            } else if (sourceType == Integer.class || sourceType == int.class) {
                return INT_TO_STRING_CONVERTER.convertForward(source);
            } else if (sourceType == Long.class || sourceType == long.class) {
                return LONG_TO_STRING_CONVERTER.convertForward(source);
            } else if (sourceType == Float.class || sourceType == float.class) {
                return FLOAT_TO_STRING_CONVERTER.convertForward(source);
            } else if (sourceType == Double.class || sourceType == double.class) {
                return DOUBLE_TO_STRING_CONVERTER.convertForward(source);
            } else if (sourceType == Boolean.class || sourceType == boolean.class) {
                return BOOLEAN_TO_STRING_CONVERTER.convertForward(source);
            } else if (sourceType == Character.class || sourceType == char.class) {
                return CHAR_TO_STRING_CONVERTER.convertForward(source);
            } else if (sourceType == BigInteger.class) {
                return BIGINTEGER_TO_STRING_CONVERTER.convertForward(source);
            } else if (sourceType == BigDecimal.class) {
                return BIGDECIMAL_TO_STRING_CONVERTER.convertForward(source);
            }
        } else if (sourceType == String.class) {
            if (targetType == Byte.class) {
                return BYTE_TO_STRING_CONVERTER.convertReverse(source);
            } else if (targetType == Short.class || targetType == short.class ) {
                return SHORT_TO_STRING_CONVERTER.convertReverse(source);
            } else if (targetType == Integer.class || targetType == int.class) {
                return INT_TO_STRING_CONVERTER.convertReverse(source);
            } else if (targetType == Long.class || targetType == long.class) {
                return LONG_TO_STRING_CONVERTER.convertReverse(source);
            } else if (targetType == Float.class || targetType == float.class) {
                return FLOAT_TO_STRING_CONVERTER.convertReverse(source);
            } else if (targetType == Double.class || targetType == double.class) {
                return DOUBLE_TO_STRING_CONVERTER.convertReverse(source);
            } else if (targetType == Boolean.class || targetType == boolean.class) {
                return BOOLEAN_TO_STRING_CONVERTER.convertReverse(source);
            } else if (targetType == Character.class|| targetType == char.class) {
                return CHAR_TO_STRING_CONVERTER.convertReverse(source);
            } else if (targetType == BigInteger.class) {
                return BIGINTEGER_TO_STRING_CONVERTER.convertReverse(source);
            } else if (targetType == BigDecimal.class) {
                return BIGDECIMAL_TO_STRING_CONVERTER.convertReverse(source);
            }
        } else if (sourceType == Integer.class && targetType == Boolean.class) {
            return INT_TO_BOOLEAN_CONVERTER.convertForward(source);
        } else if (sourceType == Boolean.class && targetType == Integer.class) {
            return INT_TO_BOOLEAN_CONVERTER.convertReverse(source);
        }

        return source;
    }
}
