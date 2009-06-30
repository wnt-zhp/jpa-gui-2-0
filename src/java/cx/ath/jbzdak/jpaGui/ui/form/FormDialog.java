package cx.ath.jbzdak.jpaGui.ui.form;

import cx.ath.jbzdak.jpaGui.Utils;
import cx.ath.jbzdak.jpaGui.genericListeners.HideWindowTask;
import cx.ath.jbzdak.jpaGui.task.Task;
import net.miginfocom.swing.MigLayout;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-06-27
 */
public class FormDialog extends JDialog{

   private DAOForm form;

   private JComponent renderedComponent;

   private final OkButtonFormPanel buttonFormPanel;

   private boolean finishedSuccesfully = false;

   public FormDialog(Frame owner, String title, boolean modal) {
      super(owner, title, modal);
      setLayout(new MigLayout("fillx", "[grow, fill]", "[grow, fill][]"));
      buttonFormPanel = new OkButtonFormPanel();
      add(buttonFormPanel, "cell 0 1");
      buttonFormPanel.setCommitOnOK(false);
      buttonFormPanel.addBothTaskAction(new HideWindowTask<DAOForm>(1000, this));
      buttonFormPanel.addCancelActionTask(new Task<DAOForm>(0, "CANCEL") {
         @Override
         public void doTask(@Nullable DAOForm daoForm, @Nullable Object... o) throws Exception {
            form.rollback();
         }
      });
      addComponentListener(new ComponentAdapter(){
         @Override
         public void componentHidden(ComponentEvent e) {
            form.getDao().rollbackIfActive();
         }
      });

   }

   public void setForm(DAOForm form) {
      this.form = form;
      buttonFormPanel.setForm(form);
   }

   public void setRenderedComponent(JComponent renderedComponent) {
      this.renderedComponent = renderedComponent;
      add(renderedComponent, "cell 0 0 ");
   }

   public boolean isFinishedSuccesfully() {
      return finishedSuccesfully;
   }

   public void setFinishedSuccesfully(boolean finishedSuccesfully) {
      this.finishedSuccesfully = finishedSuccesfully;
   }

   public void showDialog(){
      form.getDao().beginTransaction();
      form.startEditing();
      pack();
      Utils.initLocation(this);
      finishedSuccesfully = false;
      setVisible(true);
   }

   public void addOkActionTask(Task task) {
      buttonFormPanel.addOkActionTask(task);
   }

   public void addCancelActionTask(Task task) {
      buttonFormPanel.addCancelActionTask(task);
   }

   public void addBothTaskAction(Task task) {
      buttonFormPanel.addBothTaskAction(task);
   }
}
