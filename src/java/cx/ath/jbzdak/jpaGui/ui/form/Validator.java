package cx.ath.jbzdak.jpaGui.ui.form;

import java.util.List;

public interface Validator<B>{
	@SuppressWarnings("unchecked")
	Object validate(List<? extends FormElement<?, ? extends B, ?>>elements);
}
