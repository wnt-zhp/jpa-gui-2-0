package cx.ath.jbzdak.jpaGui.ui.form;

import java.awt.Component;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-24
 */
public class ReadOnlyFormElement<T extends Component> extends AbstractFormElement<T> {
   public ReadOnlyFormElement(T renderer, String name) {
      super(renderer, name);
   }

   public ReadOnlyFormElement(T renderer, String name, String shortDescription, String longDescription) {
      super(renderer, name, shortDescription, longDescription);
   }

   @Override
   public void startEditing() {

   }

   @Override
   public void startViewing() {
   }

   @Override
   public void rollback() {
   }

   @Override
   public void commit() {
   }

   @Override
   public void clear() {
   }
}
