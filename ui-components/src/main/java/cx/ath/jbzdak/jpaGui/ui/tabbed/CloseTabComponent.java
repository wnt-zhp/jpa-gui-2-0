package cx.ath.jbzdak.jpaGui.ui.tabbed;

import cx.ath.jbzdak.common.famfamicons.IconManager;
import cx.ath.jbzdak.jpaGui.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public class CloseTabComponent extends JPanel{

	private static final long serialVersionUID = 1L;

	protected final JTabbedPane tabbedPane;

	protected final Component component;

	private boolean closeable;

	private final JLabel title;

	private final JButton closeButton;

	public CloseTabComponent(JTabbedPane _tabbedPane, Component _component, boolean closeable) {
		super(new FlowLayout(FlowLayout.LEFT, 2,0));
		this.tabbedPane = _tabbedPane;
		this.component = _component;
		setOpaque(false);
		this.title = new MyLabel();
		this.title.setOpaque(false);
		this.title.setBorder(BorderFactory.createEmptyBorder(2,0,0,0));
		add(this.title);
		closeButton = Utils.createIconButton(IconManager.getIconSafe("cross"));
		closeButton.setOpaque(false);
		closeButton.setBorder(BorderFactory.createEmptyBorder(2,0,0,0));
		closeButton.addActionListener(new CloseTabActionListener());
		closeButton.setEnabled(closeable);
		add(closeButton);
	}

	public class CloseTabActionListener implements ActionListener, Serializable{

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			tabbedPane.remove(component);
		}

	}

	protected boolean isCloseable() {
		return closeable;
	}

	protected void setCloseable(boolean closeable) {
		this.closeable = closeable;
	}

	private class MyLabel extends JLabel{
		private static final long serialVersionUID = 1L;
		@Override
		public Icon getIcon() {
			return tabbedPane.getIconAt(tabbedPane.indexOfComponent(component));
		}
		@Override
		public String getText() {
			return tabbedPane.getTitleAt(tabbedPane.indexOfComponent(component));
		}
	}
}
