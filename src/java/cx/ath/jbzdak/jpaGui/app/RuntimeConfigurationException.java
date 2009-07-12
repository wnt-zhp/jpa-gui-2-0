package cx.ath.jbzdak.jpaGui.app;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public class RuntimeConfigurationException extends RuntimeException{
    public RuntimeConfigurationException() {
    }

    public RuntimeConfigurationException(String message) {
        super(message);
    }

    public RuntimeConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuntimeConfigurationException(Throwable cause) {
        super(cause);
    }
}
