package cx.ath.jbzdak.jpaGui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: Jacek Bzdak jbdak@gmail.com
 * Date: May 2, 2010
 */
public interface TestListener extends ActionListener {
   void actionPerformed(ActionEvent e);

   void actionPerformed(ActionEvent e, Integer r);

   void actionPerformed(ActionEvent e, Float z);
}
