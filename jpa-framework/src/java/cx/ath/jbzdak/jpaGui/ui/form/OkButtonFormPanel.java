package cx.ath.jbzdak.jpaGui.ui.form;

import cx.ath.jbzdak.jpaGui.task.Task;
import cx.ath.jbzdak.jpaGui.task.TasksExecutor;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

public class OkButtonFormPanel<T> extends JPanel{

	private static final long serialVersionUID = 1L;

	DAOForm<? extends T, ? extends FormElement> form;

	JButton okButton, cancelButton;

	boolean commitOnOK = true, rollbackOnCancel = true;

	TasksExecutor<DAOForm<? extends T, ? extends FormElement>>  okTasks = new TasksExecutor<DAOForm<? extends T, ? extends FormElement>>();

	TasksExecutor<DAOForm<? extends T, ? extends FormElement>> cancelTasks = new TasksExecutor<DAOForm<? extends T, ? extends FormElement>>();

	public OkButtonFormPanel() {
		setLayout(new MigLayout());
		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(commitOnOK){
					form.commit();
				}
				okTasks.executeSwallow(form);
			}
		});
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				form.rollback();
				cancelTasks.executeSwallow(form);
			}
		});
		add(okButton, "tag ok");
		add(cancelButton, "tag cancel");
	}

	public void addOkActionTask(Task<DAOForm> task){
		okTasks.addTask(task);
	}

	public void addCancelActionTask(Task<DAOForm> task){
		cancelTasks.addTask(task);
	}

	public void addBothTaskAction(Task<DAOForm> task){
		cancelTasks.addTask(task);
		okTasks.addTask(task);
	}

	public boolean isCommitOnOK() {
		return commitOnOK;
	}

	public void setCommitOnOK(boolean commitOnOK) {
		this.commitOnOK = commitOnOK;
	}

	public boolean isRollbackOnCancel() {
		return rollbackOnCancel;
	}

	public void setRollbackOnCancel(boolean rollbackOnCancel) {
		this.rollbackOnCancel = rollbackOnCancel;
	}

	public DAOForm<? extends T, ? extends FormElement> getForm() {
		return form;
	}

	public void setForm(DAOForm<? extends T, ? extends FormElement> form) {
		this.form = form;
	}

	public TasksExecutor<DAOForm<? extends T, ? extends FormElement>> getOkTasks() {
		return okTasks;
	}

	public void setOkTasks(TasksExecutor<DAOForm<? extends T, ? extends FormElement>> okTasks) {
		this.okTasks = okTasks;
	}

	public TasksExecutor<DAOForm<? extends T, ? extends FormElement>> getCancelTasks() {
		return cancelTasks;
	}

	public void setCancelTasks(TasksExecutor<DAOForm<? extends T, ? extends FormElement>> cancelTasks) {
		this.cancelTasks = cancelTasks;
	}

	public void registerSaveShortcut(JComponent registerTo){
		registerTo.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK), "save");
		registerTo.getActionMap().put("save", new AbstractAction(){
			private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			okButton.doClick();
		}});
	}

}
