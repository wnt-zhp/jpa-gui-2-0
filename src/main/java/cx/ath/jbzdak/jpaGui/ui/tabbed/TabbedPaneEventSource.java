package cx.ath.jbzdak.jpaGui.ui.tabbed;

import javax.swing.JTabbedPane;

import java.awt.Component;

public interface TabbedPaneEventSource {

	public JTabbedPane getTabbedPane();

	public Component getComponent();

}
