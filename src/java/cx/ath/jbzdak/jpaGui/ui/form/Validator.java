package cx.ath.jbzdak.jpaGui.ui.form;

import java.util.List;

public interface Validator<B>{
	@SuppressWarnings("unchecked")
	Object validate(B entity, List<? extends FormElement<?, ? extends B, ?>>elements);
}
