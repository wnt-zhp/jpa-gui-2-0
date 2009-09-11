package cx.ath.jbzdak.jpaGui.beansbinding;

import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.observablecollections.ObservableListListener;

import java.util.*;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-06-04
 */
@SuppressWarnings({"ALL"})
public class UnmodifiableObserwableList<E>  implements ObservableList<E> {

   private final ObservableList<E> internal;

   public UnmodifiableObserwableList(ObservableList<E> internal) {
      this.internal = internal;
   }

   public void addObservableListListener(ObservableListListener listener) {
      internal.addObservableListListener(listener);
   }

   public void removeObservableListListener(ObservableListListener listener) {
      internal.removeObservableListListener(listener);
   }

   public boolean supportsElementPropertyChanged() {return internal.supportsElementPropertyChanged();}

   public int size() {return internal.size();}

   public boolean contains(Object o) {return internal.contains(o);}

   public boolean isEmpty() {return internal.isEmpty();}

   public Object[] toArray() {return internal.toArray();}

   public <E> E[] toArray(E[] a) {return internal.toArray(a);}

   public boolean containsAll(Collection<?> c) {return internal.containsAll(c);}

   public boolean equals(Object o) {return internal.equals(o);}

   public int hashCode() {return internal.hashCode();}

   public E get(int index) {return internal.get(index);}

   public int indexOf(Object o) {return internal.indexOf(o);}

   public int lastIndexOf(Object o) {return internal.lastIndexOf(o);}

   public ListIterator<E> listIterator() {
      return Collections.unmodifiableList(internal).listIterator();
   }

   public ListIterator<E> listIterator(int index) {
      return Collections.unmodifiableList(internal).listIterator(index);
   }

   public List<E> subList(int fromIndex, int toIndex) {
      return Collections.unmodifiableList(internal).subList(fromIndex, toIndex);
   }

   @Override
   public Iterator<E> iterator() {
      return Collections.unmodifiableList(internal).iterator();
   }


   public boolean add(E e) {
      throw new UnsupportedOperationException("This list is unmodifiable");
   }

   public boolean remove(Object o) {
       throw new UnsupportedOperationException("This list is unmodifiable");
   }

   public boolean addAll(Collection<? extends E> c) {
       throw new UnsupportedOperationException("This list is unmodifiable");
   }

   public boolean addAll(int index, Collection<? extends E> c) {
       throw new UnsupportedOperationException("This list is unmodifiable");
   }

   public boolean removeAll(Collection<?> c) {
       throw new UnsupportedOperationException("This list is unmodifiable");
   }

   public boolean retainAll(Collection<?> c) {
       throw new UnsupportedOperationException("This list is unmodifiable");
   }

   public void clear() {
       throw new UnsupportedOperationException("This list is unmodifiable");
   }

   public E remove(int index) {
       throw new UnsupportedOperationException("This list is unmodifiable");
   }

   public E set(int index, E element) {
       throw new UnsupportedOperationException("This list is unmodifiable");
   }

   @Override
   public void add(int index, E element) {
      throw new UnsupportedOperationException("This list is unmodifiable");
   }
}
