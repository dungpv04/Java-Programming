package eventhandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import object.OSList;

public class SaveFileListener implements ActionListener {
    private OSList osList;

    public SaveFileListener(OSList osList){
        this.osList = osList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        saveToFile();
    }

    private void saveToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text File(*.txt)", "txt"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Binary File(*.dat) ", "dat"));
        fileChooser.setAcceptAllFileFilterUsed(false);

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            if (fileToSave.exists()) {
                int response = JOptionPane.showConfirmDialog(null,
                        "The file already exists. Do you want to overwrite it?",
                        "Confirm Overwrite", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (response != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            osList.writeListToFile(fileToSave);

        }
    }
}
