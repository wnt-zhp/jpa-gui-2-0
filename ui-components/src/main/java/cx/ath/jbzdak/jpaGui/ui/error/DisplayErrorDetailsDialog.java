package cx.ath.jbzdak.jpaGui.ui.error;

import cx.ath.jbzdak.common.famfamicons.IconManager;
import cx.ath.jbzdak.jpaGui.ClassHandler;
import cx.ath.jbzdak.jpaGui.Utils;
import static cx.ath.jbzdak.jpaGui.Utils.initLocation;
import cx.ath.jbzdak.jpaGui.ui.error.ErrorHandlers.Formatter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayErrorDetailsDialog extends JDialog{

	private static final long serialVersionUID = 1L;

   private  static final ClassHandler<Formatter> errorhandlers = ErrorHandlers.createLongHandlers();

   public static void showErrorDialog(Object message, Frame owner){
      DisplayErrorDetailsDialog dialog = new DisplayErrorDetailsDialog(owner, true);
      dialog.setText(errorhandlers.getHandler(message).getMessage(message));
      Utils.initLocation(dialog);
      dialog.setVisible(true);
   }

	private final JButton closeButton = new JButton("Zamknij", IconManager.getIconSafe("cancel"));
	{
		closeButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	}

	private final JTextArea textArea = new JTextArea();
	{
		textArea.setEditable(false);
      textArea.setLineWrap(true);
	}

	public DisplayErrorDetailsDialog() {
		super();
		initialize();
	}

	public DisplayErrorDetailsDialog(Dialog owner) {
		super(owner);
		initialize();
	}

	@SuppressWarnings({"SameParameterValue"})
   public DisplayErrorDetailsDialog(Frame owner, boolean modal) {
		super(owner, modal);
		initialize();
	}

	public DisplayErrorDetailsDialog(Frame owner) {
		super(owner);
		initialize();
	}

	private void initialize() {
		setLayout(new MigLayout("fill", "[fill]"));
		add(new JScrollPane(textArea));
		add(closeButton, "tag finished, dock south");
		setTitle("Sczegóły błędu");
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		//DisplayMode  displayMode = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
		//setSize(max(displayMode.getWidth()/4, 640), max(displayMode.getHeight()/4, 480));
		initLocation(this);
	}

	@Override
	public void setVisible(boolean b) {
		if(b && !isVisible()){
			closeButton.requestFocus();
			GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
         DisplayMode displayMode = graphicsEnvironment.getDefaultScreenDevice().getDisplayMode();
         setSize((int)(displayMode.getWidth()* 0.6), (int)(displayMode.getHeight()* 0.6));
		}
		super.setVisible(b);
	}

	public String getText() {
		return textArea.getText();
	}

	public void setText(String t) {
		textArea.setText(t);
		if(isVisible()){
			validate();
		}
	}
}
