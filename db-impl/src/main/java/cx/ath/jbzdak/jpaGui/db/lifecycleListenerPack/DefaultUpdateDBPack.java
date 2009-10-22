package cx.ath.jbzdak.jpaGui.db.lifecycleListenerPack;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-10-22
 */
public class DefaultUpdateDBPack extends UpdateDBPack{

   private static List<Integer> splitAndParse(String versions){
      List<Integer> result = new ArrayList<Integer>();
      for(String s : versions.split(";")){
         s = s.trim();
         if(!s.isEmpty()){
            result.add(Integer.valueOf(s));
         }
      }
      return result;
   }

   private final String resourcePath;

   private final String filePrefix;

   private final String fileSuffix;

   public DefaultUpdateDBPack(String versions, String resourcePath) {
      this(versions, resourcePath, "", ".sql");
   }

   public DefaultUpdateDBPack(String versions, String resourcePath, String filePrefix, String fileSuffix) {
      super(splitAndParse(versions));
      this.resourcePath = resourcePath;
      this.filePrefix = filePrefix;
      this.fileSuffix = fileSuffix;
   }

   @Override
   Reader getScriptUpdatingTo(Integer version) {
      String resourceName = resourcePath + "/" + filePrefix + version + fileSuffix;
      return new InputStreamReader(getClass().getResourceAsStream(resourceName), Charset.forName("UTF-8"));
   }
}
