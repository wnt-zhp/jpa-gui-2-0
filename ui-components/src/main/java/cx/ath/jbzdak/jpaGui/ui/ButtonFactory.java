package cx.ath.jbzdak.jpaGui.ui;

import cx.ath.jbzdak.common.famfamicons.IconManager;
import org.apache.commons.collections.Factory;
import org.apache.commons.collections.map.DefaultedMap;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public  class ButtonFactory {

	@SuppressWarnings("unchecked")
	static final Map<String, BFactory> buttonFactory = DefaultedMap.decorate(new HashMap() , new DefaultFact());

	static{
		buttonFactory.put("dismiss", new IconFactory("cancel","Zamknij"));
	}

	public static JButton makeButton(String key){
		return buttonFactory.get(key).create();
	}

	public static interface BFactory extends Factory{
		JButton create();
	}


	public static class DefaultFact implements BFactory{
		@Override
		public JButton create() {
			return new JButton();
		}
	}

	public static class IconFactory implements BFactory{

		private final String iconName;

		private final String label;

		public IconFactory(String iconName, String label) {
			super();
			this.iconName = iconName;
			this.label = label;
			try {
				IconManager.getIcon(iconName);
			} catch (NoSuchElementException e) {
				throw new RuntimeException(e);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public JButton create() {
			return new JButton(label, IconManager.getIconSafe(iconName));
		}



	}
}
