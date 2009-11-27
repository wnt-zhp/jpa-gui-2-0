package cx.ath.jbzdak.jpaGui.ui.query;

import cx.ath.jbzdak.jpaGui.genericListeners.DoStuffDocumentListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FulltextJTtextField extends JTextField{

	private static final long serialVersionUID = 1L;

	private final FulltextFilter fulltextFilter;

	private final Timer timer;

	private DocumentListener documentListener = new DocumentListener();

    public FulltextJTtextField(FulltextFilter fulltextFilter) {
		this(fulltextFilter, 100);
	}

	public FulltextJTtextField(FulltextFilter fulltextFilter, int timeout) {
		super();
		this.fulltextFilter = fulltextFilter;
		this.timer = new Timer(timeout, new PushQuery());
		timer.setRepeats(false);
      fulltextFilter.addActionListener(new ActionListener(){
         @Override
         public void actionPerformed(ActionEvent e) {

         }
      });
      setColumns(15);
	}


	private class PushQuery implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			fulltextFilter.setQuery(getText());
		}
	}

	private  class DocumentListener extends DoStuffDocumentListener{
		@Override
		protected void doStuff(DocumentEvent e) {
			if(timer.isRunning()){
				timer.restart();
			}else{
            timer.start();
         }
		}
	}

	@Override
	public void setDocument(Document doc) {
		if(getDocument()!=null){
			this.getDocument().removeDocumentListener(getDocumentListener());
		}
		super.setDocument(doc);
		getDocument().addDocumentListener(getDocumentListener());
	}

	public void addQueryChangedListener(ActionListener actionListener){
		fulltextFilter.addActionListener(actionListener);
	}

	DocumentListener getDocumentListener() {
		if(documentListener==null){
			documentListener = new DocumentListener();
		}
		return documentListener;
	}
}
