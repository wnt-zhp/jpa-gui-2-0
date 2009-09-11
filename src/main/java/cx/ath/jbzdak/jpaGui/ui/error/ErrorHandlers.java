package cx.ath.jbzdak.jpaGui.ui.error;

import cx.ath.jbzdak.jpaGui.ClassHandler;
import cx.ath.jbzdak.jpaGui.ExceptionForUser;
import cx.ath.jbzdak.jpaGui.beanFormatter.PatternBeanFormatter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorHandlers {
	public static ClassHandler<Formatter> createShortHandlers() {
		ClassHandler<Formatter> result = new ClassHandler<Formatter>();
		result.put(Object.class, new ToStringFormatter());
		result.put(Throwable.class, new ExceptionFormatter());
		return result;
	}

	public static ClassHandler<Formatter> createLongHandlers() {
		ClassHandler<Formatter> result = new ClassHandler<Formatter>();
		result.put(Object.class, new ToDetailStringFormatter());
		result.put(CharSequence.class, new ToStringFormatter());
		result.put(Throwable.class, new ExceptionDetailFormatter());
		return result;
	}


	public static abstract class Formatter {
		public abstract String getMessage(Object error);
	}

	public static class ToStringFormatter extends Formatter {
		public String getMessage(Object error) {
			return error.toString();
		}
	}

	public static class ExceptionFormatter extends Formatter {
		public String getMessage(Object error) {
			Exception e = (Exception) error;
			if (!(e instanceof ExceptionForUser)
					|| StringUtils.isEmpty(e.getMessage())) {
				return "Wyjątek! Wiadomość: '" + e.getMessage() + " klasa: "
						+ e.getClass().getSimpleName();
			}
			return e.getMessage();
		}
	}

	public static class ToDetailStringFormatter extends Formatter {
		public String getMessage(Object error) {

			return error.toString() + "\n"
					+ ToStringBuilder.reflectionToString(error);
		}
	}

	public static class ExceptionDetailFormatter extends Formatter {

      final PatternBeanFormatter frmtr = new PatternBeanFormatter("Wyjątek! Wiadomość: '{message}'  klasa: '{class.simpleName}\n");
		public String getMessage(Object error) {
			Exception e = (Exception) error;
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			if (!(e instanceof ExceptionForUser)
					|| StringUtils.isEmpty(e.getMessage())) {
				printWriter.append(frmtr.format(e));
			} else {
				printWriter.append(e.getMessage() );
            printWriter.append("\n");
			}
			e.printStackTrace(printWriter);
			printWriter.flush();
			return stringWriter.toString();
		}
	}

	public static class LabelFrmatter extends Formatter{
		@Override
		public String getMessage(Object error) {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
