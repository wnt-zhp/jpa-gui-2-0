package cx.ath.jbzdak.jpaGui.autoComplete;

import edu.umd.cs.findbugs.annotations.OverrideMustInvoke;
import org.apache.commons.lang.StringUtils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Enkapsuluje zamianę tego co wpisuje user na zbiór podpowiedzi.
 *
 * Klasa jest asynchroniczna, tj. pomiędzy podaniem filtra a zwróceniem odpowiedzi może wystąpić
 * przerwa czasowa, a samo  np. przeszukiwanie powinno się odbywać w innym wątku.
 *
 * Przy zmianie filtra wywolywana jest metoda {@link #onFilterChange()}, która powinna zainicjalizować
 * przeszukiwanie (i się skoczyć). Po zakończeniu prac w tle
 * następnie powinno się wywołać {@link #setCurentFilteredResults(List)} z wynikami.
 *
 * @see SwingWorkerAdaptor
 * @see DbAdaptor
 *
 * @author jb
 *
 */
public abstract class AutoCompleteAdaptor<T> implements Serializable{


	private static final long serialVersionUID = 1L;

	private final Class<T> clazz;

	/**
	 * Filtr zamieniany na podpowiedzi
	 */
	private String filter;

	/**
	 * Podpowiedzi
	 */
	private List<T> curentFilteredResults = Collections.emptyList();

	protected PropertyChangeSupport support = new PropertyChangeSupport(this);

	public AutoCompleteAdaptor() {
		this(null);
	}

	public AutoCompleteAdaptor(Class<T> clazz) {
		super();
		this.clazz = clazz;
	}


	/**
	 * Ustawia filtr przeszukiwania i inicjuje wyszukiwanie podpowiedzi
	 * (w tle).
	 * @param filter
	 */
	@OverrideMustInvoke
	public void setFilter(String filter) {
		if(!ignoreFilter(this.filter, filter)){
			this.filter = filter;
			onFilterChange();
		}
	}

	public boolean ignoreFilter(String oldFilter, String newFilter){
		return StringUtils.equals(oldFilter, newFilter);
	}

	/**
	 * Powinna zainicjować przeszukiwanie podpowiedzi i się kończyć.
	 * Podczasa wykonywania tej metody dostępna jest już nowa wartość filtra.
	 */
	protected abstract void onFilterChange();

	public List<T> getCurentFilteredResults() {
		return curentFilteredResults;
	}

	/**
	 * Wywołanie tej metody powoduje odświerzenie zawartości modelu {@link AutocompleteComboBox}.
	 * @param curentFilteredResults
	 */
	protected void setCurentFilteredResults(List<T> curentFilteredResults) {
		List<T> lastResults = this.curentFilteredResults;
		this.curentFilteredResults = curentFilteredResults;
		support.firePropertyChange("curentFilteredResults", lastResults, this.curentFilteredResults);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		support.addPropertyChangeListener(propertyName, listener);
	}

	public PropertyChangeListener[] getPropertyChangeListeners() {
		return support.getPropertyChangeListeners();
	}

	public PropertyChangeListener[] getPropertyChangeListeners(
			String propertyName) {
		return support.getPropertyChangeListeners(propertyName);
	}

	public boolean hasListeners(String propertyName) {
		return support.hasListeners(propertyName);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		support.removePropertyChangeListener(propertyName,
				listener);
	}

	public String getFilter() {
		return filter;
	}

	/**
	 * Zwraca wartość utworzoną z filtra. W niektórych wypadkach można sensownie sparsować
	 * filtr do wartości. Jeśli nie da się utworzyć nic powinno się zwrócić null
	 * @return
	 */
	public abstract T getValueHolderFromFilter();


	public AutoCompleteValueHolder getValueHolderProperty(Object property){
		if(clazz!=null && clazz.isInstance(property)){
			throw new IllegalArgumentException();
		}
		if(property==null){
			return new AutoCompleteValueHolder("", null, true);
		}
		return new AutoCompleteValueHolder(property.toString(), property, true);
	}



}
