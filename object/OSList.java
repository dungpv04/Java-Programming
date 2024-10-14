package object;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import enums.OSType;
import filehandler.SynchronizedFileHandler;
import filehandler.SynchronizedTextFileHandler;

public class OSList implements Serializable {
    private ArrayList<OS> osList;
    private SynchronizedFileHandler synchronizedFileHandler;
    private SynchronizedTextFileHandler synchronizedTextFileHandler;
    private Thread writeThread;
    private Thread readThread;
    Boolean isWriteSuccessful = false;
    private Thread readTextThread;
    private Thread writeTextThread;
    private String textOutput;
    private String textInput;
    private File file;

    public OSList(File file){

        try{
            osList = new ArrayList<OS>();
            this.file = file;
            String fileExtension = getFileExtension(file);
            if(fileExtension.equals("txt")){
                synchronizedTextFileHandler = new SynchronizedTextFileHandler(file.getAbsolutePath());
                readTextThread = new Thread(() -> {
                    textInput = synchronizedTextFileHandler.readObjectFromFile(file);
                });
        
                readTextThread.start();
                readTextThread.join();
                deserializeObjectFromString();
            }

            else if(fileExtension.equals("dat")){
                synchronizedFileHandler = new SynchronizedFileHandler(file);
                readThread = new Thread(() ->{
                    osList = synchronizedFileHandler.readObjectFromFile(file);
                });
                readThread.start();
                readThread.join();
            }
        }
        catch(InterruptedException err){
            err.printStackTrace();
        }
        
    }

    public OSList(){
        osList = new ArrayList<OS>();
    }

    private void deserializeObjectFromString(){
        
        osList = new ArrayList<OS>();
        String [] splitedObject = textInput.split("\n");
        for (String objectStr : splitedObject) {
            String[] osStr = objectStr.split(",");
            if(osStr[0].equals("Windows")){
                Windows windows = new Windows();
                windows.setVersion(osStr[1]);
                windows.setPublicDate(osStr[2]);
                windows.setLanguage(osStr[3]);
                windows.setType(osStr[4]);
                osList.add(windows);
            }

            else if(osStr[0].equals("Linux")){
                Linux linux = new Linux();
                linux.setVersion(osStr[1]);
                linux.setPublicDate(osStr[2]);

                if(osStr[3].equals("1"))
                    linux.setIsGUI(true);
                else
                    linux.setIsGUI(false);
                
                linux.setPackageManager(osStr[4]);
                osList.add(linux);
            }
        }

    }

    public ArrayList<OS> getList() {
        if(osList == null)
            return null;
        return osList;
    }

    public void setList(ArrayList<OS> list){
        this.osList = list;
    }

    public void printAllList(OSType osType){
        for (OS os : osList) {
            if(os.osType == osType){
                os.displayData();
            }
        }
    }

    public ArrayList<OS> findAll(String language, String type, String version, String publicDate){
        ArrayList<OS> findList = new ArrayList<OS>();
        for (OS os : osList) {
           if(os instanceof Windows){
            Windows window = new Windows();
            window = (Windows) os;

            if(window.getLanguage().equals(language) || window.getType().equals(type) 
                || window.getVersion().equals(version) || window.getPublicDate().equals(publicDate)){
                findList.add(window);
            }
           }
        }
        return findList;
    }

    public ArrayList<OS> findAll(Boolean isGUI, String packageManager, String version, String publicDate){
        ArrayList<OS> findList = new ArrayList<OS>();
        for (OS os : osList) {
           if(os instanceof Linux){
            Linux linux = new Linux();
            linux = (Linux) os;

            if(linux.getIsGUI() == isGUI || linux.getPackageManager().equals(packageManager)
            || linux.getVersion().equals(version) || linux.getPublicDate().equals(publicDate)){
                findList.add(linux);
            }
           }
        }
        return findList;
    }

    public void deleteOne(OS os){
        osList.remove(os);
    }

    public void add(OS os){
        osList.add(os);
    }

    private void parseListToString(){
        textOutput = new String();
        textOutput = osList.get(0).parseObjectToString();
        for (int i = 1; i < osList.size(); i++) {
            textOutput += "\n" + osList.get(i).parseObjectToString();
        }
    }

    public void writeListToFile(File file){

        try{
            String fileExtension = getFileExtension(file);
            if(fileExtension.equals("txt")){
                parseListToString();
                writeTextThread = new Thread(() -> {
                    synchronizedTextFileHandler = new SynchronizedTextFileHandler(file.getAbsolutePath());
                    synchronizedTextFileHandler.writeObjectToFile(textOutput, file.getAbsolutePath());
                });
                writeTextThread.start();
                writeTextThread.join();
            }
            else{
                writeThread = new Thread(() ->{
                    synchronizedFileHandler = new SynchronizedFileHandler(file);
                    isWriteSuccessful = synchronizedFileHandler.writeObjectToFile(osList);
                });
                writeThread.start();
                writeThread.join();                
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        int lastIndexOfDot = fileName.lastIndexOf('.');

        // If there is no dot or it is at the beginning of the name (e.g., ".hiddenfile"), return empty string
        if (lastIndexOfDot == -1 || lastIndexOfDot == 0) {
            return ""; // No extension found
        }

        // Return the substring after the last dot
        return fileName.substring(lastIndexOfDot + 1);
    }

}
