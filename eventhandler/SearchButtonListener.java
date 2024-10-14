package eventhandler;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import frame.SearchFrame;
import object.Linux;
import object.OS;
import object.OSList;
import object.Windows;

public class SearchButtonListener implements ActionListener {
    private JTextField textFieldVersion;
    private JTextField textFieldPublicDate;
    private JTextField textFieldLanguage;
    private JTextField textFieldType;
    private JTextField textFieldPackageManager;
    private JCheckBox checkBoxIsGUI;
    private DefaultTableModel tableModel;
    private JRadioButton radioLinux;
    private JRadioButton radioWindows;
    private OSList osList;
    private Windows windows;
    private Linux linux;

    public SearchButtonListener(JTextField textFieldVersion, JTextField textFieldPublicDate,
                             JTextField textFieldLanguage, JTextField textFieldType, JTextField textFieldPackageManager,
                             JCheckBox checkBoxIsGUI, DefaultTableModel tableModel, JRadioButton radioLinux, JRadioButton radioWindows,
                             OSList osList) {
        this.textFieldVersion = textFieldVersion;
        this.textFieldPublicDate = textFieldPublicDate;
        this.textFieldLanguage = textFieldLanguage;
        this.textFieldType = textFieldType;
        this.textFieldPackageManager = textFieldPackageManager;
        this.checkBoxIsGUI = checkBoxIsGUI;
        this.tableModel = tableModel;
        this.radioLinux = radioLinux;
        this.radioWindows = radioWindows;
        this.osList = osList;
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        String os = new String();
        if(radioWindows.isSelected()){
            os = "Windows";
        }
        else{
            os = "Linux";
        }
        String version = textFieldVersion.getText().trim();
        String publicDate = textFieldPublicDate.getText().trim();
        String language = textFieldLanguage.isEnabled() ? textFieldLanguage.getText().trim() : "N/A";
        String type = textFieldType.isEnabled() ? textFieldType.getText().trim() : "N/A";
        String packageManager = textFieldPackageManager.isEnabled() ? textFieldPackageManager.getText().trim() : "N/A";
        Boolean isGUI = null;
        if(radioLinux.isSelected())
            isGUI = checkBoxIsGUI.isSelected();
       
        if(os.equals("Windows")){
            String[] columnNames = {"Version", "Public Date", "Language", "Type"};
            ArrayList<OS> findList = findAll(language, type, version, publicDate);
            SearchFrame searchFrame = new SearchFrame(columnNames, findList);
        }
        else{
            String[] columnNames = {"Version", "Public Date", "Is GUI", "Package Manager"};
            ArrayList<OS> findList = findAll(isGUI, packageManager, version, publicDate);
            SearchFrame searchFrame = new SearchFrame(columnNames, findList);
        }

    }

    private ArrayList<OS> findAll(String language, String type, String version, String publicDate){
        return osList.findAll(language, type, version, publicDate);
    }

    private ArrayList<OS> findAll(Boolean isGUI, String packageManager, String version, String publicDate){
        return osList.findAll(isGUI, packageManager, version, publicDate);
    }
}

