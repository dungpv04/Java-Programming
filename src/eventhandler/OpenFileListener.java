package eventhandler;

import database.DBConnector;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import object.Linux;
import object.OS;
import object.OSList;
import object.Windows;

public class OpenFileListener implements ActionListener {
    private final DefaultTableModel tableModel;
    private OSList osList;
    private final DBConnector dbConnector;
    public OpenFileListener(DefaultTableModel tableModel, OSList osList) {
        this.tableModel = tableModel;
        this.osList = osList;
        dbConnector = new DBConnector();
    }

    /* 
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("Text and binary file", "txt", "dat");
        fileChooser.setFileFilter(fileNameExtensionFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            OSList newList = new OSList(selectedFile);
            osList.setList(newList.getList());
            tableModel.setRowCount(0);
            for (OS os : osList.getList()) {
                if(os instanceof Windows){
                    Windows windows = new Windows();
                    windows = (Windows) os;
                    tableModel.addRow(new Object[]{"Windows", windows.getVersion(), windows.getPublicDate(), windows.getLanguage(), windows.getType(), null, "N/A"});
                }
                else{
                    Linux linux = new Linux();
                    linux = (Linux) os;
                    tableModel.addRow(new Object[]{"Linux", linux.getVersion(), linux.getPublicDate(), "N/A", "N/A", linux.getIsGUI(), linux.getPackageManager()});
                }
            }
            tableModel.fireTableDataChanged();
        }
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
        osList = new OSList();
        osList.setList(dbConnector.selectAll());
        tableModel.setRowCount(0);
        for (OS os : osList.getList()) {
            if(os instanceof Windows){
                Windows windows = new Windows();
                windows = (Windows) os;
                tableModel.addRow(new Object[]{"Windows", windows.getVersion(), windows.getPublicDate(), windows.getLanguage(), windows.getType(), null, "N/A"});
            }
            else{
                Linux linux = new Linux();
                linux = (Linux) os;
                tableModel.addRow(new Object[]{"Linux", linux.getVersion(), linux.getPublicDate(), "N/A", "N/A", linux.getIsGUI(), linux.getPackageManager()});
            }
        }
        tableModel.fireTableDataChanged();
    }
}
