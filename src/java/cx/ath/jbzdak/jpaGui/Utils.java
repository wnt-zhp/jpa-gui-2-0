package cx.ath.jbzdak.jpaGui;

import javax.annotation.CheckForNull;
import javax.persistence.Id;
import javax.swing.*;
import org.apache.commons.math.util.MathUtils;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.Bindings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.rmi.server.UID;
import java.util.Calendar;
import java.util.Date;

/**
 * Dziwne utilsy
 * @author jb
 *
 */
public class Utils {

	private static final Logger LOGGER = makeLogger();

   private static final File TMP_FOLDER;

   private static final File PROGRAM_FOLDER;

   static{
//      String classPath = System.getProperty("java.class.path");
//      String[] jars = classPath.split(System.getProperty("path.separator"));
//      System.out.println(classPath);
//      File jar = new File(jars[0]);
//      File parent = jar.getParentFile();
//      File result = null;
//
//      if(parent!=null && parent.isDirectory()){
//         File tmpFile = initTempFile(parent, "txt");
//         try {
//            if(tmpFile.createNewFile()){
//               result = parent;
//            }
//         } catch (IOException e) {
//            LOGGER.debug("", e);
//         }
//      }
//      if(result==null){1
//         result = new File(".");
//      }
      PROGRAM_FOLDER = new File(System.getProperty("user.dir"));
      LOGGER.info("Progam folder is {} ", PROGRAM_FOLDER);
   }

   static{
      File tmpFolder = null;
      try{
         tmpFolder = new File(PROGRAM_FOLDER, ".tmp");
         tmpFolder.mkdir();
      }catch (Exception e){
         LOGGER.warn("Exception while making tmp folder",e);
      }finally{
         TMP_FOLDER = tmpFolder;
         LOGGER.info("tmp folder is {}", TMP_FOLDER);
      }
   }

	private Utils(){};


	/**
	 * Zamienia Object... w Object[] żeby opakowywać argumenty {@link Logger#debug(String, Object[])}
	 */
	public static final Object[] wrap(Object... values){
		return values;
	}

	/**
	 * Tworzy loggera zainicjalizowanego nazwą klasy która go wywyołała
	 * @return
	 */
	public static Logger makeLogger(){
		StackTraceElement directCaller = Thread.currentThread().getStackTrace()[2];
		return LoggerFactory.getLogger(directCaller.getClassName());
	}

	/**
	 * Tworzy loggera zainicjalizowanego ntym wywołującym tą metodę
	 */
	public static Logger makeLogger(int n){
		StackTraceElement directCaller = Thread.currentThread().getStackTrace()[1 + n];
		return LoggerFactory.getLogger(directCaller.getClassName());
	}

	@SuppressWarnings("unchecked")
	public static Binding createAutoBinding(
			UpdateStrategy strategy,
			Object source, String sourceProperty, Object target,
			String targeTProperty) {
		return Bindings.createAutoBinding(strategy, source, BeanProperty
				.create(sourceProperty), target, BeanProperty
				.create(targeTProperty));
	}

	/**
	 * Sprawdza czy pole annotowane annotacją {@link Id} jest nullem czy nie.
	 * Jeśli klasa nie ma takiego pola leci {@link IllegalArgumentException}.
	 * @param object obiekt którego id jest sprawdzane
	 * @return true jesli id obiektu nie jest nullem.
	 */
	public static boolean isIdNull(Object object) {
		return getId(object)==null;
	}

