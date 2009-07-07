package cx.ath.jbzdak.jpaGui.ui.form;

import java.awt.*;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-24
 */
public class ReadOnlyFormElement<T extends Component, B, V> extends AbstractFormElement<T, B, V> {
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

   }@Override

    public void setReadNullValues(boolean readNullValues) {

   }

   @Override
   public V getValue() {
      return null;
   }

   @Override
   public void setValue(V v) {
     
   }
}
