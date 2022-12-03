import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyFrame extends JFrame {// 管理用户修改信息窗口
    private MySqlDataBase DBHelper;
    private Container ModifyContainer;
    private String id;
    private String uname;
    private String major;
    private MD5Util myMd5 = new MD5Util();
    private final JLabel UserIdLabel = new JLabel("ID:", JLabel.CENTER);
    private final JLabel UserPwdLabel = new JLabel("密码:", JLabel.CENTER);
    private final JLabel UserNameLabel = new JLabel("用户名:", JLabel.CENTER);
    private final JLabel UserMajorLabel = new JLabel("专业:", JLabel.CENTER);
    // 以下是菜单传进来然后初始化的内容
    private final JPanel ModifyPanel = new JPanel();
    private final JTextField UserIdArea = new JTextField();
    private final JTextField UserNameArea = new JTextField();
    private final JPasswordField UserPwdArea = new JPasswordField();
    private final JTextField UserMajorArea = new JTextField();

    private final JPanel ButtonPanel = new JPanel();
    private final JButton UserChangeButton = new JButton("更改");
    private final JButton UserChangeCancel = new JButton("取消");

    public ModifyFrame(String id, String uname, String major) {
        this.id = id;
        this.uname = uname;
        this.major = major;
        ModifyContainer = getContentPane();
        ModifyContainer.setLayout(new BorderLayout());
        InitUIEvent();
        setTitle("修改信息");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void InitModifyPanel() {
        ModifyPanel.setLayout(null);
        UserIdLabel.setBounds(50, 20, 50, 20);
        UserNameLabel.setBounds(50, 50, 50, 20);
        UserPwdLabel.setBounds(50, 80, 50, 20);
        UserMajorLabel.setBounds(50, 110, 50, 20);

        UserIdArea.setBounds(110, 20, 120, 20);
        UserNameArea.setBounds(110, 50, 120, 20);
        UserPwdArea.setBounds(110, 80, 120, 20);
        UserMajorArea.setBounds(110, 110, 120, 20);

        ModifyPanel.add(UserIdLabel);
        ModifyPanel.add(UserNameLabel);
        ModifyPanel.add(UserPwdLabel);
        ModifyPanel.add(UserMajorLabel);

        ModifyPanel.add(UserIdArea);
        ModifyPanel.add(UserNameArea);
        ModifyPanel.add(UserPwdArea);
        ModifyPanel.add(UserMajorArea);

        UserIdArea.setText(this.id);
        UserNameArea.setText(this.uname);
        UserMajorArea.setText(this.major);
        ModifyContainer.add(ModifyPanel, "Center");
    }

    public void InitButtonPanel() {
        ButtonPanel.setLayout(new GridLayout(1, 2));
        ButtonPanel.add(UserChangeButton);
        ButtonPanel.add(UserChangeCancel);
        ModifyContainer.add(ButtonPanel, "South");
    }

    public void InitUIEvent() {
        InitModifyPanel();
        InitButtonPanel();
        try {
            DBHelper = new MySqlDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 更改用户信息
        UserChangeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean UpdateFinish1 = false;
                boolean UpdateFinish2 = true;
                boolean UpdateFinish3 = false;
                if (!UserNameArea.getText().equals("")) {
                    UpdateFinish1 = DBHelper.updateUser("uname", Integer.parseInt(id), UserNameArea.getText());
                }
                if (!String.valueOf(UserPwdArea.getPassword()).equals("")) {
                    if(String.valueOf(UserPwdArea.getPassword()).length()>=10){
                        try {
                            UpdateFinish2 = DBHelper.updateUser("upwd", Integer.parseInt(id), myMd5.getMD5(String.valueOf(UserPwdArea.getPassword())));
                        } catch (Exception eU) {
                            eU.printStackTrace();
                        }
                    }else {
                        UpdateFinish2 = false;
                        JOptionPane.showMessageDialog(ModifyPanel, "密码不能少于10位！", "更改反馈", JOptionPane.WARNING_MESSAGE);
                    }
                }
                if (!UserMajorArea.getText().equals("")) {
                    UpdateFinish3= DBHelper.updateUser("major", Integer.parseInt(id), UserMajorArea.getText());
                }
                if(UpdateFinish1&UpdateFinish2&UpdateFinish3){
                    JOptionPane.showMessageDialog(ModifyPanel, "更改成功！", "更改反馈", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            }
        });
        UserChangeCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
