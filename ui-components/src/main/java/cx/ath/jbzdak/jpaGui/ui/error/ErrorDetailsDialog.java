package cx.ath.jbzdak.jpaGui.ui.error;

import cx.ath.jbzdak.jpaGui.ClassHandler;
import cx.ath.jbzdak.jpaGui.Utils;
import static cx.ath.jbzdak.jpaGui.Utils.initLocation;
import cx.ath.jbzdak.jpaGui.ui.error.ErrorHandlers.Formatter;
import cx.ath.jbzdak.jpaGui.ui.MainIconManager;
import net.miginfocom.swing.MigLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ErrorDetailsDialog extends JDialog{

   private static final Logger LOGGER = LoggerFactory.getLogger(ErrorDetailsDialog.class);

	private static final long serialVersionUID = 1L;

   /**
    * Default size of error dialog in units of screen width.
    */
   public static final double DEFAULT_SIZE_WIDTH = 0.6, DEFAULT_SIZE_HEIGHT = 0.6;

   private  static final ClassHandler<Formatter> errorhandlers = ErrorHandlers.createLongHandlers();

   public static void showErrorDialog(Object message, Frame owner){
      if (message instanceof Exception) {
         Exception exception = (Exception) message;
         LOGGER.warn("Caught exception", exception);
      }
      ErrorDetailsDialog dialog = new ErrorDetailsDialog(owner, true);
      dialog.setText(errorhandlers.getHandler(message).getMessage(message));
      Utils.initLocation(dialog);
      dialog.setVisible(true);
   }

	private final JButton closeButton = new JButton("Zamknij", MainIconManager.getIcon("cancel", MainIconManager.MEDIUM));
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

	public ErrorDetailsDialog() {
		super();
		initialize();
	}

	public ErrorDetailsDialog(Dialog owner) {
		super(owner);
		initialize();
	}

	@SuppressWarnings({"SameParameterValue"})
   public ErrorDetailsDialog(Frame owner, boolean modal) {
		super(owner, modal);
		initialize();
	}

	public ErrorDetailsDialog(Frame owner) {
		super(owner);
		initialize();
	}

	private void initialize() {
		setLayout(new MigLayout("fill", "[fill]"));
		add(new JScrollPane(textArea));
		add(closeButton, "tag finished, dock south");
		setTitle("Sczegóły błędu");
		setDefaultCloseOperation(HIDE_ON_CLOSE);
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
         setSize((int)(displayMode.getWidth()* DEFAULT_SIZE_WIDTH), (int)(displayMode.getHeight()* DEFAULT_SIZE_HEIGHT));
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