	public static Object getId(Object object) {
		if(object==null){
			throw new IllegalArgumentException();
		}
		Field id = null;
		for (Field f : object.getClass().getDeclaredFields()) {
			if (f.getAnnotation(Id.class) != null) {
				id = f;
			}
		}
		if (id == null) {
			throw new IllegalArgumentException();
		}
		String fieldName = id.getName();
		String getter = "get" + Character.toUpperCase(fieldName.charAt(0))
				+ fieldName.substring(1);
		Method m;
		try {
			m = object.getClass().getMethod(getter);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException(e);
		}
		try {
			return m.invoke(object);
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e.getCause());
		}
	}

	public static <T> boolean equals(T o1, T o2 ){
		return o1==null ? o2==null : o1.equals(o2);
	}


	public static <T> boolean compareNullity(T o1, T o2 ){
		return o1==null ? o2==null : o2 != null;
	}


	/**
	 * Porównuje dwa stringi które mogą być nullami.
	 */
    public static int compare(String s1, String s2){
		if(s1!=null && s2!=null){
			return s1.compareTo(s2);
		}else{
			if(s1==null){
				if(s2==null){
					return 0;
				}else{
					return -1;
				}
			}
            //noinspection ConstantConditions
            if(s2==null){
				return 0;
			}
			return 1;
		}
	}

	/**
	 * Zwraca datę o daysFromToday odległą od dziś. Używa bierzącego locale.
	 */
	public static Date getRelativeDate(Integer daysFromToday){
		if(daysFromToday==null){
			return new Date();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		cal.add(Calendar.DAY_OF_MONTH, daysFromToday);
		return cal.getTime();
	}

	public static void initLocation(Window window){
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		if(window.getWidth()==0 || window.getHeight()==0){
			LOGGER.error("Not packed window passed to initLocation");
		}
		center.x-=window.getWidth()/2;
		center.y-=window.getHeight()/2;
		window.setLocation(center);
	}

	/**
	 * Ustawia registerTo tak że gdy ma focus wciśnięcie keyStroke spowoduje
	 * wywołanie na button kliknięcia
	 * @param button
	 * @param registerTo
	 * @param keyStroke
	 */
	public static void registerKeyBinding(final JButton button, JComponent registerTo, KeyStroke keyStroke){
		String uid = new UID().toString();
		registerTo.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(keyStroke, uid);
		registerTo.getActionMap().put(uid, new AbstractAction(){
			private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			button.doClick();
		}});
	}

	public static BigDecimal round(BigDecimal value, int places){
		if(places < 0){
			throw new IllegalArgumentException();
		}
		return BigDecimal.valueOf(MathUtils.round(value.doubleValue(), places));
	}

	public static final String valueOf(Object obj){
		return obj==null?"":obj.toString();
	}

	public static final JButton createIconButton(Icon icon){
		JButton result = new JButton(icon);
		result.setBorder(BorderFactory.createEmptyBorder());
		result.setBorderPainted(false);
		result.setContentAreaFilled(false);
      result.setMargin(new Insets(0,0,0,0));
		result.putClientProperty("JComponent.sizeVariant", "tiny");
		return result;
	}

   /**
    * Sprawdza pierwszy element classpahta i bierze jego parenta
    * @return
    */
   @Deprecated()
   public static final File guessProgramFolder(){
      return PROGRAM_FOLDER;
   }

     public static final File createTmpFile(File folder, String extension){
      File tmpFile;
      int counter = 0;
      do{
         tmpFile = initTempFile(folder, extension);
         try {
            if(tmpFile.createNewFile()){
            break;
         }
         } catch (IOException e) {
            //Igrnore
         }
         counter++;
      } while(counter < 10);
      if(counter == 9){
         throw new IllegalStateException("Couldnt create new file. . . ");
      }
      tmpFile.deleteOnExit();
      return tmpFile;

   }

   public static final File createTmpFile(String extension){
      return  initTempFile(TMP_FOLDER, extension);
   }

   public static File getTmpFolder() {
      return TMP_FOLDER;
   }

   private static final File initTempFile(File folder, String extension){
      UID uuid = new UID();
      String filename = uuid.toString().replaceAll("[;:\\-,\\.]", "") + (extension==null?"":extension);
      return new File(folder, filename);
   }

   public static String cleanFileName(String desiredName){
      return desiredName.replaceAll("[^a-zA-Z0-9]", "_");
   }

   @CheckForNull
   public static Frame getFrameForComponent(Component component){
      Window w = SwingUtilities.windowForComponent(component);
      while(w !=null){
         if (w instanceof Frame) {
            Frame frame = (Frame) w;
            return frame;
         }
         w = SwingUtilities.windowForComponent(w);
      }
      return null;
   }

      public static JPanel wrapInTiteledPanel(JComponent jComponent, String title){
      JPanel panel = new JPanel(new BorderLayout());
      panel.setBorder(BorderFactory.createTitledBorder(title));
      panel.add(jComponent, BorderLayout.CENTER);
      return panel;
   }

   @CheckForNull public static <T extends Throwable> T findCauseOfClass(Throwable t, Class<T> clazz){
      if(t==null){
         return null;
      }
      if(clazz.isInstance(t)){
         return  clazz.cast(t);
      }
      return findCauseOfClass(t.getCause(), clazz);
   }




//	public static <T> T cloneObject(T t, String cloningProperties){
//		try {
//			Class clazz = t.getClass();
//			T result = (T) clazz.newInstance();
//			for(String property : cloningProperties.split(",")){
//				String[] p = property.split(" ");
//				String fieldName = p[0];
//				String operation = p.length>1?p[1]:null;
//				Field f = clazz.getField(fieldName);
//				if(operation.equals("null")){
//
//				}
//			}
//		} catch (InstantiationException e) {
//			LOGGER.warn("",e);
//		} catch (IllegalAccessException e) {
//			LOGGER.warn("",e);
//		} catch (SecurityException e) {
//			LOGGER.warn("",e);
//		} catch (NoSuchFieldException e) {
//			LOGGER.warn("",e);
//		}
//	}
}
