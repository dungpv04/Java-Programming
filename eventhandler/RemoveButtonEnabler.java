package eventhandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import object.Linux;
import object.OSList;
import object.Windows;

public class RemoveButtonEnabler implements ListSelectionListener, ActionListener {
    private JButton btnRemove;
    private JTable table;
    private DefaultTableModel tableModel;
    private OSList osList;

    public RemoveButtonEnabler(JButton btnRemove, JTable table, DefaultTableModel tableModel, OSList osList) {
        this.btnRemove = btnRemove;
        this.table = table;
        this.tableModel = tableModel;
        this.osList = osList;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) { // Chỉ kích hoạt khi không còn thay đổi
            btnRemove.setEnabled(table.getSelectedRow() != -1); // Kích hoạt nút Remove nếu có hàng được chọn
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            String os = (String) tableModel.getValueAt(selectedRow, 0);
            String version = (String) tableModel.getValueAt(selectedRow, 1);
            String publicDate = (String) tableModel.getValueAt(selectedRow, 2);
            String language = (String) tableModel.getValueAt(selectedRow, 3);
            String type = (String) tableModel.getValueAt(selectedRow,4);
            Boolean isGUI = (Boolean) tableModel.getValueAt(selectedRow, 5);
            
            String packageManager = (String) tableModel.getValueAt(selectedRow, 6);

            // Confirm removal
            int confirmation = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to remove this entry?\nOS: " + os + "\nVersion: " + version + "\nPublic Date: " + publicDate,
                    "Confirm Removal", JOptionPane.YES_NO_OPTION);

            if (confirmation == JOptionPane.YES_OPTION) {
                tableModel.removeRow(selectedRow);
                tableModel.fireTableDataChanged();
                if(os.equals("Windows")){
                    Windows windows = new Windows(language, type, version, publicDate);
                    osList.deleteOne(windows);
                }
                else{
                    Linux linux = new Linux(isGUI, packageManager, version, publicDate);
                    osList.deleteOne(linux);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row to remove.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

}
