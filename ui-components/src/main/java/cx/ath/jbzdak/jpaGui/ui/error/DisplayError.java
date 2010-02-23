package cx.ath.jbzdak.jpaGui.ui.error;

import cx.ath.jbzdak.jpaGui.ExceptionForUser;
import cx.ath.jbzdak.jpaGui.Utils;
import cx.ath.jbzdak.jpaGui.beanFormatter.PatternBeanFormatter;
import cx.ath.jbzdak.jpaGui.ui.MainIconManager;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;

import static cx.ath.jbzdak.jpaGui.ui.MainIconManager.*;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-11-28
 */
public class DisplayError {

   private static final Logger LOGGER = LoggerFactory.getLogger(DisplayError.class);

   public static void showError(Exception e, Frame owner){
      LOGGER.warn("Exception shown to user", e);
      ErrorDialog errorDialog = new ErrorDialog(e, owner);
      errorDialog.pack();
      Utils.initLocation(errorDialog);
      errorDialog.setVisible(true);
   }


   public static void showError(String message, Frame owner){
      ErrorDialog errorDialog = new ErrorDialog(message, null, true,  owner);
      errorDialog.pack();
      Utils.initLocation(errorDialog);
   }


   public static class ErrorDialog extends JDialog{

      PatternBeanFormatter messageFormatter = new PatternBeanFormatter(
              "<html><strong>Wiadomość<br/></strong>{0}</html>"
      );

      Exception exception;

      JLabel message;

      JButton showDetails;

      JTextArea details;

      JButton close;

      public ErrorDialog(Exception e, Frame owner) {
         this(e, true, owner);
      }

      public ErrorDialog(String message, String detailedMessage, boolean modal, Frame owner) {
         super(owner, modal);
         initialize(message, detailedMessage);
      }


      public ErrorDialog(Exception e, boolean modal, Frame owner) {
         super(owner, modal);
         StringWriter stringWriter = new StringWriter();
         PrintWriter printWriter = new PrintWriter(stringWriter);
         e.printStackTrace(printWriter);
         printWriter.flush();
         initialize(e.getMessage(), stringWriter.toString());
      }

      void initialize(String messageString, String detailsSting){
         setIconImage(getIcon("error").getImage());
         setTitle("Błąd");
         setLayout(new MigLayout("growx, hidemode 4", "[fill]", "[||fill|]"));
         message.setText(messageFormatter.format(messageString));
         details = new JTextArea();
         details.setEditable(false);
         details.setLineWrap(true);
         details.setText(detailsSting);
         showDetails = Utils.createIconButton(getIcon("book_open"));
         showDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               showDetails.setIcon(getIcon(details.isVisible()?"book":"book_open"));
               details.setVisible(!details.isVisible());
               pack();
               Utils.initLocation(ErrorDialog.this);
            }
         });
         close = new JButton("Zamkinj", getIcon("cancel"));
         JPanel buttonPanel = new JPanel(new MigLayout("growx"));
         buttonPanel.add(close, "tag cancel");
         add(message);
         add(showDetails);
         add(details);
         add(close);
         details.setVisible(false);
         if(StringUtils.isBlank(detailsSting)){
            showDetails.setVisible(false);
         }
      }
      
   }
}
