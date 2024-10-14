package object;
import java.util.Scanner;
import enums.OSType;

public class Linux extends OS {
    private boolean isGUI;
    private String packageManager;
    private final String TYPE = "Linux";

    public Linux(){
        super();
        this.isGUI = false;
        this.packageManager = "N/A";
        this.osType = OSType.Linux;
    }

    public Linux(boolean isGUI, String packageManager, String version, String publicDate){
        super(version, publicDate);
        this.isGUI = isGUI;
        this.packageManager = packageManager;
        this.osType = OSType.Linux;
    }

    public boolean getIsGUI(){
        return this.isGUI;
    }

    public String getPackageManager(){
        return this.packageManager;
    }

    public void setIsGUI(boolean isGUI){
        this.isGUI = isGUI;
    }

    public void setPackageManager(String packageManager){
        this.packageManager = packageManager;
    }

    @Override
    public void inputData() throws NullPointerException{
        super.inputData();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Is GUI(y/n) ?: ");
        String a = new String();
        a = scanner.nextLine();
        if(a == "y")
            this.isGUI = true;
        else 
            this.isGUI = false;
        System.out.println("Enter packageManager: ");
        this.packageManager = scanner.nextLine();
        if(packageManager == null || a == null || packageManager.isEmpty() || a.isEmpty())
            throw new NullPointerException("Data can't be null or empty");
    }

    @Override
    public void displayData(){
        super.displayData();
        System.out.println("Is GUI: " + this.isGUI);
        System.out.println("Package Manager: " + this.packageManager);
    }

    @Override
    public String parseObjectToString(){
        String isGUItoString = new String();
        if(isGUI)
            isGUItoString = "1";
        else isGUItoString = "0";
        return TYPE + "," + getVersion() + "," + getPublicDate() + "," + isGUItoString  + "," + packageManager;
    }
}
