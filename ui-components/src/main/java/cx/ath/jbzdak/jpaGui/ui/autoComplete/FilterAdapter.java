package cx.ath.jbzdak.jpaGui.ui.autoComplete;

import cx.ath.jbzdak.jpaGui.MyFormatter;
import cx.ath.jbzdak.jpaGui.Utils;
import cx.ath.jbzdak.jpaGui.ui.formatted.formatters.ToStringFormatter;
import cx.ath.jbzdak.jpaGui.ui.query.Query;
import org.slf4j.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-20
 */
public abstract class FilterAdapter<T> extends AutoCompleteAdaptor<T>{

   private static final Logger LOGGER = Utils.makeLogger();

   private Collection<T> contents;

   private final Query<String, T> query;

   protected final MyFormatter formatter = new ToStringFormatter();

   protected FilterAdapter(Query<String, T> query) {
      this.query = query;
      this.query.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            if(Query.QUERY_CHANGED_COMMAND.equals(e.getActionCommand())){
               onFilterChange();
            }
         }
      });
   }

   @Override
   public void setFilter(String filter) {
      this.query.setQuery(filter);
   }

   public void setContents(Collection<T> contents) {
      Collection<T> oldContents = this.getContents();
      this.contents = contents;
      support.firePropertyChange("contents", oldContents, this.getContents());
      onFilterChange();
   }

   @Override
   protected void onFilterChange() {
      ArrayList<T> filtered = new ArrayList<T>
                      (contents.size());
      for (T item : contents) {
         if(query.matches(item)){
            filtered.add(item);
         }
      }
      setCurentFilteredResults(filtered);
   }

   @SuppressWarnings({"WeakerAccess"})
   public Collection<T> getContents() {
      return contents;
   }
}

