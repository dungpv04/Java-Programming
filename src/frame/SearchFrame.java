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

        JTable table = new JTable(tableModel);
        
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);

        JFrame frame = new JFrame("Simple Table Example");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(scrollPane);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
