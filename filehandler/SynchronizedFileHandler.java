package filehandler;

import java.io.*;
import java.util.ArrayList;

import object.OS;

public class SynchronizedFileHandler {

    private String filePath;
    private ArrayList<OS> osList;

    public SynchronizedFileHandler(File file){
        filePath = file.getAbsolutePath();
        file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File does not exist. Creating and writing object...");
            ArrayList<OS> obj = new ArrayList<OS>();
            try (FileOutputStream fos = new FileOutputStream(file);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(obj);
                fos.close();
                System.out.println("Object has been written to the file.");

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File already exists. No need to write.");
        }
    }

    public synchronized Boolean writeObjectToFile(ArrayList<OS> osList) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(osList);
            oos.close();
            fos.close();
            System.out.println("Object has been written to file");
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public synchronized ArrayList<OS> readObjectFromFile(File file) {
        
        try (FileInputStream fis = new FileInputStream(file.getAbsolutePath());
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            osList = new ArrayList<OS>();
            osList = (ArrayList<OS>) ois.readObject();
            ois.close();
            fis.close();
            System.out.println("Object has been read from file");
            return osList;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
