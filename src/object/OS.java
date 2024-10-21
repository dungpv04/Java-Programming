package object;

import java.io.Serializable;
import java.util.Scanner;
import interfaces.IOS;
import enums.OSType;

public abstract class OS implements IOS, Serializable {

    private String version;
    private String publicDate;
    protected OSType osType;

    public OS(String version, String publicDate){
        this.version = version;
        this.publicDate = publicDate;
    }

    public OS(){
        this.version = "N/A";
        this.publicDate = "N/A";
    }

    public String getVersion(){
        return version;
    }

    public String getPublicDate(){
        return publicDate;
    }

    public void setVersion(String version){
        this.version = version;
    }

    public void setPublicDate(String publicDate){
        this.publicDate = publicDate;
    }

    public void displayData(){
        System.out.println("publicDate: " + publicDate);
        System.out.println("version: " + version);
    }

    public void inputData() throws NullPointerException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter publicDate: ");
        this.publicDate = scanner.nextLine();
        System.out.println("Enter version: ");
        this.version = scanner.nextLine();

        if(publicDate == null || version == null || publicDate.isEmpty() || version.isEmpty())
            throw new NullPointerException("Data can't be null or empty");
        
    }

    public String parseObjectToString(){
        return new String();
    }

}