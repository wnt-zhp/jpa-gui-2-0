package cx.ath.jbzdak.jpaGui.ui.query;

import cx.ath.jbzdak.jpaGui.ui.form.DefaultFormElement;
import cx.ath.jbzdak.jpaGui.ui.form.FormPanel;
import cx.ath.jbzdak.jpaGui.ui.form.FormPanelVisibility;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.EnumSet;
import java.util.ResourceBundle;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-11-17
 */
public class FullTextPanel extends JPanel{



   public FullTextPanel(FulltextFilter fulltextFilter, ResourceBundle resourceBundle) {
     this(fulltextFilter, 100, "cx.ath.jbzdak.jpaGui.ui.query.FullTextPanel.query", resourceBundle);
  }


    public FullTextPanel(FulltextFilter fulltextFilter, String labelName, ResourceBundle resourceBundle) {
		this(fulltextFilter, 100, labelName, resourceBundle);
	}

	public FullTextPanel(FulltextFilter fulltextFilter, int timeout, String labelName, ResourceBundle resourceBundle) {
		super(new MigLayout("fillx", "[][]:push"));
	   FulltextJTtextField field = new FulltextJTtextField(fulltextFilter, timeout);
      DefaultFormElement fieldElement = new DefaultFormElement(field, labelName, resourceBundle);
      FormPanel panel = new FormPanel(fieldElement, null, EnumSet.of(FormPanelVisibility.HIDE_ERROR_ICON));
      add(panel);
      JCheckBox jCheckBox = new JCheckBox();
      DefaultFormElement checkBoxElement = new DefaultFormElement(jCheckBox, "cx.ath.jbzdak.jpaGui.ui.query.FullTextPanel.fuzzy", resourceBundle);
      FormPanel checkboxPanel = new FormPanel(checkBoxElement, null, EnumSet.of(FormPanelVisibility.HIDE_ERROR_ICON));
      add(checkboxPanel);
	}
}
