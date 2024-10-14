package filehandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SynchronizedTextFileHandler {
    public SynchronizedTextFileHandler(String filePath){
        File file = new File(filePath);
        if(!file.exists()){
            try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))){
                bufferedWriter.write("");
            }
            catch(IOException err){
                err.printStackTrace();
                System.out.println("Created text file");
            }
        }
        else{
            System.out.println("File has been already exist, no need to create !");
        }
    }

    public String readObjectFromFile(File file){
        StringBuilder content = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()))){
            String data;
            while ((data = reader.readLine()) != null) {
                content.append(data).append("\n");
            }
            return content.toString();
        }
        catch(IOException err){
            err.printStackTrace();
            return null;
        }
    
    }

    public Boolean writeObjectToFile(String content, String filePath){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            writer.write(content);
            return true;
        }
        catch(IOException err){
            err.printStackTrace();
            return false;
        }
    }
}
