package cx.ath.jbzdak.jpaGui.utils;

import cx.ath.jbzdak.jpaGui.Utils;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Nie robi nic ciekawego poza tym że enkapsuluje zapis/odczyt do i z pliku.
 * Łatwiej tak nie przegapić zamknięcia czegokolwiek. 
 *
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public final class  FileCall {

   private static final Logger LOGGER = Utils.makeLogger();

   private FileCall() { }

   public interface FileRead{
      void read(FileInputStream fileInputStream) throws IOException;
   }

   public interface FileWrite{
      void write(FileOutputStream fileOutputStream) throws IOException;
   }


   public static void read(File file, FileRead read) throws IOException{
      FileInputStream fileInputStream = new FileInputStream(file);
      try{
         read.read(fileInputStream);
      }finally {
         try{
            fileInputStream.close();
         } catch (IOException e){
            LOGGER.warn("Exception while closing stream", e);
         }
      }
   }

   public static void write(File file, FileWrite write)throws IOException{
      FileOutputStream fileOutputStream = new FileOutputStream(file);
      try{
         write.write(fileOutputStream);
      }finally {
         try{
            fileOutputStream.close();
         } catch (IOException e){
            LOGGER.warn("Exception while closing stream", e);
         }
      }
   }


}

