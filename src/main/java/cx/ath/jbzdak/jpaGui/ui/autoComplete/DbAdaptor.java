package cx.ath.jbzdak.jpaGui.ui.autoComplete;

import cx.ath.jbzdak.jpaGui.db.AdministrativeDBManager;

import javax.persistence.EntityManager;

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

	protected final AdministrativeDBManager manager;


	public DbAdaptor(AdministrativeDBManager manager) {
		super();
		this.manager = manager;
		if(manager==null) throw new IllegalArgumentException();
	}

	/**
	 * Tutaj wykonujemy odpowiedż na ustawienie filtra. Filt jest dostępny
	 * poprzez {@link #getFilter()}
	 * @param entityManager manager, tworzony na potrzeby wywołania tej metody
	 * Potem zamykany (i ew. rollbackowany jeśli tranakcja była aktywna).
	 * @return Wyniki przeszukiwania.
	 */
	protected abstract T doInBackground(EntityManager entityManager);


	@Override
	protected T doInBackground() {
		EntityManager entityManager = manager.createEntityManager();
		try{
			return doInBackground(entityManager);
		}finally{
			if(entityManager.getTransaction().isActive()){
				entityManager.getTransaction().rollback();
			}
			entityManager.close();
		}
	}
}
