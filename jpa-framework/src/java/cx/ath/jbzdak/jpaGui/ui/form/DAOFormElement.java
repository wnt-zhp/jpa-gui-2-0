package cx.ath.jbzdak.jpaGui.ui.form;

import java.awt.Component;

public interface DAOFormElement<T extends Component, E> extends FormElement<T> {

   /**
	 * Zwraca edyowaną encję
	 * @return
	 */
	public E getEntity();

	/**
	 * Ustawia edytowaną encję
	 * @param entity
	 */
	public void setEntity(E entity);

	/**
	 * Ustawia wartość
	 * @param value
	 */
	public void setValue(Object value);

	/**
	 * Zwraca wartość
	 * @return
	 */
	public Object getValue();

   public boolean isReadNullValues();

	public void setReadNullValues(boolean readNullValues);


}
