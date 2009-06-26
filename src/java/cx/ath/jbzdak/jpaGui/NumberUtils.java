package cx.ath.jbzdak.jpaGui;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-15
 */
public class NumberUtils {

    public static <T extends Comparable<T>> T min(T t1, T t2){
        return t1.compareTo(t2)>0?t2:t1;
    }

    public static <T extends Comparable<T>> T max(T t1, T t2){
        return t1.compareTo(t2)>0?t1:t2;
    }
}
