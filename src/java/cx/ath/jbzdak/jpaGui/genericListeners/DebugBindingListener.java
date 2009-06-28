package cx.ath.jbzdak.jpaGui.genericListeners;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.Binding.SyncFailure;
import org.jdesktop.beansbinding.BindingListener;
import org.jdesktop.beansbinding.PropertyStateEvent;


//Jeśli chodzi o:CallToPrintStackTrace to nie chce tu używać logowania bo ten kompoennt jest do użycia
//też w momencie w którym jeszcze logowania NIE MA
@SuppressWarnings({"unchecked", "ThrowableResultOfMethodCallIgnored", "CallToPrintStackTrace", "UseOfSystemOutOrSystemErr"})
public class DebugBindingListener implements BindingListener  {

	@Override
	public void bindingBecameBound(Binding binding) {
		//log.debug("Binding became bound {}", binding);
		System.out.println("DebugBindingListener.bindingBecameBound()");
	}

   @Override
	public void bindingBecameUnbound(Binding binding) {
		//log.debug("Binding became unbound {}", binding);
		System.out.println("DebugBindingListener.bindingBecameUnbound()");

	}

	@Override
	public void sourceChanged(Binding binding, PropertyStateEvent event) {
		//log.debug("Source changed event {}, binding {}", event, binding);
		System.out.println("DebugBindingListener.sourceChanged()" + event);
	}

	@Override
	public void syncFailed(Binding binding, SyncFailure failure) {
		//log.debug("SyncFailed {}, event {}", binding, failure);
		System.out.println("DebugBindingListener.syncFailed()" + ToStringBuilder.reflectionToString(failure));

		if(failure.getType() == Binding.SyncFailureType.CONVERSION_FAILED && failure.getConversionException()!=null){
			failure.getConversionException().printStackTrace();
		}
	}

	@Override
	public void synced(Binding binding) {
		//log.debug("Synced {}", binding);
		System.out.println("DebugBindingListener.synced()");
	}

	@Override
	public void targetChanged(Binding binding, PropertyStateEvent event) {
		//log.debug("Target changed {}, event {}", binding, event);
		System.out.println("DebugBindingListener.targetChanged()" + event);

	}

}
