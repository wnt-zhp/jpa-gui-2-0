package cx.ath.jbzdak.jpaGui.db.lifecycleListenerPack;

import cx.ath.jbzdak.jpaGui.db.DBLifecyclePhase;
import cx.ath.jbzdak.jpaGui.db.DBManager;
import cx.ath.jbzdak.jpaGui.db.DefaultLifecycleListener;
import cx.ath.jbzdak.jpaGui.db.LifecycleAdministrator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-27
 */
public class AutoBackupPack<T extends DBManager, L extends LifecycleAdministrator> extends  DefaultLifecycleListenerPack<T, L> {

   private final static Logger LOGGER = LoggerFactory.getLogger(AutoBackupPack.class);

   private final DateFormat backupDateFormat
             = new SimpleDateFormat("yyyy-MM-dd");

   private final File bakcupFolder;
   
   public AutoBackupPack(File bakcupFolder) {
      super(new HashSet<String>(Arrays.asList("backup")), "auto-backup");
      this.bakcupFolder = bakcupFolder;
      if(bakcupFolder == null){
         throw new IllegalArgumentException("Backup folder passed is null");
      }
      if(!bakcupFolder.isDirectory()){
         throw new IllegalArgumentException("Backup folder passed is not a directory");
      }
      addListener(EnumSet.of(DBLifecyclePhase.PRE_BACKUP), new DefaultLifecycleListener<T,L>(100, "Set backup file name"){
         @Override
         public void executePhase(T manager, L administrator, Object... params) {
            if(administrator.getUserConfiguration().containsKey("backup-file")){
               return; //Means that file was set in step 0 from params
            }
            String backupType = null;
            if (params[0] instanceof String) {
               backupType = (String) params[0];
            }
            final Pattern today = Pattern.compile("" + backupDateFormat.format(new Date()) + "_(\\d)(:?_(.*))?.zip");
            String[] fileNames = AutoBackupPack.this.bakcupFolder.list();
            int maxId = -1;
            for(String name : fileNames){
               Matcher m = today.matcher(name);
               if(m.matches()){
                  try{
                     maxId = Math.max(maxId, Integer.parseInt(m.group(1)));
                  }catch (NumberFormatException e){
                     throw new IllegalStateException(e);
                  }
               }
            }
            File backFile = new File(AutoBackupPack.this.bakcupFolder, backupDateFormat.format(new Date()) + "_" + ++maxId + (backupType==null?"":("_"+backupType)));
            LOGGER.debug("File that next backup will be written to is {}", backFile);
            administrator.getUserConfiguration().put("backup-file", backFile);
         }
      });
      addListener(EnumSet.of(DBLifecyclePhase.POST_READ_BACKUP), new DefaultLifecycleListener<T,L>(100, "Set backup file name"){
         @Override
         public void executePhase(T manager, L administrator, Object... params) throws Exception {
            String[] fileNames = AutoBackupPack.this.bakcupFolder.list();
            Arrays.sort(fileNames);
            File file = new File(fileNames[fileNames.length -1]);
            LOGGER.debug("File from wchich backup will be read is {}", file);
            administrator.getUserConfiguration().put("read-backup-file", file);
         }
      });
    
   }
}
