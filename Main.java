import java.util.InputMismatchException;
import java.util.Scanner;

import enums.OSType;
import frame.ManagementApp;
import object.Linux;
import object.OSList;
import object.Windows;

public class Main{
    public static void main(String agrs[]){
        Scanner scanner = new Scanner(System.in);
        OSList osList = new OSList();
        String action = new String();
        OSType osType = null;
        int typeIndex = -1;
        Windows windows;
        Linux linux;

        ManagementApp mainFrame = new ManagementApp();
        
    }

}