package cx.ath.jbzdak.jpaGui.ui.autoComplete.adapter;

import cx.ath.jbzdak.jpaGui.ui.autoComplete.AutoCompleteAdaptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Testowya adapter
 * @author jb
 *
 */
@SuppressWarnings({"ALL"})
public class MockAdaptor extends AutoCompleteAdaptor<String> {

	private static final long serialVersionUID = 1L;

	public static final List<String> unfiltered =
		Arrays.asList(
              "AAA",
              "AAB",
              "ABA",
              "CBA",
              "CAB"
			);

	public MockAdaptor(){
		setCurentFilteredResults(createList(""));
	}

	private List<String> createList(String filter){
		List<String> filtered = new  ArrayList<String>();
		for(String s : unfiltered){
			if(s.startsWith(filter)){
				filtered.add(s);
			}
		}
		return filtered;
	}

	@Override
	protected void onFilterChange() {
		setCurentFilteredResults(createList(getFilter()));

	}
}
