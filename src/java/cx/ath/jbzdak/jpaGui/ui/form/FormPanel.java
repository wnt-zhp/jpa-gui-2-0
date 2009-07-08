package cx.ath.jbzdak.jpaGui.ui.form;

import javax.swing.*;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Zostaje bop nie chce mi się tego zmieniać na FormPanel. Generalnie po skończeniu żarłoka -- wywalić
 * @param <T>
 */
public class FormPanel<T extends JComponent> extends FormPanelMock<T, DAOFormElement<T, ?>> {

	private static final long serialVersionUID = 1L;

   public FormPanel(DAOFormElement<T,?> element) {
		this(element, FormPanelConstraints.createDefaultConstraints());
	}

	public FormPanel(DAOFormElement<T,?> element, Map<String, String> constraints) {
		super(element, constraints, null);
	}

    public FormPanel(DAOFormElement<T, ?> formElement, Map<String, String> constraints, ResourceBundle bundle) {
        super(formElement, constraints, bundle);
    }
}
