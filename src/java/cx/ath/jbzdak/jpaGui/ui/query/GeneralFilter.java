package cx.ath.jbzdak.jpaGui.ui.query;

import javax.swing.RowFilter;

public abstract class GeneralFilter<M,I> extends RowFilter<M, I> {

   private final int[] columns;

   private boolean enabled = true; 

   public GeneralFilter(int... columns) {
      this.columns = columns;
   }

   public boolean include(Entry<? extends M,? extends I> value){
      if(!enabled){
         return true;
      }
      final int count = value.getValueCount();
      if (columns.length > 0) {
         for (int column : columns) {
            if (column < count) {
               if (include(value, column)) {
                  return true;
               }
            }
         }
      } else {
         for(int ii = 0; ii < count; ii++)
            if (include(value, count)) {
               return true;
            }
      }
      return false;
   }

   public boolean isEnabled() {
      return enabled;
   }

   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   protected abstract boolean include(Entry<? extends M,? extends I> value, int index);


}
