package cx.ath.jbzdak.jpaGui.ui.autoComplete;

import cx.ath.jbzdak.jpaGui.db.DBManager;

import javax.persistence.EntityManager;
import java.util.Collection;

/**
 * Adater enkapsulujący jakąś interakcję z bazą danych.
 * @author jb
 *
 * @param <V> typ który zawiera lista {@link #getCurentFilteredResults()}
 *
 */
public abstract class DbAdaptor<V> extends SwingWorkerAdaptor<V> {

	private static final long serialVersionUID = 1L;

	protected final DBManager<EntityManager> manager;


	public DbAdaptor(DBManager<EntityManager>  manager) {
		super();
		this.manager = manager;
		if(manager==null) {
         throw new IllegalArgumentException();
      }
	}

	/**
	 * Tutaj wykonujemy odpowiedż na ustawienie filtra. Filt jest dostępny
	 * poprzez {@link #getFilter()}
	 * @return Wyniki przeszukiwania.
	 */
	protected abstract Collection<V>  doInBackground(EntityManager manager);

	@Override
	protected Collection<V> doInBackground() {
      EntityManager entityManager = manager.createProvider();
		try{
			return doInBackground(entityManager);
		}finally{
		   entityManager.close();
		}
	}

   @Override
   protected void done() {
      setCurentFilteredResults(getUnsafe());
   }
}
