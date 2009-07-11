package cx.ath.jbzdak.jpaGui.autoComplete.adapter;

import cx.ath.jbzdak.jpaGui.autoComplete.AutoCompleteAdaptor;

/**
 * Nic nie robiący adaptor. 
 * @author jb
 *
 */
public class NoopAdaptor<T> extends AutoCompleteAdaptor<T>{

	private static final long serialVersionUID = 1L;

	@Override
	protected void onFilterChange() {
		
	}

}
