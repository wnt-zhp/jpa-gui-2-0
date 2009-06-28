package cx.ath.jbzdak.jpaGui.ui.query;

import javax.swing.*;

public abstract  class GeneralFilter extends RowFilter<Object,Object> {
   private int[] columns;

   GeneralFilter(int[] columns) {
      this.columns = columns;
   }


   public boolean include(Entry<? extends Object,? extends Object> value){
      int count = value.getValueCount();
      if (columns.length > 0) {
         for (int i = columns.length - 1; i >= 0; i--) {
            int index = columns[i];
            if (index < count) {
               if (include(value, index)) {
                  return true;
               }
            }
         }
      }
      else {
         while (--count >= 0) {
            if (include(value, count)) {
               return true;
            }
         }
      }
      return false;
   }

   protected abstract boolean include(
           Entry<? extends Object,? extends Object> value, int index);
}
