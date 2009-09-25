package cx.ath.jbzdak.jpaGui.db;

public abstract class Transaction<PROVIDER> {

	protected boolean closeEntityManager = false;

	@SuppressWarnings({"RedundantThrows"})
   public abstract void doTransaction(PROVIDER provider) throws Exception;

}
