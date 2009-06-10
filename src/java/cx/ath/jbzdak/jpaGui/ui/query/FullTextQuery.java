package cx.ath.jbzdak.jpaGui.ui.query;

import org.apache.commons.lang.StringUtils;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.beans.PropertyChangeSupport;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 * Date: 2009-04-15
 */
public class FullTextQuery extends AbstractQuery<String, String>{

    private static final int DEFAULT_LEVENSTEIN_DST = 2;

    private boolean fuzzy;

    private String[] words = new String[0];

    private int levensteinDST  = DEFAULT_LEVENSTEIN_DST;

   public FullTextQuery(PropertyChangeSupport support) {
      super(support);
   }

   public FullTextQuery() {
   }

   public void setFuzzy(boolean fuzzy) {
        boolean oldFuzzy = this.fuzzy;
        this.fuzzy = fuzzy;
        support.firePropertyChange("fuzzy", oldFuzzy, this.fuzzy);
        fireEvent(queryChangedEvt);
    }

    public void setLevensteinDST(int levensteinDST) {
        int oldLevensteinDST = this.levensteinDST;
        this.levensteinDST = levensteinDST;
        support.firePropertyChange("levensteinDST", oldLevensteinDST, this.levensteinDST);
        if(this.levensteinDST!=oldLevensteinDST && fuzzy){
          fireEvent(queryChangedEvt);
        }
    }

   @Override
   protected void queryChangedEntry() {
      this.words = query!=null?query.split("\\s+"):new String[0];
      for(int ii = 0; ii < words.length; ii++){
         words[ii] = words[ii].toLowerCase();
      }
   }

    public boolean matches(String str){
        if(words!=null && words.length==0){
			return true;
		}
        if(fuzzy){
            return matchesFuzzy(str);
        }else{
            return  matchesNotFuzzy(str);
        }
    }

    boolean matchesFuzzy(String str){
    	String[] targetWords = str.split("\\s+");
		boolean found;
		for(String word : words){
			found = false;
			for(String target : targetWords){
				if(!isBlank(target) && StringUtils.getLevenshteinDistance(word, target) <= levensteinDST){
					found = true;
					break;
				}
			}
			if(!found){
				return false;
			}
		}
		return true;
    }

    boolean matchesNotFuzzy(String str){
    	String v = str.toLowerCase();
        for(String word : words){
            if(!v.contains(word)){
                return false;
            }
        }
        return true;
    }

    public boolean isFuzzy() {
        return fuzzy;
    }

    public String[] getWords() {
        return words;
    }

    public String getQuery() {
        return query;
    }

    public int getLevensteinDST() {
        return levensteinDST;
    }
}
