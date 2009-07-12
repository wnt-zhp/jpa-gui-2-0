package cx.ath.jbzdak.jpaGui.genericListeners;

import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.event.MouseEvent;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-06-27
 */
public class PopupMenuMouseListener extends DoStuffMouseListener{

   private final JPopupMenu menu;

   private final Component invoker;

   public PopupMenuMouseListener(JPopupMenu menu, Component invoker) {
      this.menu = menu;
      this.invoker = invoker;
   }

   @Override
   protected void doStuff(MouseEvent e) {
      if(e.isPopupTrigger()){
         menu.show(invoker,e.getX(), e.getY());         
      }
   }
}
