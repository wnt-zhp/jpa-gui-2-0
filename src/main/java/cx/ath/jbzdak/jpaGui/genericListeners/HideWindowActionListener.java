package cx.ath.jbzdak.jpaGui.genericListeners;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HideWindowActionListener implements ActionListener {

	private final Window window;

	public HideWindowActionListener(Window window) {
		super();
		this.window = window;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		window.setVisible(false);
	}
}
