package cx.ath.jbzdak.jpaGui.autoComplete.adapter;

import cx.ath.jbzdak.jpaGui.autoComplete.AutoCompleteAdaptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Testowya adapter
 * @author jb
 *
 */
public class MockAdaptor extends AutoCompleteAdaptor<String> {

	private static final long serialVersionUID = 1L;

	public static final List<String> unfiltered =
		Arrays.asList(
				new String("AAA"),
				new String("AAB"),
				new String("ABA"),
				new String("CBA"),
				new String("CAB")
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
