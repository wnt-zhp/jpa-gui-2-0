package cx.ath.jbzdak.jpaGui.ui.error;

import javax.swing.SwingUtilities;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Window;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-04-23
 */
public class ErrorDialogManager {
//   LOWPRIO Zrobić jakoś tak żeby keszować dialogi. . .
//   private final List<SoftReference<ErrorDialog>> errorDialogCache
//           = new ArrayList<SoftReference<ErrorDialog>>();
//
//   private final ReentrantLock cacheLock= new ReentrantLock();
//
//   private final PropertyChangeListener listener = new PropertyChangeListener() {
//      @Override
//      public void propertyChange(PropertyChangeEvent evt) {
//         if("visible".equals(evt.getPropertyName())){
//            if(!(Boolean)evt.getNewValue()){
//               cacheLock.lock();
//               try{
//                   errorDialogCache.add(new SoftReference(errorDialogCache));
//               }finally {
//                  cacheLock.unlock();
//               }
//            }
//         }
//      }
//   };
//
//   public ErrorDialog createErrorDialog(){
//      ErrorDialog errorDialog = new
//   }

   public static final ErrorDialog getDialogInstance(Component owner){
      Window window = SwingUtilities.windowForComponent(owner);
      Frame f = (window instanceof Frame)?(Frame)window:null;
      ErrorDialog ed = new ErrorDialog(f, true);
      return ed;
   }
}