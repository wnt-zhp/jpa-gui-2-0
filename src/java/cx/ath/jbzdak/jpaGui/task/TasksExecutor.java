package cx.ath.jbzdak.jpaGui.task;

import static cx.ath.jbzdak.jpaGui.Utils.makeLogger;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Wykonuje {@link Task}i.
 * @author jb
 *
 * @param <T> Typ pierwszego parametru przekazany do {@link Task#doTask(Object, Object...)}
 */
public class TasksExecutor<T> {

	private static Logger logger = makeLogger();

	private List<Task<? super T>> taskList = new ArrayList<Task<? super T>>();

	public void addTask(Task<? super T> t){
		taskList.add(t);
	}

	/**
	 * Wykonuje zadania i połykając i logując wyjątki.
	 * @param obj parametr dla którego zadanie będzie wykonane. Może być przez zdanie modyfikowany.
	 * @param o
	 */
	public void executeSwallow(T obj, Object... o) {
		logger.debug("Started executing tasks");
		Collections.sort(taskList, new TaskComparator());
		for(Task<? super T> t : taskList){
			logger.debug("Executing task named {}, task has priority {}", t.getName(), t.getPriority());
			try {
				t.doTask(obj, o);
			} catch (Exception e) {

					logger.warn("Encountered exception when executing task named {}, task has priority {}", t.getPriority(), t.getPriority());
					logger.warn("Error was",e);
			}
		}
		logger.debug("Finished executing tasks");
	}

   	/**
	 * Wykonuje zadania i połykając i logując wyjątki.
	 * @param obj parametr dla którego zadanie będzie wykonane. Może być przez zdanie modyfikowany.
	 * @param o
	 */
	public void executeStop(T obj, Object... o) {
		logger.debug("Started executing tasks");
		Collections.sort(taskList, new TaskComparator());
		for(Task<? super T> t : taskList){
			logger.debug("Executing task named {}, task has priority {}", t.getName(), t.getPriority());
			try {
				t.doTask(obj, o);
			} catch (Exception e) {
            logger.warn("Encountered exception when executing task named {}, task has priority {}\n STOPPING EXECUTION", t.getPriority(), t.getPriority());
            logger.warn("Error was",e);
            return;
			}
		}
		logger.debug("Finished executing tasks");
	}



	public void executeThrow(T obj, Object... o) throws Exception {
		logger.debug("Started executing tasks");
		Collections.sort(taskList, new TaskComparator());
		for(Task<? super T> t : taskList){
			logger.debug("Executing task named {}, task has priority {}", t.getName(), t.getPriority());
			t.doTask(obj, o);
		}
		logger.debug("Finished executing tasks");
	}

	public List<Task<? super T>> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Task<? super T>> taskList) {
		this.taskList = taskList;
	}
}
