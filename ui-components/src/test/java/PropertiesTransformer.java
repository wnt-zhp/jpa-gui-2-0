import javax.swing.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-11-29
 */
public class PropertiesTransformer {

   public static void main(String[] foo) throws Exception{
      StringBuffer input = new StringBuffer();
      {
         InputStream inputStream = PropertiesTransformer.class.getClass().getResourceAsStream("/input.txt");
         Reader reader = new InputStreamReader(inputStream);
         int read;
         while((read = reader.read())!=-1){
            input.append((char) read);
         }
      }

      Map<String, String> results = new TreeMap<String, String>();
      Pattern pattern = Pattern.compile("\\{\\s*\"([\\w\\d\\.]+)\"\\s*,\\s*\"([^\"]+)\"\\s*}");
      Matcher matcher = pattern.matcher(input);
      while(matcher.find()){
         results.put(matcher.group(1), matcher.group(2));
      }
      for (Map.Entry<String, String> e : results.entrySet()) {
         System.out.println(e.getKey() + "=" + e.getValue());
      }
   }
}
