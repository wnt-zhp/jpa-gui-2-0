package cx.ath.jbzdak.common.famfamicons;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Klasa zarządza ikonami.
 *
 * @author jb
 *
 */
public class IconManager {

	private static final HashMap<String, ImageIcon> originals = new HashMap<String, ImageIcon>();

	private static final HashMap<ScaledKey, ImageIcon> scaled
		= new HashMap<ScaledKey, ImageIcon>();

	private static final Map<String,String> aliases = new HashMap<String, String>();

	private static final Logger log = LoggerFactory.getLogger(IconManager.class);

	/**
	 * Zwraca ikonę o nazwie <code>iconName</code>. Jeżeli ustawiono alias dla ikony
	 * zwraca ikonę dla której aliasem jest <code>iconName</code>
	 * @param iconName nazwa ikony lub jej alias
	 * @return Nie zwraca nulla.
	 * @throws NoSuchElementException Nie znaleziono ikony o zadanej nazwie.
	 * @throws IOException jeśli jakis strumień rzuc
	 */
	public static ImageIcon getIcon(String iconName) throws NoSuchElementException, IOException{
		String aliasedName = aliases.get(iconName);
		if(aliasedName!=null){
			iconName = aliasedName;
		}
		ImageIcon result = originals.get(iconName);
		if(result!=null)
			return result;
      //System.out.println("IconManager.getIcon(" + iconName+")");
		ByteArrayOutputStream ostr = new ByteArrayOutputStream();
		InputStream buffistr
			= IconManager.class.getResourceAsStream("icons/" + iconName + ".png");
		if(buffistr==null)
			throw new NoSuchElementException("Icon not found '" + iconName + "'") ;
		int read;
		while((read = buffistr.read())!=-1){
			ostr.write(read);
		}
		ostr.flush();
		result= new ImageIcon(ostr.toByteArray());
		originals.put(iconName, result);
		return result;
	}

	private static ImageIcon createScaled(ImageIcon orig, ScaledKey key){
		Image scaled = orig.getImage().getScaledInstance(key.width, key.height, Image.SCALE_SMOOTH);
		ImageIcon result = new ImageIcon(scaled);
      //System.out.println("IconManager.createScaled(" + key +")");
		IconManager.scaled.put(key, result);
		return result;
	}

	/**
	 * Zwraca przeskalowaną ikonę. Rozmiar wyniku zalezy od wielkości oryginalnej ikony.
	 *
	 * Uwaga!
	 * @param iconName nazwa ikony
	 * @param scaleFactor stopień skalowania <0 znaczy że ikonę zmniejszamy
	 * @return Może zwracać null jeżeli nie znajdzie ikony.
	 */
	public static Icon getScaled(String iconName, double scaleFactor){
		return getScaled(iconName, scaleFactor, scaleFactor);
	}

	/**
	 * Zwraca przeskalowaną ikonę o zadanych rozmiarach.
	 * @param iconName nazwa ikony
	 * @param width szerokość zwróconej ikony
	 * @param height wysokość zwróconej ikony
	 * @return null jeśli nie znajdzie ikony o zadanej nazwie
	 */
	public static ImageIcon getScaled(String iconName, int width, int height){
		ScaledKey key = new ScaledKey(width, height, iconName);
		ImageIcon result = scaled.get(key);
		if(result!=null){
			return result;
		}
		return createScaled(getIconSafe(iconName), key);
	}

	public static Icon getScaled(String iconName, double scaleFactorX, double scaleFactorY){
		ImageIcon orig = getIconSafe(iconName);
		int width = (int) Math.round(orig.getIconWidth()*scaleFactorX);
		int height = (int) Math.round(orig.getIconHeight()*scaleFactorY);
		return getScaled(iconName, width, height);
	}

	public static ImageIcon getIconSafe(String name){
		try {
			return getIcon(name);
		}catch (IOException e) {
			log.error("IO Error occoured when accessing icon named \"" + name +"\"");
			return null;
		}
	}

	/**
	 * Ustawia alias dla ikony. Po wywołaniu tej funkcji
	 * będzie: <code> getIcon(alias) == getIcon(iconName) </code>
	 * @param alias
	 * @param iconName
	 */
	public static void setAlias(String alias, String iconName){
		aliases.put(alias, iconName);
	}

   private static final class ScaledKey{

		final int width;
      final int height;

		final String iconName;

		public ScaledKey(int width, int height, String iconName) {
			super();
			this.width = width;
			this.height = height;
			this.iconName = iconName;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + height;
			result = prime * result
					+ ((iconName == null) ? 0 : iconName.hashCode());
			result = prime * result + width;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ScaledKey other = (ScaledKey) obj;
			if (height != other.height)
				return false;
			if (iconName == null) {
				if (other.iconName != null)
					return false;
			} else if (!iconName.equals(other.iconName))
				return false;
			if (width != other.width)
				return false;
			return true;
		}

      @Override
      public String toString() {
         return "ScaledKey{" +
                 "width=" + width +
                 ", height=" + height +
                 ", iconName='" + iconName + '\'' +
                 '}';
      }



	}
}
