package cx.ath.jbzdak.jpaGui.ui.form;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class WrappedPanel<T extends JComponent> extends JPanel {

	private static final long serialVersionUID = 1L;

	JLabel nameLabel;

	T renderer;

	String labelText;

	public WrappedPanel(T renderer, String labelText) {
		super();
		this.renderer = renderer;
		this.labelText = labelText;
		initialize();
	}

	private void initialize() {
		setLayout(new MigLayout("fillx", "[min!|fill]"));
		add(getNameLabel());
		add(renderer);
	}

	public JLabel getNameLabel() {
		if (nameLabel == null) {
			nameLabel = new JLabel();
			nameLabel.setText(labelText);
		}
		return nameLabel;
	}

	public T getRenderer() {
		return renderer;
	}

}
