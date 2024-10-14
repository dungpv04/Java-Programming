package frame;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import object.Linux;
import object.NonEditableTableModel;
import object.OS;
import object.Windows;

public class SearchFrame {


    public SearchFrame(String[] columnNames, ArrayList<OS> osList) {

        // Create a table model with the column names and data
        DefaultTableModel tableModel = new NonEditableTableModel(columnNames, 0);
        
        for(OS os: osList){
            if(os.getClass().getSimpleName().equals("Windows")){
                Windows window = (Windows) os;
                tableModel.addRow(new Object[]{window.getVersion(), window.getPublicDate(), window.getLanguage(), window.getType()});
            }
            else{
                Linux linux = (Linux) os;
                tableModel.addRow(new Object[]{linux.getVersion(), linux.getPublicDate(), linux.getIsGUI(), linux.getPackageManager()});
            }
        }

        // Create the table with the model
        JTable table = new JTable(tableModel);
        
        // Make the table non-editable
        table.setEnabled(false);

        // Create a scroll pane and add the table to it
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a frame and add the scroll pane to it
        JFrame frame = new JFrame("Simple Table Example");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // This closes only the current frame
        frame.add(scrollPane); // Add scroll pane containing the table to the frame
        frame.setSize(800, 400); // Set the size of the frame
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true); // Make the frame visible
    }
}
