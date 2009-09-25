package cx.ath.jbzdak.jpaGui.db;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-21
 */
public interface LifecycleHook {

   /**
    * Sprawdza czy dany listener ma coś przeciw zmianie stanu.
    * Program może sprzeciw zignorować.
    * @param manager manager którego dotyczy zmiana
    * @param targetState stan w którm chce się znaleźć
    * @param reason powód zmiany
    * @return <code>null</code> jeśli nie ma nic przeciwko, wiadomość w przeciwnym wypadku
    */
   public Object mayGoToPhase(AdministrativeDBManager manager, DBState targetState, DBStateChangeReason reason);

   /**
    * Notyfikuje listenera o zmianie stanu.
    * Przy startowaniu bazy odpalamy po zmianie stanu, przy zamykaniu PRZED
    * @param manager manager krótego stan się zmieni
    * @param targetState stan docelow
    */
   public void goToPhase(AdministrativeDBManager manager, DBState targetState);

}
