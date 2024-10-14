package eventhandler;

import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import object.Linux;
import object.OS;
import object.OSList;
import object.Windows;

public class AddButtonListener implements ActionListener {
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

    public AddButtonListener(JTextField textFieldVersion, JTextField textFieldPublicDate,
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

        boolean isEmptyField = version.isEmpty() || publicDate.isEmpty() || language.isEmpty() || type.isEmpty() || packageManager.isEmpty();
        if(!isEmptyField){
            tableModel.addRow(new Object[]{os, version, publicDate, language, type, isGUI, packageManager});
            String message = String.format("OS: %s\nVersion: %s\nPublic Date: %s\nLanguage: %s\nType: %s\n" +
                                            "Is GUI: %b\nPackage Manager: %s",
                                            os, version, publicDate, language, type, isGUI, packageManager);
            JOptionPane.showMessageDialog(null, message, "Add Item", JOptionPane.INFORMATION_MESSAGE);

            if(os.equals("Windows")){
                windows = new Windows(language, type, version, publicDate);
                addToList(windows);
            }
            else{
                linux = new Linux(isGUI, packageManager, version, publicDate);
                addToList(linux);
            }

            clearFields();
        }
        else{
            String message = String.format("All fields mustn't be empty !");
            JOptionPane.showMessageDialog(null, message, "Empty Field", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void clearFields() {
        textFieldVersion.setText("");
        textFieldPublicDate.setText("");
        textFieldLanguage.setText("");
        textFieldType.setText("");
        textFieldPackageManager.setText("");
        checkBoxIsGUI.setSelected(false);
    }

    private void addToList(OS os){
        osList.add(os);
    }
}

