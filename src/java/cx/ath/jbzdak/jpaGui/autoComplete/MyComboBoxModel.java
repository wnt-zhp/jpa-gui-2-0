package cx.ath.jbzdak.jpaGui.autoComplete;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import javax.swing.DefaultComboBoxModel;
import javax.swing.MutableComboBoxModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import java.util.*;

/**
 * Model comboboxa.
 *
 * Nie zmienia w losowych momentach {@link #selectedItem} (jak {@link DefaultComboBoxModel}
 * Ma też opcję {@link #detatchedSelectedItem} (ustawianą w konstruktorze - {@link #MyComboBoxModel(boolean)},
 * która zupełnie separuje wyrany element - może on nawet nie być w wartościach modelu, i tak dalej.
 * @author jb
 *
 */
public class MyComboBoxModel<V> implements MutableComboBoxModel {

	/**
	 * Zawartość modelu.
	 *
	 * Generalnie nie ruszać, z klas pochodnych -- bo zmiany w tej liśnie
	 * nie powodują wywołania odpowiednich eventów.
	 */
	@SuppressWarnings({"WeakerAccess"})
   protected List<Object> items = new ArrayList<Object>();

	/**
	 * Listenery
	 */
	@SuppressWarnings({"WeakerAccess"})
   protected final Set<ListDataListener> listeners = new HashSet<ListDataListener>();

	/**
	 * Wybrany element.
	 *  Generalnie nie ruszać, z klas pochodnych -- bo zmiany
	 * nie powodują wywołania odpowiednich eventów.
	 */
	@SuppressWarnings({"WeakerAccess"})
   protected Object selectedItem;

	/**
	 * Wskazuje czy {@link #selectedItem} jest kompletnie niezależna
	 * od reszty modelu.
	 */
	@SuppressWarnings({"WeakerAccess"})
   protected final boolean detatchedSelectedItem;

   @SuppressWarnings({"WeakerAccess"})
   protected Class<? extends V> elementClass;

	public MyComboBoxModel() {
		super();
		this.detatchedSelectedItem = false;
	}

	@SuppressWarnings({"SameParameterValue"})
   public MyComboBoxModel(boolean detatchedSelectedItem) {
		super();
		this.detatchedSelectedItem = detatchedSelectedItem;
	}

	public MyComboBoxModel(Collection<?> items,boolean detatchedSelectedItem) {
		super();
		this.detatchedSelectedItem = detatchedSelectedItem;
		this.items.addAll(items);
	}

	@Override
	public V getSelectedItem() {
		return (V) selectedItem;
	}

	@SuppressWarnings({"WeakerAccess"})
   public void setSelectedItemQuiet(@Nullable Object anItem){
      if(elementClass!= null && !elementClass.isInstance(anItem)){
         throw new IllegalArgumentException();
      }
		selectedItem = anItem;
	}

	@Override
	public void setSelectedItem(@Nullable Object anItem){
		if(
			(selectedItem != null && !selectedItem.equals(anItem)) ||
			(selectedItem == null && anItem !=null)
			){
				setSelectedItemQuiet(anItem);
				fireDataChanged(-1, -1);
		}
	}

	/**
	 * Całkowicie wymienia zawatrość modelu.
	 * Wywołuje dwa eventy - zmianę interwału
	 * 0 - min(newContents.size, items.size), oraz dodanie/usunięcie interwału
	 *  min(newContents.size, items.size) - max(newContents.size, items.size). <br/>
	 *  Metoda ta nie zmieni {@link #selectedItem}.
	 * @param newContents nowa zawartość modelu
	 */
	public void setContents(Collection<?> newContents){
		int oldSize = items.size();
		items.clear();
		items.addAll(newContents);
		if(oldSize < items.size()){
			fireIntervalAdded(oldSize, items.size() - 1);
		}
		if(oldSize > items.size()){
			fireIntervalDeleted(items.size(), oldSize - 1);
		}
		fireDataChanged(0, items.size());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public V getElementAt(int index) {
		return (V) items.get(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSize() {
		return items.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	/**
	 * Dodaje element. <br/>
	 * W przeciwieństwie {@link DefaultComboBoxModel}, nigdy nie zmieni
	 * {@link #selectedItem}.
	 */
	@Override
	public void addElement(Object obj) {
		items.add(obj);
		fireIntervalAdded(items.size()-1, items.size()-1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertElementAt(Object obj, int index) {
		items.add(index, obj);
		fireIntervalAdded(index, index);
	}

	private void removeAndCheck(int index, Object obj){
		items.remove(index);
		fireIntervalDeleted(index, index);
		if(!detatchedSelectedItem && selectedItem!=null && selectedItem.equals(obj)){
			setSelectedItem(null);
		}
	}

	/**
	 * Usuwa element. Jeśli ustawiono {@link #detatchedSelectedItem}, nie
	 * zmieni {@link #selectedItem} nawet jeśli ta jest usuwana wywołaniem
	 * tej metody z modelu.
	 */
	@Override
	public void removeElement(Object obj) {
		int index = items.lastIndexOf(obj);
		if(index > -1 && index < items.size()){
			removeAndCheck(index, obj);
		}
	}

	/**
	 * Usuwa element. Jeśli ustawiono {@link #detatchedSelectedItem}, nie
	 * zmieni {@link #selectedItem} nawet jeśli ta jest usuwana wywołaniem
	 * tej metody z modelu.
	 */
	@Override
	public void removeElementAt(int index) {
		if(index > -1 && index < items.size()){
			removeAndCheck(index, items.get(index));
		}
	}

	@SuppressWarnings({"WeakerAccess"})
   protected void fireIntervalDeleted(int leftBound, int rightBound){
		ListDataEvent dataEvent = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, leftBound, rightBound);
		for(ListDataListener ldl : listeners){
			ldl.intervalRemoved(dataEvent);
		}
	}

	@SuppressWarnings({"WeakerAccess"})
   protected void fireDataChanged(int leftBound, int rightBound){
		ListDataEvent dataEvent = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, leftBound, rightBound);
		for(ListDataListener ldl : listeners){
			ldl.contentsChanged(dataEvent);
		}
	}

	@SuppressWarnings({"WeakerAccess"})
   protected void fireIntervalAdded(int leftBound, int rightBound){
		ListDataEvent dataEvent = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, leftBound, rightBound);
		for(ListDataListener ldl : listeners){
			ldl.intervalAdded(dataEvent);
		}
	}

   public Class<? extends V> getElementClass() {
      return elementClass;
   }

   public void setElementClass(@NonNull Class<? extends V> elementClass) {
      if(this.elementClass!=null){//Idea jest taka że elementClass ustawiamy raz na zawsze, inaczej się będzie chrzanić checked collection
         throw new IllegalStateException();
      }
      if(this.elementClass != elementClass){
         for(Object ii : items){
            if(!elementClass.isInstance(ii)){
               throw new IllegalStateException();
            }
         }
         items = (List<Object>) Collections.checkedList((List<V>) items, (Class<V>) elementClass);
         this.elementClass = elementClass;
      }
   }

   public boolean contains(Object o) {return items.contains(o);}
}
