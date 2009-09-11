package cx.ath.jbzdak.jpaGui.ui.tabbed;

import cx.ath.jbzdak.jpaGui.Utils;
import org.apache.commons.collections.map.DefaultedMap;
import org.apache.commons.collections.map.MultiValueMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;


@SuppressWarnings({"WeakerAccess", "WeakerAccess", "WeakerAccess", "WeakerAccess", "WeakerAccess"})
public class JBTabbedPane extends JTabbedPane{

	private static final long serialVersionUID = 1L;

   /**
    * ActionCommand wysyany gdy jakiś tab przestaje być wybrany
    */
   @SuppressWarnings({"WeakerAccess"})
   public static final String DESELECTED = "DESELECTED";
   /**
    * ActionCommand wysyany gdy jakiś tab jest wybierany
    */
   @SuppressWarnings({"WeakerAccess"})
   public static final String SELECTED = "SELECTED";
   /**
    * ActionCommand wysyany gdy jakiś tab jest wstawiany do panelu
    */
   @SuppressWarnings({"WeakerAccess"})
   public static final String INSERTED = "INSERTED";
   /**
O    */
   @SuppressWarnings({"WeakerAccess"})
   public static final String REMOVED = "REMOVED";


	@SuppressWarnings("unchecked")
	private final Map<Component, ActionListener> listeners = DefaultedMap.decorate(MultiValueMap.decorate(new HashMap(), ArrayList.class),Collections.emptyList());

	@SuppressWarnings("unchecked")
	private final Map<Component, Boolean> cloesable = DefaultedMap.decorate(new HashMap(), Boolean.FALSE);

   public void addListener(Component listenerOwner, ActionListener actionListener){
		if(listenerOwner == null){
			throw new IllegalArgumentException();
		}
		listeners.put(listenerOwner, actionListener);
	}

	@Override
	public void setSelectedComponent(Component c) {
		Component oldComponent = getSelectedComponent();
		super.setSelectedComponent(c);
		doAction(oldComponent, c);
	}

	@Override
	public void setSelectedIndex(int index) {
		Component oldComponent = getSelectedComponent();
		super.setSelectedIndex(index);
		doAction(oldComponent, getSelectedComponent());
	}


	@SuppressWarnings({"WeakerAccess"})
   protected void doAction(Component oldCmp, Component newCmp){
		if(!Utils.equals(oldCmp, newCmp)){
			if(oldCmp!=null){
				fireEvent(new ActionEvent(oldCmp, 0, DESELECTED));
			}
			if(newCmp!=null){
				fireEvent(new ActionEvent(newCmp, 0, SELECTED));
			}
		}
	}

	public void addTabCloseable(String title, Icon icon, Component component, String tip,  boolean closeable){
		this.cloesable.put(component, closeable);
		addTab(title,icon, component, tip);
	}

	@SuppressWarnings({"unchecked", "SuspiciousMethodCalls"})
	private void fireEvent(ActionEvent actionEvent){
		for (ActionListener al : (List<ActionListener>) listeners.get(actionEvent.getSource())){
			al.actionPerformed(actionEvent);
		}
   }

	@Override
	public void insertTab(String title, Icon icon, Component component,
			String tip, int index) {
		super.insertTab(title, icon, component, tip, index);
		super.insertTab(title, icon, component, tip, index);
		setTabComponentAt(indexOfComponent(component), new CloseTabComponent(this, component, cloesable.get(component)));
		fireEvent(new ActionEvent(component, 0, INSERTED));
	}

	@Override
	public void remove(Component component) {
		super.remove(component);
		fireEvent(new ActionEvent(component, 0, REMOVED));
	}
}
