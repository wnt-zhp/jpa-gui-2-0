package cx.ath.jbzdak.jpaGui.genericListeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-28
 */
public abstract class DoStuffMouseListener implements MouseListener, Serializable{

   @Override
   public void mouseClicked(MouseEvent e) {
      doStuff(e);
   }

   @Override
   public void mousePressed(MouseEvent e) {
      doStuff(e);
   }

   @Override
   public void mouseReleased(MouseEvent e) {
      doStuff(e);
   }

   @Override
   public void mouseEntered(MouseEvent e) {
      doStuff(e);
   }

   @Override
   public void mouseExited(MouseEvent e) {
      doStuff(e);
   }

   protected abstract void doStuff(MouseEvent e);
}
