package cx.ath.jbzdak.jpaGui.ui.formatted;

/**
 * @param <V>
 * @param <V2>
 */
public interface MyFormatter<V, V2> {

   public V parseValue(String text) throws Exception;

	public String formatValue(V2 value) throws FormattingException;

   

}
