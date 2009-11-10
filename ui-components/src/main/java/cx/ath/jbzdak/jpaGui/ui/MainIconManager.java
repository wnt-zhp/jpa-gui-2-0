package cx.ath.jbzdak.jpaGui.ui;

import cx.ath.jbzdak.common.iconManager.IconManager;
import cx.ath.jbzdak.common.iconManager.IconSize;
import cx.ath.jbzdak.common.iconManager.NoIconAction;

import javax.swing.*;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.CheckForNull;
import edu.umd.cs.findbugs.annotations.Nullable;

import java.util.Map;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-10-22
 */
public class MainIconManager {
   private static final IconManager ICON_MANAGER = new IconManager();

   public static final IconSize SMALL = new IconSize(10,10);

   public static final IconSize MEDIUM = new IconSize(17,17);

   static{
      ICON_MANAGER.setDefaultSize(MEDIUM);
      ICON_MANAGER.setDefaultCollecion("silk");
   }

   public static Icon getIcon(@NonNull String iconName, @NonNull String collectionName, @CheckForNull IconSize size) {
      return ICON_MANAGER.getIcon(iconName, collectionName, size);
   }

   public static void setAlias(@NonNull String alias, @NonNull String iconName) {
      ICON_MANAGER.setAlias(alias, iconName);
   }

   public static Map<String, String> getAliases() {
      return ICON_MANAGER.getAliases();
   }

   public static Icon getIcon(@NonNull String iconName) {
      return ICON_MANAGER.getIcon(iconName);
   }

   public static Icon getIcon(@NonNull String iconName, @NonNull String collectionName) {
      return ICON_MANAGER.getIcon(iconName, collectionName);
   }

   public static Icon getIcon(@NonNull String iconName, @NonNull IconSize size) {
      return ICON_MANAGER.getIcon(iconName, size);
   }

   public static void setDefaultSize(@Nullable IconSize defaultSize) {
      ICON_MANAGER.setDefaultSize(defaultSize);
   }

   public static void setDefaultCollecion(@NonNull String defaultCollecion) {
      ICON_MANAGER.setDefaultCollecion(defaultCollecion);
   }

   public static void setNoIconAction(@NonNull NoIconAction noIconAction) {
      ICON_MANAGER.setNoIconAction(noIconAction);
   }

   public static IconSize getDefaultSize() {
      return ICON_MANAGER.getDefaultSize();
   }

   public static String getDefaultCollecion() {
      return ICON_MANAGER.getDefaultCollecion();
   }

   public static NoIconAction getNoIconAction() {
      return ICON_MANAGER.getNoIconAction();
   }
}
