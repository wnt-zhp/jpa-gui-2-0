package cx.ath.jbzdak.jpaGui.ui.form;

import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Property;

import java.awt.*;

public abstract class PropertyFormElement<T extends Component, E,V> extends AbstractFormElement<T, E, V> {

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
			entityValueProperty.setValue(getEntity(), getValue());
		} catch (RuntimeException e) {
			throw new RuntimeException("property: " + entityValueProperty.toString() + "entity: " + getEntity(),e);
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
			setValue((V) value);
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
		setValue((V) entityValueProperty.getValue(getEntity()));
		startViewingEntry();
	}

	@SuppressWarnings({"EmptyMethod", "WeakerAccess", "WeakerAccess"})
   protected void startViewingEntry() { }

	protected abstract void setRendererEditable(boolean editable);

   private E getEntity() {
		return getBeanHolder().getBean()!=null?getBeanHolder().getBean():null;
	}

   @SuppressWarnings({"EmptyMethod", "WeakerAccess", "UnusedParameters"})
    protected void entitySet(E entity2) {	}

   @SuppressWarnings({"EmptyMethod", "WeakerAccess", "WeakerAccess", "UnusedDeclaration"})
    protected void rendererSet(T renderer) { }

	@Override
   public void setEditable(boolean editable) {
      super.setEditable(editable);
      setRendererEditable(editable);
	}

   public boolean isReadNullValues() {
		return readNullValues;
	}

	@Override
   public void setReadNullValues(boolean readEntityNullValues) {
		this.readNullValues = readEntityNullValues;
	}


}
