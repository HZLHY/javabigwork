import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class UserManagerFrame extends JFrame {
    private MySqlDataBase DBHelper;
    private String Uname;  // 根据登录的用户名设置
    private String Id;
    private String Major;
    private Container UserManagerContainer;

    private final JTabbedPane UserTab = new JTabbedPane();
    private final JPanel UserInfoPanel = new JPanel();
    private final JPanel UserManagerPanel = new JPanel();
    private JLabel UserInfoTitle;
    private final JLabel UserIdLabel = new JLabel("ID:", JLabel.CENTER);
    private final JLabel UserNameLabel = new JLabel("用户名:", JLabel.CENTER);
    private final JLabel UserMajorLabel = new JLabel("专业:", JLabel.CENTER);
    private final JLabel UserIdLabelR = new JLabel();
    private final JLabel UserNameLabelR = new JLabel();
    private final JLabel UserMajorLabelR = new JLabel();
    private final JButton UserLogOut = new JButton("退出登录");
    private final JButton UserModifyPwd = new JButton("修改密码");

    private JTable UserMangerTable;
    private JLabel QueryTypeLabel = new JLabel("查询选项:", JLabel.CENTER);
    private JLabel QueryAreaLabel = new JLabel("内容:", JLabel.CENTER);
    private final JLabel SavePathLabel = new JLabel("保存路径:", JLabel.CENTER);
    private final JComboBox UserManagerComboBox = new JComboBox();  // 查询类型选项
    private final JComboBox SaveFileTypeComboBox = new JComboBox(); // 保存文件类型
    private final JTextField QueryDataArea = new JTextField();
    private final JTextField SavePathArea = new JTextField();
    private final JButton AccurateQueryButton = new JButton("精确查询");
    private final JButton FuzzyQueryButton = new JButton("模糊查询");
    private final JButton QueryAllUserButton = new JButton("查询全部");
    private final JButton AddNewUserButton = new JButton("新增用户");
    private final JButton SaveButton = new JButton("导出用户信息");
    private final String[] columnNames = {"ID", "用户名", "专业"};
    private JScrollPane UserManagerScrollPane;

    public UserManagerFrame(String uname) {
        try {
            DBHelper = new MySqlDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.Uname = uname;
        getUserInfo(Uname);  // 获取要显示的个人信息
        UserManagerContainer = getContentPane();
        UserManagerContainer.setLayout(new BorderLayout());
        setTitle("用户管理");
        setSize(800, 500);
        InitUserInfo();
        InitUserManager();
        InitTab();
        UserManagerContainer.add(UserTab, "Center");
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void InitTab() {
        UserTab.add("个人信息", UserInfoPanel);
        UserTab.add("管理用户", UserManagerPanel);
    }

    public void InitUserInfo() { // 个人信息面板

        UserInfoPanel.setLayout(null);
        UserInfoTitle = new JLabel("Welcome！" + Uname, JLabel.CENTER);

        UserInfoTitle.setBounds(330, 20, 120, 50);
        UserIdLabel.setBounds(300, 100, 50, 20);
        UserNameLabel.setBounds(300, 150, 50, 20);
        UserMajorLabel.setBounds(300, 200, 50, 20);
        UserIdLabelR.setBounds(380, 100, 120, 20);
        UserNameLabelR.setBounds(380, 150, 120, 20);
        UserMajorLabelR.setBounds(380, 200, 120, 20);

        UserModifyPwd.setBounds(250, 300, 85, 20);
        UserLogOut.setBounds(465, 300, 85, 20);

        UserIdLabelR.setText(Id);
        UserNameLabelR.setText(Uname);
        UserMajorLabelR.setText(Major);
        UserInfoPanel.add(UserInfoTitle);
        UserInfoPanel.add(UserIdLabel);
        UserInfoPanel.add(UserIdLabelR);
        UserInfoPanel.add(UserNameLabel);
        UserInfoPanel.add(UserNameLabelR);
        UserInfoPanel.add(UserMajorLabel);
        UserInfoPanel.add(UserMajorLabelR);
        UserInfoPanel.add(UserModifyPwd);
        UserInfoPanel.add(UserLogOut);

        // 修改密码
        UserModifyPwd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ModifyPwdFrame(Uname, Id);
            }
        });
        // 推出登录
        UserLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginRegisterFrame();
            }
        });
    }

    public void InitUserManager() {  // 管理用户面板
        UserManagerPanel.setLayout(null);
        UserManagerComboBox.addItem("ID");
        UserManagerComboBox.addItem("用户名");
        UserManagerComboBox.addItem("专业");
        SaveFileTypeComboBox.addItem("txt");
        SaveFileTypeComboBox.addItem("xls");

        QueryTypeLabel.setBounds(0, 0, 60, 20);
        UserManagerComboBox.setBounds(70, 0, 70, 20);
        QueryAreaLabel.setBounds(145, 0, 30, 20);
        QueryDataArea.setBounds(180, 0, 150, 20);
        AccurateQueryButton.setBounds(350, 0, 85, 20);
        FuzzyQueryButton.setBounds(460, 0, 85, 20);
        QueryAllUserButton.setBounds(570, 0, 85, 20);
        AddNewUserButton.setBounds(680, 0, 85, 20);

        SaveFileTypeComboBox.setBounds(150, 380, 50, 20);
        SavePathLabel.setBounds(200, 380, 60, 20);
        SavePathArea.setBounds(270, 380, 210, 20);
        SaveButton.setBounds(490, 380, 120, 20);
        UserManagerPanel.add(QueryTypeLabel);
        UserManagerPanel.add(UserManagerComboBox);
        UserManagerPanel.add(QueryAreaLabel);
        UserManagerPanel.add(QueryDataArea);
        UserManagerPanel.add(AccurateQueryButton);
        UserManagerPanel.add(FuzzyQueryButton);
        UserManagerPanel.add(QueryAllUserButton);
        UserManagerPanel.add(AddNewUserButton);
        UserManagerPanel.add(SaveFileTypeComboBox);
        UserManagerPanel.add(SavePathLabel);
        UserManagerPanel.add(SavePathArea);
        UserManagerPanel.add(SaveButton);

        AccurateQueryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!QueryDataArea.getText().equals("")) {
                    String ComboCheck = (String) UserManagerComboBox.getSelectedItem();
                    String ComboCheckSelect = getQueryType(ComboCheck);
                    if (UserManagerScrollPane != null) {
                        UserManagerPanel.remove(UserManagerScrollPane);
                    }
                    ArrayList<String[]> QueryResult = DBHelper.AccurateQuery(ComboCheckSelect, QueryDataArea.getText());
                    String[][] TableData = QueryResult.toArray(new String[QueryResult.size()][]);
                    DefaultTableModel tableModel = new DefaultTableModel(TableData, columnNames);
                    UserMangerTable = new JTable(tableModel);  // 新建JTable表格
                    // 增加表格鼠标右键菜单
                    UserMangerTable.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            super.mouseClicked(e);
                            if (e.getButton() == MouseEvent.BUTTON3) {
                                int focusedRowIndex = UserMangerTable.rowAtPoint(e.getPoint());
                                if (focusedRowIndex != -1) {
                                    UserMangerTable.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
                                    String TableId = (String) UserMangerTable.getValueAt(focusedRowIndex, 0);
                                    String TableName = (String) UserMangerTable.getValueAt(focusedRowIndex, 1);
                                    String TableMajor = (String) UserMangerTable.getValueAt(focusedRowIndex, 2);
                                    myPopupMenu popupMenu = new myPopupMenu(TableId, TableName, TableMajor);
                                    popupMenu.show(UserMangerTable, e.getX(), e.getY());
                                }
                            }
                        }
                    });
                    // 刷新表格
                    UserManagerScrollPane = new JScrollPane(UserMangerTable);
                    UserManagerScrollPane.setBounds(140, 50, 500, 300);
                    UserManagerPanel.add(UserManagerScrollPane);
                    UserManagerPanel.validate();
                    UserManagerPanel.repaint();
                }
            }
        });

        FuzzyQueryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!QueryDataArea.equals("")) {
                    String ComboCheck = (String) UserManagerComboBox.getSelectedItem();
                    String ComboCheckSelect = getQueryType(ComboCheck);
                    if (UserManagerScrollPane != null) {
                        UserManagerPanel.remove(UserManagerScrollPane);
                    }
                    ArrayList<String[]> QueryResult = DBHelper.FuzzyQuery(ComboCheckSelect, QueryDataArea.getText());
                    String[][] TableData = QueryResult.toArray(new String[QueryResult.size()][]);
                    DefaultTableModel tableModel = new DefaultTableModel(TableData, columnNames);
                    UserMangerTable = new JTable(tableModel);
                    UserMangerTable.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) { // 添加表格右键点击菜单事件
                            super.mouseClicked(e);
                            if (e.getButton() == MouseEvent.BUTTON3) {
                                int focusedRowIndex = UserMangerTable.rowAtPoint(e.getPoint());
                                if (focusedRowIndex != -1) {
                                    UserMangerTable.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
                                    String TableId = (String) UserMangerTable.getValueAt(focusedRowIndex, 0);
                                    String TableName = (String) UserMangerTable.getValueAt(focusedRowIndex, 1);
                                    String TableMajor = (String) UserMangerTable.getValueAt(focusedRowIndex, 2);
                                    myPopupMenu popupMenu = new myPopupMenu(TableId, TableName, TableMajor);
                                    popupMenu.show(UserMangerTable, e.getX(), e.getY());
                                }
                            }
                        }
                    });
                    UserManagerScrollPane = new JScrollPane(UserMangerTable);
                    UserManagerScrollPane.setBounds(140, 50, 500, 300);
                    UserManagerPanel.add(UserManagerScrollPane);
                    UserManagerPanel.validate();
                    UserManagerPanel.repaint();
                }
            }
        });

        AddNewUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddFrame();
            }
        });

        QueryAllUserButton.addActionListener(new ActionListener() { // 查询所有数据
            @Override
            public void actionPerformed(ActionEvent e) {
                if (UserManagerScrollPane != null) {
                    UserManagerPanel.remove(UserManagerScrollPane);
                }
                ArrayList<String[]> QueryResult = DBHelper.QueryAll();
                String[][] TableData = QueryResult.toArray(new String[QueryResult.size()][]);
                DefaultTableModel tableModel = new DefaultTableModel(TableData, columnNames);
                UserMangerTable = new JTable(tableModel);
                UserMangerTable.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) { // 添加表格右键点击菜单事件
                        super.mouseClicked(e);
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            int focusedRowIndex = UserMangerTable.rowAtPoint(e.getPoint());
                            if (focusedRowIndex != -1) {
                                UserMangerTable.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
                                String TableId = (String) UserMangerTable.getValueAt(focusedRowIndex, 0);
                                String TableName = (String) UserMangerTable.getValueAt(focusedRowIndex, 1);
                                String TableMajor = (String) UserMangerTable.getValueAt(focusedRowIndex, 2);
                                myPopupMenu popupMenu = new myPopupMenu(TableId, TableName, TableMajor);
                                popupMenu.show(UserMangerTable, e.getX(), e.getY());
                            }
                        }
                    }
                });
                UserManagerScrollPane = new JScrollPane(UserMangerTable);
                UserManagerScrollPane.setBounds(140, 50, 500, 300);
                UserManagerPanel.add(UserManagerScrollPane);
                UserManagerPanel.validate();
                UserManagerPanel.repaint();
            }
        });

        // 导出文件事件
        SaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String[]> queryList = DBHelper.QueryAllToFile();
                String savePathText = SavePathArea.getText();
                String outPutType = (String) SaveFileTypeComboBox.getSelectedItem();
                if (!savePathText.equals("")) {
                    try {
                        if (outPutType.equals("txt")) {
                            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(savePathText + ".txt"));
                            bufferedWriter.write("id,uname,upwd,major\n");
                            if (queryList != null) {
                                for (int i = 0; i < queryList.size(); i++) {
                                    String id = queryList.get(i)[0];
                                    String uname = queryList.get(i)[1];
                                    String upwd = queryList.get(i)[2];
                                    String major = queryList.get(i)[3];
                                    bufferedWriter.write(id + "," + uname + "," + upwd + "," + major + "\n");
                                }
                                bufferedWriter.close();
                                JOptionPane.showMessageDialog(UserManagerPanel,"导出成功","导出文件",JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {
                            FileWriter fileWriter = new FileWriter(savePathText + ".xls");
                            String[] columnName = {"id", "uname", "upwd", "major"};
                            for (int i = 0; i < columnName.length; i++) {
                                fileWriter.write(columnName[i] + "\t");
                            }
                            fileWriter.write("\n");
                            if (queryList != null) {
                                for (int j = 0; j < queryList.size(); j++) {
                                    for (int k = 0; k < queryList.get(j).length; k++) {
                                        fileWriter.write(queryList.get(j)[k] + "\t");
                                        System.out.println(queryList.get(j)[k]);
                                    }
                                    fileWriter.write("\n");
                                }
                                JOptionPane.showMessageDialog(UserManagerPanel,"导出成功","导出文件",JOptionPane.INFORMATION_MESSAGE);
                            }
                            fileWriter.close();
                        }

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(UserManagerPanel, "请输入路径!", "导出失败", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }
    // 获取查询类型
    public String getQueryType(String ComboCheck) {
        if (ComboCheck.equals("ID")) {
            return "id";
        } else if (ComboCheck.equals("用户名")) {
            return "uname";
        } else {
            return "major";
        }
    }
    // 获取已登录用户信息
    public void getUserInfo(String uname) {
        ArrayList<String[]> UserInfoList = DBHelper.AccurateQuery("uname", uname);
        this.Id = UserInfoList.get(0)[0];
        this.Major = UserInfoList.get(0)[2];
    }
}
