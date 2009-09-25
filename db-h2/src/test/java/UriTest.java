import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-12
 */
public class UriTest {

   public static void main(String[] str) throws IOException {
      File f = new File("");
      URI uri = f.toURI();
      URL url = uri.toURL();
      System.out.println("f=" + f.getCanonicalPath());
      System.out.println(uri.getScheme());
      System.out.println("uri" + url.getProtocol());
   }
}
