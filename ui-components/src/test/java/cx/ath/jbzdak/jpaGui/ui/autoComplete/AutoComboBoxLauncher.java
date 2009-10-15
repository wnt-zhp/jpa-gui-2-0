package cx.ath.jbzdak.jpaGui.ui.autoComplete;

import cx.ath.jbzdak.jpaGui.ui.autoComplete.adapter.MockAdaptor;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-10-14
 */
public class AutoComboBoxLauncher {
   	/**
	 * Do testów.
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame f = new JFrame();
		AutocompleteComboBox box = new AutocompleteComboBox(new MockAdaptor());
		f.add(box, BorderLayout.CENTER);
		f.pack();
		f.setVisible(true);
	}
}
