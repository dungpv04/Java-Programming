package frame;

import eventhandler.AddButtonListener;
import eventhandler.OpenFileListener;
import eventhandler.RadioButtonHandler;
import eventhandler.RemoveButtonEnabler;
import eventhandler.SaveFileListener;
import eventhandler.SearchButtonListener;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import object.NonEditableTableModel;
import object.OSList;

public class ManagementApp extends JFrame {

    private JTextField textFieldOS;
    private JTextField textFieldVersion;
    private JTextField textFieldPublicDate;
    private JTextField textFieldLanguage;
    private JTextField textFieldType;
    private JCheckBox checkBoxIsGUI;
    private JTextField textFieldPackageManager;
    private JButton btnAdd, btnRemove, btnSearch, btnOpenFile, btnSaveFile;
    private JTable table;
    private DefaultTableModel tableModel;
    private JRadioButton radioWindows;
    private JRadioButton radioLinux;
    private OSList osList;

    public ManagementApp() {
        setTitle("Object Management System");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        osList = new OSList();

        JPanel inputPanel = new JPanel(new GridLayout(7, 2));

        inputPanel.add(new JLabel("OS:"));
        textFieldOS = new JTextField();
        textFieldOS.setEnabled(false);
        textFieldOS.setText("Windows");
        inputPanel.add(textFieldOS);

        inputPanel.add(new JLabel("Version:"));
        textFieldVersion = new JTextField();
        inputPanel.add(textFieldVersion);

        inputPanel.add(new JLabel("Public Date:"));
        textFieldPublicDate = new JTextField();
        inputPanel.add(textFieldPublicDate);

        inputPanel.add(new JLabel("Language:"));
        textFieldLanguage = new JTextField();
        inputPanel.add(textFieldLanguage);

        inputPanel.add(new JLabel("Type:"));
        textFieldType = new JTextField();
        inputPanel.add(textFieldType);

        inputPanel.add(new JLabel("Is GUI:"));
        checkBoxIsGUI = new JCheckBox();
        checkBoxIsGUI.setEnabled(false);
        inputPanel.add(checkBoxIsGUI);

        inputPanel.add(new JLabel("Package Manager:"));
        textFieldPackageManager = new JTextField();
        textFieldPackageManager.setEnabled(false);
        inputPanel.add(textFieldPackageManager);

        JPanel radioPanel = new JPanel();
        radioWindows = new JRadioButton("Windows");
        radioLinux = new JRadioButton("Linux");
        radioWindows.setSelected(true);

        ButtonGroup osGroup = new ButtonGroup();
        osGroup.add(radioWindows);
        osGroup.add(radioLinux);

        radioPanel.add(radioWindows);
        radioPanel.add(radioLinux);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnAdd = new JButton("Add");
        btnRemove = new JButton("Remove");
        btnRemove.setEnabled(false);
        btnSearch = new JButton("Search");
        btnOpenFile = new JButton("Load Database");
        btnSaveFile = new JButton("Save to File");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnRemove);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnOpenFile);
        buttonPanel.add(btnSaveFile);

        String[] columnNames = {"OS", "Version", "Public Date", "Language", "Type", "Is GUI", "Package Manager"};
        tableModel = new NonEditableTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.add(inputPanel);
        mainPanel.add(radioPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(scrollPane);

        add(mainPanel);

        initListener();

        setSize(600, 400);
        setVisible(true);
    }

    private void initListener(){
        RemoveButtonEnabler removeButtonEnabler = new RemoveButtonEnabler(btnRemove, table, tableModel, osList);
        table.getSelectionModel().addListSelectionListener(removeButtonEnabler);

        RadioButtonHandler osSelectionListener = new RadioButtonHandler(textFieldLanguage, textFieldType, 
                                                                        checkBoxIsGUI, textFieldPackageManager, textFieldOS);
        radioWindows.addActionListener(osSelectionListener);
        radioLinux.addActionListener(osSelectionListener);

        AddButtonListener addButtonListener = new AddButtonListener(textFieldVersion, textFieldPublicDate, 
                                                                    textFieldLanguage, textFieldType, textFieldPackageManager, 
                                                                    checkBoxIsGUI, tableModel, radioLinux, radioWindows, osList);
        btnAdd.addActionListener(addButtonListener);
        btnRemove.addActionListener(removeButtonEnabler);

        SearchButtonListener searchButtonListener = new SearchButtonListener(textFieldVersion, textFieldPublicDate, textFieldLanguage, textFieldType, textFieldPackageManager, checkBoxIsGUI, tableModel, radioLinux, radioWindows, osList);
        btnSearch.addActionListener(searchButtonListener);

        OpenFileListener openFileListener = new OpenFileListener(tableModel, osList);
        btnOpenFile.addActionListener(openFileListener);

        SaveFileListener saveFileListener = new SaveFileListener(osList);
        btnSaveFile.addActionListener(saveFileListener);

    }
}