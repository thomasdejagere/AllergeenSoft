package ErrorHandling;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Thomas
 */
public class ErrorHandling {
    public static void createStackTraceFile(StackTraceElement[] stackTrace){
        BufferedWriter writer = null;
        try {
            String path = "C:" + File.separator + "AllergeenSoft" + File.separator + "stacktrace.txt";
            File logFile = new File(path);
            
            writer = new BufferedWriter(new FileWriter(logFile));
            for(int i = 0; i < stackTrace.length; i++){
                writer.write(stackTrace[i].toString() + System.getProperty("line.separator"));
                writer.write("\n");
            }
        }catch(Exception ex){
            
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(ErrorHandling.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
}
