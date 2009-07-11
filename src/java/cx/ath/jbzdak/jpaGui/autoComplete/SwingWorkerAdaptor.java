package cx.ath.jbzdak.jpaGui.autoComplete;

import javax.swing.SwingWorker;

import java.util.concurrent.ExecutionException;

/**
 * Używa {@link SwingWorker} do wykonywania prac w tle.
 *
 * @author jb
 *
 * @param <T>
 */
public abstract class SwingWorkerAdaptor<T, V> extends AutoCompleteAdaptor<V> {

	private static final long serialVersionUID = 1L;

	private transient boolean workBegin;

	private transient boolean newQueryNeeded;

	private  T value;

	private ExecutionException ee;

	private InterruptedException ie;

	private transient int workerNum=0;

	T get() throws ExecutionException, InterruptedException{
		if(ee !=null){
			throw ee;
		}
		if(ie != null){
			throw ie;
		}
		return value;
	}


	public T getUnsafe(){
		try{
			return get();
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	protected  void onFilterChange() {
		synchronized(this){
			if(workBegin){
				newQueryNeeded=true;
			}else{
				workBegin = true;
				newQueryNeeded=false;
				new worker().execute();
			}
		}

	}

	/**
	 * Metoda wywoływana w tle.
	 * @return wyniki przeszukiwania
	 */
	protected abstract T doInBackground();

	/**
	 * Metoda wywoływana w EDT - ma dostępne wyniki wywołania
	 * {@link #doInBackground()}.
	 *
	 * W czasie wykonania tej metody bierzący filtr jest dostępny
	 * jako wynik wywołania {@link #getFilter()}.
	 * @see #get()
	 * @see #getUnsafe()
	 */
	protected abstract void done();

	private class worker extends SwingWorker<T, Void>{

		@Override
		protected T doInBackground() throws Exception {
			return SwingWorkerAdaptor.this.doInBackground();
		}

		@Override
		protected void done() {
			try {
				SwingWorkerAdaptor.this.value = get();
			} catch (InterruptedException e) {
				ie = e;
			} catch (ExecutionException e) {
				ee = e;
			}finally{
				synchronized (this) {
					workBegin = false;
					if(newQueryNeeded){
						onFilterChange();
					}
					--workerNum;
				}
				SwingWorkerAdaptor.this.done();
			}
		}

	}
}
