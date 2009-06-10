package cx.ath.jbzdak.jpaGui.ui.form;

import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Property;

import java.awt.Component;

public  abstract class PropertyFormElement<T extends Component, E> extends AbstractFormElement<T> implements DAOFormElement<T, E> {

	private E entity;

   @SuppressWarnings({"WeakerAccess"})
   protected final Property<E, Object> entityValueProperty;

	private boolean readNullValues = true;

   protected PropertyFormElement(T renderer, String labelText) {
		super(renderer, labelText);
		this.entityValueProperty = null;
	}

	protected PropertyFormElement(T renderer, String labelText, String entityPropertyPath) {
		super(renderer, labelText);
		this.entityValueProperty =  BeanProperty.create(entityPropertyPath);
	}

	public PropertyFormElement(T renderer, String labelText,
			Property<E, Object> entityValueProperty) {
		super(renderer, labelText);
		this.entityValueProperty = entityValueProperty;
	}



	@Override
	public void commit() {
		try {
			entityValueProperty.setValue(entity, getRendererValue());
		} catch (RuntimeException e) {
			throw new RuntimeException("property: " + entityValueProperty.toString() + "entity: " + entity,e);
		}
	}

	@Override
	public void startEditing() {
		Object value;
		try {
			value = entityValueProperty.getValue(getEntity());
		}catch (RuntimeException e) {
			throw new RuntimeException(entityValueProperty.toString() + ", entity=" +getEntity(), e);
		}
		if(value != null || readNullValues){
			setRendererValue(value);
		}
		setRendererEditable(isEditable());
		startEditingEntry(value);
		if(isError()){
			support.firePropertyChange("error", false, true);
		}
		if(getErrorMessage()!=null){
			support.firePropertyChange("errorMsg", null, getErrorMessage());
		}
	}

	@SuppressWarnings({"UnusedParameters", "UnusedParameters"})
   protected void startEditingEntry(Object value) { }

	@Override
	public void rollback() {
		setRendererEditable(false);
		stopEditingEntry();
	}

	@SuppressWarnings({"EmptyMethod", "WeakerAccess", "WeakerAccess"})
    protected  void stopEditingEntry() { }

	@Override
	public void startViewing() {
		setRendererEditable(false);
		setRendererValue(entityValueProperty.getValue(entity));
		startViewingEntry();
	}

	@SuppressWarnings({"EmptyMethod", "WeakerAccess", "WeakerAccess"})
   protected void startViewingEntry() { }

	protected abstract void setRendererValue(Object value);

	protected abstract Object getRendererValue();

	protected abstract void setRendererEditable(boolean editable);

	public E getEntity() {
		return entity;
	}

	public void setEntity(E entity) {
		E oldEntity = this.entity;
		this.entity = entity;
		firePropertyChange("entity", oldEntity, entity);
		entitySet(entity);
	}

	@SuppressWarnings({"EmptyMethod", "WeakerAccess", "UnusedParameters"})
    protected void entitySet(E entity2) {	}

   @SuppressWarnings({"EmptyMethod", "WeakerAccess", "WeakerAccess", "UnusedDeclaration"})
    protected void rendererSet(T renderer) { }

   @Override
	public Object getValue() {
		return getRendererValue();
	}

	@Override
	public void setValue(Object value) {
		this.setRendererValue(value);
	}


	public void setEditable(boolean editable) {
      super.setEditable(editable);
      setRendererEditable(editable);
	}

	public boolean isReadNullValues() {
		return readNullValues;
	}

	public void setReadNullValues(boolean readEntityNullValues) {
		this.readNullValues = readEntityNullValues;
	}


}
