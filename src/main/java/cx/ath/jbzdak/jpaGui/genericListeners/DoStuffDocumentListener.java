package cx.ath.jbzdak.jpaGui.genericListeners;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public abstract class DoStuffDocumentListener implements DocumentListener {

	@Override
	public void changedUpdate(DocumentEvent e) {
		doStuff(e);
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		doStuff(e);		
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		doStuff(e);		
	}

	protected abstract void doStuff(DocumentEvent e);
}
