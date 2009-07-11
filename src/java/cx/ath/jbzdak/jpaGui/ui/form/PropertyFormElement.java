package cx.ath.jbzdak.jpaGui.ui.form;

import cx.ath.jbzdak.jpaGui.BeanHolder;
import cx.ath.jbzdak.jpaGui.BeanHolderAware;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Property;

import java.awt.Component;

public abstract class PropertyFormElement<T extends Component, B, V, BH extends BeanHolder<? extends B>>
        extends AbstractFormElement<T, B, V> implements BeanHolderAware<B, BH>{

   @SuppressWarnings({"WeakerAccess"})
   protected final Property<B, Object> beanValueProperty;

	private boolean readNullValues = true;

   private BH beanHolder;

   protected PropertyFormElement(T renderer, String labelText) {
		super(renderer, labelText);
		this.beanValueProperty = null;
	}

	protected PropertyFormElement(T renderer, String labelText, String entityPropertyPath) {
		super(renderer, labelText);
		this.beanValueProperty =  BeanProperty.create(entityPropertyPath);
	}

	protected PropertyFormElement(T renderer, String labelText,
			Property<B, Object> beanValueProperty) {
		super(renderer, labelText);
		this.beanValueProperty = beanValueProperty;
	}

	@Override
	public void commit() {
		try {
			beanValueProperty.setValue(getBean(), getValue());
		} catch (RuntimeException e) {
			throw new RuntimeException("property: " + beanValueProperty.toString() + "entity: " + getBean(),e);
		}
	}

	@Override
	public void startEditing() {
		Object value;
		try {
			value = beanValueProperty.getValue(getBean());
		}catch (RuntimeException e) {
			throw new RuntimeException(beanValueProperty.toString() + ", entity=" + getBean(), e);
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

	@SuppressWarnings({"UnusedParameters", "EmptyMethod", "WeakerAccess"})
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
		setValue((V) beanValueProperty.getValue(getBean()));
		startViewingEntry();
	}

	@SuppressWarnings({"EmptyMethod", "WeakerAccess", "WeakerAccess"})
   protected void startViewingEntry() { }

	protected abstract void setRendererEditable(boolean editable);

   private B getBean() {
		return getBeanHolder().getBean()!=null?getBeanHolder().getBean():null;
	}

   @SuppressWarnings({"EmptyMethod", "WeakerAccess", "UnusedParameters"})
    protected void entitySet(B entity2) {	}

   @SuppressWarnings({"EmptyMethod", "WeakerAccess", "WeakerAccess", "UnusedDeclaration"})
    protected void rendererSet(T renderer) { }

	@Override
   public void setEditable(boolean editable) {
      super.setEditable(editable);
      setRendererEditable(editable);
	}

   @SuppressWarnings({"WeakerAccess"})
   public boolean isReadNullValues() {
		return readNullValues;
	}

	@Override
   public void setReadNullValues(boolean readEntityNullValues) {
		this.readNullValues = readEntityNullValues;
	}

   @SuppressWarnings({"WeakerAccess"})
   protected BH getBeanHolder() {
      return beanHolder;
   }

   @Override
   public void setBeanHolder(BH beanHolder) {
      this.beanHolder = beanHolder;
   }
}
