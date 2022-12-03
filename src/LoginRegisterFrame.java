import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;

public class LoginRegisterFrame extends JFrame {  // 登录与注册窗口
    private MySqlDataBase DBHelper;
    private final Container LoginRegisterContainer; // 登录注册窗口容器
    // 这些写进函数太麻烦了。。。最好就是写在这里了感觉
    private final JLabel LoginTitle = new JLabel("登陆页面", JLabel.CENTER);
    private final JLabel LoginUnameLabel = new JLabel("用户名:", JLabel.CENTER);
    private final JLabel LoginPwdLabel = new JLabel("密码:", JLabel.CENTER);
    private final JLabel RegisterMajorLabel = new JLabel("专业:", JLabel.CENTER);
    private final JLabel RegisterTitle = new JLabel("注册页面", JLabel.CENTER);
    private final JTextField LoginUnameTextArea = new JTextField();
    private final JPasswordField LoginPwdPasswordArea = new JPasswordField();
    private final JTextField RegisterMajorTextArea = new JTextField();
    private final JPanel LoginInputPanel = new JPanel();
    private final JPanel RegisterInputPanel = new JPanel();
    private final JPanel ButtonPanel = new JPanel();
    private final JButton LoginButton = new JButton("登录");
    private final JButton RegisterButton = new JButton("注册");

    public LoginRegisterFrame() {
        LoginRegisterContainer = getContentPane();
        LoginRegisterContainer.setLayout(new BorderLayout());
        LoginRegisterContainer.setBounds(0, 0, 300, 200);
        setTitle("JDBC DataBase");
        setSize(300, 200);
        InitUIEvent();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 退出关闭整个程序
        setLocationRelativeTo(null);  // 居中显示
        setVisible(true);
    }

    // 向输入区域添加组件
    public void InitLoginInputPanel() {
        LoginRegisterContainer.add(LoginTitle, "North");
        LoginInputPanel.setLayout(null);
        LoginUnameLabel.setBounds(50, 20, 50, 20);
        LoginPwdLabel.setBounds(50, 60, 50, 20);
        LoginUnameTextArea.setBounds(110, 20, 120, 20);
        LoginPwdPasswordArea.setBounds(110, 60, 120, 20);
        LoginInputPanel.add(LoginUnameLabel);
        LoginInputPanel.add(LoginUnameTextArea);
        LoginInputPanel.add(LoginPwdLabel);
        LoginInputPanel.add(LoginPwdPasswordArea);
        LoginRegisterContainer.add(LoginInputPanel, "Center");
    }
    // 注册页面
    public void InitRegisterInputPanel() {
        LoginRegisterContainer.add(RegisterTitle, "North");
        RegisterInputPanel.setLayout(null);
        LoginUnameLabel.setBounds(50, 20, 50, 20);
        LoginPwdLabel.setBounds(50, 50, 50, 20);
        RegisterMajorLabel.setBounds(50, 80, 50, 20);
        LoginUnameTextArea.setBounds(110, 20, 120, 20);
        LoginPwdPasswordArea.setBounds(110, 50, 120, 20);
        RegisterMajorTextArea.setBounds(110, 80, 120, 20);
        RegisterInputPanel.add(LoginUnameLabel);
        RegisterInputPanel.add(LoginUnameTextArea);
        RegisterInputPanel.add(LoginPwdLabel);
        RegisterInputPanel.add(LoginPwdPasswordArea);
        RegisterInputPanel.add(RegisterMajorLabel);
        RegisterInputPanel.add(RegisterMajorTextArea);
        LoginRegisterContainer.add(RegisterInputPanel, "Center");
    }
    // 按钮区域
    public void InitButtonArea() {
        ButtonPanel.setLayout(new GridLayout(1, 2));
        ButtonPanel.add(LoginButton);
        ButtonPanel.add(RegisterButton);
        LoginRegisterContainer.add(ButtonPanel, "South");
    }

