package cx.ath.jbzdak.jpaGui.task;

import javax.annotation.Nullable;


/**
 * Task dla {@link TasksExecutor}. 
 * 
 * Taski są wykonywane rosnąco względem {@link #priority}. 
 * Czyli mniejszy protrytet wykonany wcześniej. 
 * @author jb
 *
 * @param <T>
 */
public abstract class Task<T> {
	
	private final int priority;
	
	private final String name; 


	protected Task() {
		super();
		this.priority = 0;
		this.name = "UNNAMED TASK";
	}

	
	public Task(int priority, String name) {
		super();
		this.priority = priority;
		this.name = name;
	}

	/**
	 * Wykonuje zadanie. 
	 * @param t parametr
	 * @param o parametry 
	 * @throws Exception W przypadku błedu wykonania trzeba czymś rzucić!
	 */
	@SuppressWarnings({"UnusedParameters", "RedundantThrows"})
   public abstract void doTask(@Nullable T t , @Nullable Object... o) throws Exception;

	/**
	 * Priorytet zdania - mniejszy wykonane wcześniej
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Nazwa zadania - trafia do logów. 
	 * @return
	 */
	public String getName() {
		return name;
	}
}
