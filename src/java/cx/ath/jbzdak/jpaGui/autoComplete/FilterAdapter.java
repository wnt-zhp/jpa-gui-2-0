package cx.ath.jbzdak.jpaGui.autoComplete;

import cx.ath.jbzdak.jpaGui.Utils;
import cx.ath.jbzdak.jpaGui.ui.formatted.FormattingException;
import cx.ath.jbzdak.jpaGui.ui.formatted.MyFormatter;
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
public abstract class FilterAdapter<T> extends AutoCompleteAdaptor<AutoCompleteValueHolder>{

   private static final Logger LOGGER = Utils.makeLogger();

   private Collection<T> contents;

   private final ArrayList<AutoCompleteValueHolder> holders =
           new ArrayList<AutoCompleteValueHolder>();

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
      holders.clear();
      holders.ensureCapacity(contents.size());
      for(T t: contents){
         try {
            holders.add(new AutoCompleteValueHolder(formatter.formatValue(t), t));
         } catch (FormattingException e) {
            holders.add(new AutoCompleteValueHolder("formatting exception", t));
            LOGGER.debug("Handled", e);
         }
      }
      support.firePropertyChange("contents", oldContents, this.getContents());
      onFilterChange();
   }

   @Override
   protected void onFilterChange() {
      ArrayList<AutoCompleteValueHolder> filtered =
              new ArrayList<AutoCompleteValueHolder>(holders.size());
      for (int ii = 0; ii < holders.size(); ii++) {
         if(query.matches((T) holders.get(ii))){
            filtered.add(holders.get(ii));
         }
      }
      setCurentFilteredResults(filtered);
   }

   public Collection<T> getContents() {
      return contents;
   }
}