    public void InitUIEvent() {
        InitLoginInputPanel();  // 在输入区域添加组件
        InitButtonArea(); // 添加按钮

        try {
            DBHelper = new MySqlDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 登录按钮
        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 因为容器中的内容会变化，所以需要在点击后获取当前的组件
                JLabel currentTitle = (JLabel) LoginRegisterContainer.getComponent(0);
                if (currentTitle.getText().equals(LoginTitle.getText())) {
                    JPanel currentInputArea = (JPanel) LoginRegisterContainer.getComponent(1);
                    JTextField currentUname = (JTextField) currentInputArea.getComponent(1);
                    JPasswordField currentUPwd = (JPasswordField) currentInputArea.getComponent(3);
                    String Uname = currentUname.getText();
                    String Upwd = String.valueOf(currentUPwd.getPassword());
                    if (Uname.equals("") | Upwd.equals("")) {
                        JOptionPane.showMessageDialog(currentInputArea, "请检查你是否输入了信息！", "登录失败", JOptionPane.WARNING_MESSAGE);
                    } else {
                        int VerifyCode = DBHelper.VerifyUser(Uname, Upwd);
                        if (VerifyCode == 1) {
                            System.out.println("登录成功");
                            dispose();  // 关闭窗口
                            new UserManagerFrame(Uname);
                        } else if (VerifyCode == -1) {
                            JOptionPane.showMessageDialog(currentInputArea, "该用户不存在！", "登录失败", JOptionPane.WARNING_MESSAGE);
                        } else if (VerifyCode == -2) {
                            JOptionPane.showMessageDialog(currentInputArea, "密码不正确！", "登录失败", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } else {
                    // 变换容器中的内容
                    LoginRegisterContainer.removeAll();
                    InitLoginInputPanel();
                    InitButtonArea();
                    LoginRegisterContainer.validate();
                    LoginRegisterContainer.repaint();
                }
            }
        });
        // 注册按钮
        RegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JLabel currentTitle = (JLabel) LoginRegisterContainer.getComponent(0);
                if (currentTitle.getText().equals(RegisterTitle.getText())) {
                    JPanel currentInputArea = (JPanel) LoginRegisterContainer.getComponent(1);
                    JTextField currentUname = (JTextField) currentInputArea.getComponent(1);
                    JPasswordField currentUPwd = (JPasswordField) currentInputArea.getComponent(3);
                    JTextField currentMajor = (JTextField) currentInputArea.getComponent(5);
                    String Uname = currentUname.getText();
                    String Upwd = String.valueOf(currentUPwd.getPassword());
                    String Major = currentMajor.getText();
                    if(Uname.equals("")|Upwd.equals("")|Major.equals("")){
                        JOptionPane.showMessageDialog(currentInputArea, "请检查你是否输入了信息！", "登录失败", JOptionPane.WARNING_MESSAGE);
                    }else if(Upwd.length()>=10){
                        try {
                            if(DBHelper.insertUser(Uname,Upwd,Major)){
                                JOptionPane.showMessageDialog(currentInputArea, "注册成功！", "注册消息", JOptionPane.INFORMATION_MESSAGE);
                                // 注册成功后转到登录界面，重新刷新容器
                                LoginRegisterContainer.removeAll();
                                InitLoginInputPanel();
                                InitButtonArea();
                                LoginRegisterContainer.validate();
                                LoginRegisterContainer.repaint();
                            }else {
                                JOptionPane.showMessageDialog(currentInputArea, "注册失败！", "注册消息", JOptionPane.WARNING_MESSAGE);
                            }
                        }catch (NoSuchAlgorithmException ex){
                            ex.printStackTrace();
                        }
                    }else {
                        JOptionPane.showMessageDialog(currentInputArea, "密码不能少于10位！", "注册消息", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    LoginRegisterContainer.removeAll();
                    InitRegisterInputPanel();
                    InitButtonArea();
                    LoginRegisterContainer.validate();
                    LoginRegisterContainer.repaint();
                }
            }
        });
    }
}
