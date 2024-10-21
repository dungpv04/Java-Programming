package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import object.Linux;
import object.OS;
import object.OSList;
import object.Windows;

public class DBConnector {

    private final String url = "jdbc:sqlserver://localhost:1433;databaseName=OS;user=dungpv04;password=1;integratedSecurity=false;encrypt=false";
    private Statement statement;
    private Connection connection;
    public DBConnector(){

        try {
            // Load the SQL Server JDBC Driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Establish the connection
            connection = DriverManager.getConnection(url);

            // Create a statement object to execute queries
            statement = connection.createStatement();

        } catch (Exception e) {
            System.err.println(e);
        }
    }


    public ArrayList<OS> selectAll(){
        try {
            OSList osList = new OSList();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM OSList");
            while(resultSet.next()){
                if(resultSet.getString("os").equals("Windows")){
                    Windows windows = new Windows(
                        resultSet.getString("osLanguage"),
                        resultSet.getString("osType"),
                        resultSet.getString("osVersion"),
                        resultSet.getString("publicDate")
                    );
                    osList.add(windows);
                }
                else{
                    Linux linux = new Linux(
                        resultSet.getBoolean("isGUI"),
                        resultSet.getString("packageManager"),
                        resultSet.getString("osVersion"),
                        resultSet.getString("publicDate")
                    );
                    osList.add(linux);
                }
            }
            statement.close();
            resultSet.close();
            return osList.getList();
        } 
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insert(OS os) {
        String query = "INSERT INTO OSList (publicDate, osVersion, osType, osLanguage, os) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            
            pstmt.setString(1, os.getPublicDate());  // Date (assuming LocalDate for `publicDate`)
            pstmt.setString(2, os.getVersion());  // OS Version
            
            if (os instanceof Windows windows) {
                pstmt.setString(3, windows.getType());  // Windows-specific: OS Type
                pstmt.setString(4, windows.getLanguage());  // Windows-specific: OS Language
                pstmt.setString(5, "Windows");  // OS Type identifier
            } else if (os instanceof Linux linux) {
                pstmt.setString(3, linux.getIsGUI() ? "GUI" : "Non-GUI");  // Linux-specific: OS Type based on GUI presence
                pstmt.setString(4, linux.getPackageManager());  // Linux-specific: Package Manager
                pstmt.setString(5, "Linux");  // OS Type identifier
            }

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Record inserted successfully!");
            }

        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void delete(OS os) {
        try {
            if (os instanceof Windows windows) {
                // Deleting based on all Windows attributes
                String query = "DELETE FROM OSList WHERE osVersion = ? AND publicDate = ? AND osLanguage = ? AND osType = ? AND os = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                    pstmt.setString(1, windows.getVersion());
                    pstmt.setString(2, windows.getPublicDate());
                    pstmt.setString(3, windows.getLanguage());
                    pstmt.setString(4, windows.getType());
                    pstmt.setString(5, "Windows");

                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Windows record deleted successfully!");
                    } else {
                        System.out.println("No Windows record found with the given attributes.");
                    }
                }
            } else if (os instanceof Linux linux) {
                // Deleting based on all Linux attributes
                String query = "DELETE FROM OSList WHERE osVersion = ? AND publicDate = ? AND isGUI = ? AND packageManager = ? AND os = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                    pstmt.setString(1, linux.getVersion());
                    pstmt.setString(2, linux.getPublicDate());
                    pstmt.setBoolean(3, linux.getIsGUI());
                    pstmt.setString(4, linux.getPackageManager());
                    pstmt.setString(5, "Linux");

                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Linux record deleted successfully!");
                    } else {
                        System.out.println("No Linux record found with the given attributes.");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}