package eventhandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class RadioButtonHandler implements ActionListener {
    private JTextField textFieldLanguage;
    private JTextField textFieldType;
    private JCheckBox checkBoxIsGUI;
    private JTextField textFieldPackageManager;
    private JTextField textFieldOS;

    public RadioButtonHandler(JTextField textFieldLanguage, JTextField textFieldType, 
                            JCheckBox checkBoxIsGUI, JTextField textFieldPackageManager, JTextField textFieldOS) {
        this.textFieldLanguage = textFieldLanguage;
        this.textFieldType = textFieldType;
        this.checkBoxIsGUI = checkBoxIsGUI;
        this.textFieldPackageManager = textFieldPackageManager;
        this.textFieldOS = textFieldOS;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JRadioButton) {
            JRadioButton selectedRadio = (JRadioButton) e.getSource();

            if (selectedRadio.getText().equals("Windows")) {
                textFieldLanguage.setEnabled(true);
                textFieldType.setEnabled(true);
                
                checkBoxIsGUI.setEnabled(false);
                textFieldPackageManager.setEnabled(false);
                textFieldOS.setText("Windows");
            } else if (selectedRadio.getText().equals("Linux")) {
                checkBoxIsGUI.setEnabled(true);
                textFieldPackageManager.setEnabled(true);
                
                textFieldLanguage.setEnabled(false);
                textFieldType.setEnabled(false);
                textFieldOS.setText("Linux");
            }
        }
    }
}
