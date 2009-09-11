package cx.ath.jbzdak.jpaGui.app;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-22
 */
public interface PersistableConfigEntry {
   String getValue();

   String getEntryName();

   String getShortDescription();

   String getLongDesctiption();

   boolean isSingle();

   String getValidatorClass();

   String getValidatorParams();

   String getValueClassName();
}
