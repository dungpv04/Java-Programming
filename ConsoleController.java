import java.io.IOException;
import java.util.Scanner;

public final class ConsoleController{

     public static void standBy(){
        System.out.println("Press any key to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public static void printGUI(){
        System.out.println("Choose object: ");
            System.out.println("1.Windows");
            System.out.println("2.Linux");
            System.out.println("3.Exit program");
            System.out.println("Your selection: ");
    }

    public static void printAction(){
        System.out.println("Select your action: ");
        System.out.println("1.Add");
        System.out.println("2.Print all members of list");
        System.out.println("3.Delete a member from list");
        System.out.println("4.Search for a member");
        System.out.println("5.Exit");
        System.out.println("Your action: ");
    }

    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // For Unix-like systems (Linux, macOS)
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}