package cx.ath.jbzdak.jpaGui.genericListeners;

import cx.ath.jbzdak.jpaGui.Utils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

@SuppressWarnings({"ALL"})
public class DebugPropertyChangeListener implements PropertyChangeListener {

	private final String propertyName ;

	private final String objectName ;

	public DebugPropertyChangeListener() {
		this(null);
	}
	public DebugPropertyChangeListener(String propertyName) {
		this(propertyName, null);
	}
	public DebugPropertyChangeListener(String propertyName, String objectName) {
		super();
		this.propertyName = propertyName;
		this.objectName = objectName;
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ((propertyName==null || propertyName.equals(evt.getPropertyName())) && !Utils.equals(evt.getOldValue(), evt.getNewValue())) {
			System.out.println(StringUtils.defaultString(objectName) + ToStringBuilder.reflectionToString(evt));
		}
	}


}
