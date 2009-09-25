package cx.ath.jbzdak.jpaGui.ui.autoComplete;

import cx.ath.jbzdak.jpaGui.db.AdministrativeDBManager;
import cx.ath.jbzdak.jpaGui.db.DBManager;
import cx.ath.jbzdak.jpaGui.db.Query;

/**
 * Adater enkapsulujący jakąś interakcję z bazą danych.
 * @author jb
 *
 * @param <T> Typ zwracany przez {@link #doInBackground()}
 * @param <V> typ który zawiera lista {@link #getCurentFilteredResults()}
 *
 */
public abstract class DbAdaptor<T, V> extends SwingWorkerAdaptor<T, V> {

	private static final long serialVersionUID = 1L;

	protected final DBManager manager;


	public DbAdaptor(AdministrativeDBManager manager) {
		super();
		this.manager = manager;
		if(manager==null) throw new IllegalArgumentException();
	}

	/**
	 * Tutaj wykonujemy odpowiedż na ustawienie filtra. Filt jest dostępny
	 * poprzez {@link #getFilter()}
	 * @return Wyniki przeszukiwania.
	 */
	protected abstract T doInBackground(Query query);


   protected abstract Query openQuery(DBManager manager);

	@Override
	protected T doInBackground() {
		Query q = openQuery(manager);
		try{
			return doInBackground(q);
		}finally{
		   q.close();
		}
	}
}
