package cx.ath.jbzdak.zarlok.ui.dzien;

import cx.ath.jbzdak.jpaGui.Utils;
import cx.ath.jbzdak.jpaGui.ui.error.DisplayErrorDetailsDialog;
import cx.ath.jbzdak.jpaGui.ui.error.ErrorHandlers;
import cx.ath.jbzdak.zarlok.entities.Dzien;
import cx.ath.jbzdak.zarlok.raport.RaportException;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class TreePopupMenu extends JPopupMenu {
   private Dzien d;

   private final DniTreePanelModel dniTreePanelModel;


   boolean printEnable = Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Action.PRINT);


   private final JMenuItem makeZZMenuItem = new JMenuItem("Drukuj ZZ");
   private final JMenuItem saveZZMenuItem = new JMenuItem("Zapisz ZZ");
   private final JMenuItem saveStanMenuItem = new JMenuItem("Zapisz stan magazynu");
   private final JMenuItem printStanMagazynu = new JMenuItem("Drukuj stan magazyny");

   TreePopupMenu(DniTreePanelModel dniTreePanelModel) {
      this.dniTreePanelModel = dniTreePanelModel;
   }

   {
      makeZZMenuItem.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            try {
               dniTreePanelModel.raportFactory.printZZ(d);
            } catch (RaportException re) {
               DisplayErrorDetailsDialog dialog = new DisplayErrorDetailsDialog();
               dialog.setText(ErrorHandlers.createLongHandlers().getHandler(re).getMessage(re));
               dialog.pack();
               Utils.initLocation(dialog);
               dialog.setVisible(true);
            }
         }
      });
      saveZZMenuItem.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            try {
               dniTreePanelModel.raportFactory.saveZZ(d);
            } catch (RaportException re) {
               DisplayErrorDetailsDialog dialog = new DisplayErrorDetailsDialog();
               dialog.setText(ErrorHandlers.createLongHandlers().getHandler(re).getMessage(re));
               dialog.pack();
               Utils.initLocation(dialog);
               dialog.setVisible(true);
            }
         }
      });
      saveStanMenuItem.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            try{
               dniTreePanelModel.raportFactory.saveStanMagazynu(d);
            }catch (RaportException re){
               DisplayErrorDetailsDialog.showErrorDialog(re, null);
            }
         }
      });
      printStanMagazynu.addActionListener(new ActionListener(){
         @Override
         public void actionPerformed(ActionEvent e) {
            try{
               dniTreePanelModel.raportFactory.printStanMagazynu(d);
            }catch (RaportException re){
               DisplayErrorDetailsDialog.showErrorDialog(re, null);
            }
         }
      });
      add(makeZZMenuItem);
      add(saveZZMenuItem);
      add(printStanMagazynu);
      add(saveStanMenuItem);
   }

   @Override
   public void show(Component invoker, int x, int y) {
      if (dniTreePanelModel.selectedItems.size() == 1 && dniTreePanelModel.onlyDniSelected) {
         makeZZMenuItem.setEnabled(true);
         saveZZMenuItem.setEnabled(true);
         printStanMagazynu.setEnabled(true);
         saveStanMenuItem.setEnabled(true);
         d = (Dzien) dniTreePanelModel.selectedItems.get(0);
      } else {
         makeZZMenuItem.setEnabled(false);
         saveZZMenuItem.setEnabled(false);
         printStanMagazynu.setEnabled(false);
         saveStanMenuItem.setEnabled(false);
         d = null;
      }
      super.show(invoker, x, y);
   }
}
