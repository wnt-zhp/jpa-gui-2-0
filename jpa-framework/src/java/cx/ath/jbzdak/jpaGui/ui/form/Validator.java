package cx.ath.jbzdak.jpaGui.ui.form;

import java.util.List;

public interface Validator{
	@SuppressWarnings("unchecked")
	Object validate(Object entity, List<? extends FormElement>elements);
}
