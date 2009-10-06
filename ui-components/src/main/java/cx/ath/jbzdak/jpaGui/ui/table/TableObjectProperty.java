package cx.ath.jbzdak.jpaGui.ui.table;

import org.jdesktop.beansbinding.Property;
import org.jdesktop.beansbinding.PropertyStateListener;


/**
 * Zhakowana Object property, która dział dla tabelki. 
 * 
 * Takie property mają kolumny zawierające guziki z akcjami.
 * 
 * @author jb
 *
 * @param <S>
 */
public class TableObjectProperty<S> extends Property<S, S> {

		public TableObjectProperty() {
		}

		/**
		 * Throws {@code UnsupportedOperationException}; {@code ObjectProperty}
		 * is never writeable.
		 * 
		 * @param source
		 *            {@inheritDoc}
		 * @return never returns; always throws {@code
		 *         UnsupportedOperationException}; {@code ObjectProperty} is
		 *         never writeable
		 * @throws UnsupportedOperationException
		 *             always; {@code ObjectProperty} is never writeable
		 * @see #isWriteable
		 */
		@Override
      @SuppressWarnings("unchecked")
		public Class<? extends S> getWriteType(S source) {
			return (Class<? extends S>) source.getClass();
		}

		/**
		 * Returns the source object passed to the method.
		 * 
		 * @return the value of the {@code source} argument
		 * @see #isReadable
		 */
		@Override
      public S getValue(S source) {
			return source;
		}

		@Override
      public void setValue(S source, S value) {
		}

		/**
		 * Returns {@code true}; {@code ObjectProperty} is always readable.
		 * 
		 * @return {@code true}; {@code ObjectPropert} is always readable
		 * @see #isWriteable
		 */
		@Override
      public boolean isReadable(Object source) {
			return true;
		}

		/**
		 * Returns {@code false}; {@code ObjectProperty} is never writeable.
		 * 
		 * @return {@code false}; {@code ObjectPropert} is never writeable
		 * @see #isReadable
		 */
		@Override
      public boolean isWriteable(Object source) {
			return true;
		}

		/**
		 * Returns a string representation of the {@code ObjectProperty}. This
		 * method is intended to be used for debugging purposes only, and the
		 * content and format of the returned string may vary between
		 * implementations. The returned string may be empty but may not be
		 * {@code null}.
		 * 
		 * @return a string representation of this {@code ObjectProperty}
		 */
		@Override
      public String toString() {
			return getClass().getName();
		}

		/**
		 * Does nothing; the state of an {@code ObjectProperty} never changes so
		 * listeners aren't useful.
		 */
		@Override
      public void addPropertyStateListener(S source,
				PropertyStateListener listener) {
		}

		/**
		 * Does nothing; the state of an {@code ObjectProperty} never changes so
		 * listeners aren't useful.
		 * 
		 * @see #addPropertyStateListener
		 */
		@Override
      public void removePropertyStateListener(S source,
				PropertyStateListener listener) {
		}

		/**
		 * Returns an empty array; the state of an {@code ObjectProperty} never
		 * changes so listeners aren't useful.
		 * 
		 * @return an empty array
		 * @see #addPropertyStateListener
		 */
		@Override
      public PropertyStateListener[] getPropertyStateListeners(S source) {
			return new PropertyStateListener[0];
		}

	}