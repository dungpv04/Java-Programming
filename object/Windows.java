package object;

import java.io.Serializable;
import java.util.Scanner;
import enums.OSType;


public class Windows extends OS implements Serializable {
    private String language;
    private String type;
    private final String TYPE = "Windows";
    public Windows(){
        super();
        this.language = "N/A";
        this.type = "N/A";
        this.osType = OSType.Windows;
    }

    public Windows(String language, String type, String version, String publicDate){
        super(version, publicDate);
        this.language = language;
        this.type = type;
        this.osType = OSType.Windows;
    }

    public String getLanguage(){
        return this.language;
    }

    public String getType(){
        return this.type;
    }

    public void setLanguage(String language){
        this.language = language;
    }

    public void setType(String type){
        this.type = type;
    }

    @Override
    public void inputData() throws NullPointerException{
        super.inputData();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Windows language: ");
        this.language = scanner.nextLine();
        System.out.println("Enter type: ");
        this.type = scanner.nextLine();

        if(type == null || language == null || type.isEmpty() || language.isEmpty())
            throw new NullPointerException("Data can't be null or empty");

    }

    @Override
    public void displayData(){
        super.displayData();
        System.out.println("Language: " + this.language);
        System.out.println("Type: " + this.type);
    }

    @Override
    public String parseObjectToString(){
        return TYPE + "," + getVersion() + "," + getPublicDate() + "," + language + "," + type;
    }

}
