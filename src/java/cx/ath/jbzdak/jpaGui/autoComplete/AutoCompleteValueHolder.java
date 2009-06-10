package cx.ath.jbzdak.jpaGui.autoComplete;

/**
 * Wartość przechowywana w {@link AutocompleteComboBox}. 
 * @author jb
 *
 */
public class AutoCompleteValueHolder {
	
	private final String label; 
	
	private final Object value;

	private final boolean autoCreated;
	
	public AutoCompleteValueHolder(String label) {
		super();
		this.label = label;
		this.value = label;
		this.autoCreated = false;
	}

	public AutoCompleteValueHolder(String label, Object value) {
		super();
		this.label = label;
		this.value = value;
		this.autoCreated = false;
	}

	public AutoCompleteValueHolder(String label, Object value,
			boolean autoCreated) {
		super();
		this.label = label;
		this.value = value;
		this.autoCreated = autoCreated;
	}

	public String getLabel() {
		return label;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public String toString() {
		return label;
	}

	public boolean isAutoCreated() {
		return autoCreated;
	}
}
