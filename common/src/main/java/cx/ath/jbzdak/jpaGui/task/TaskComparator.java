package cx.ath.jbzdak.jpaGui.task;

import java.io.Serializable;
import java.util.Comparator;

public class TaskComparator implements Comparator<Task<?>>, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public int compare(Task<?> o1, Task<?> o2) {
		return o1.getPriority()-o2.getPriority();
	}

}
