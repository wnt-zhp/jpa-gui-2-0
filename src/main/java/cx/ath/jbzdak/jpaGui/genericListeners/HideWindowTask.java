package cx.ath.jbzdak.jpaGui.genericListeners;

import cx.ath.jbzdak.jpaGui.task.Task;

import java.awt.Window;

public class HideWindowTask<T> extends Task<T> {

	private final Window window;

	public HideWindowTask(Window window) {
		super(0, "HIDE_WINDOW");
		this.window = window;
	}

   @SuppressWarnings({"SameParameterValue"})
   public HideWindowTask(int priority, Window window) {
      super(priority, "HIDE_WINDOW");
      this.window = window;
   }

   @Override
	public void doTask(T t, Object... o) throws Exception {
		window.setVisible(false);

	}

}
