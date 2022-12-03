import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;

public class AddFrame extends JFrame { // 新增用户
    private MySqlDataBase DBHelper;
    private Container AddContainer;

    private final JLabel AddTitle = new JLabel("新增用户",JLabel.CENTER);
    private final JLabel UserNameLabel = new JLabel("用户名",JLabel.CENTER);
    private final JLabel UserMajorLabel =new JLabel("专业:",JLabel.CENTER);
    private final JLabel UserPwdLabel = new JLabel("密码:", JLabel.CENTER);
    private final JTextField UserNameArea = new JTextField();
    private final JPasswordField UserPwdArea = new JPasswordField();
    private final JTextField UserMajorArea = new JTextField();

    private final JButton AddButton = new JButton("增加");
    private final JButton AddCancel = new JButton("取消");

    private final JPanel AddInputPanel = new JPanel();
    private final JPanel AddButtonPanel = new JPanel();

    public AddFrame(){ // 增加用户信息窗口
        AddContainer = getContentPane();
        AddContainer.setLayout(new BorderLayout());
        setTitle("增加用户");
        setSize(300,200);
        setLocationRelativeTo(null);  // 默认居中显示
        InitUIEvent();
        setVisible(true);
    }
    public void InitAddInputPanel(){
        AddContainer.add(AddTitle,"North");
        AddInputPanel.setLayout(null);
        UserNameLabel.setBounds(50,20,50,20);
        UserMajorLabel.setBounds(50,50,50,20);
        UserPwdLabel.setBounds(50,80,50,20);
        UserNameArea.setBounds(110,20,120,20);
        UserMajorArea.setBounds(110,50,120,20);
        UserPwdArea.setBounds(110,80,120,20);
        AddInputPanel.add(UserNameLabel);
        AddInputPanel.add(UserMajorLabel);
        AddInputPanel.add(UserPwdLabel);
        AddInputPanel.add(UserNameArea);
        AddInputPanel.add(UserMajorArea);
        AddInputPanel.add(UserPwdArea);
        AddContainer.add(AddInputPanel,"Center");
    }
    public void InitAddButtonPanel(){
        AddButtonPanel.setLayout(new GridLayout(1,2));
        AddButtonPanel.add(AddButton);
        AddButtonPanel.add(AddCancel);
        AddContainer.add(AddButtonPanel,"South");
    }
    public void InitUIEvent(){
        InitAddInputPanel();
        InitAddButtonPanel();

        try {
            DBHelper = new MySqlDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        AddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Uname = UserNameArea.getText();
                String Major = UserMajorArea.getText();
                String Upwd = String.valueOf(UserPwdArea.getPassword());
                if (Uname.equals("")|Major.equals("")|Upwd.equals("")){
                    JOptionPane.showMessageDialog(AddInputPanel, "请检查你是否输入了完整信息！", "添加失败", JOptionPane.WARNING_MESSAGE);
                }else if (Upwd.length()>=10){
                    try {
                        if(DBHelper.insertUser(Uname,Upwd,Major)){
                            JOptionPane.showMessageDialog(AddInputPanel, "添加成功", "添加用户", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                        }else {
                            JOptionPane.showMessageDialog(AddInputPanel, "添加失败", "添加失败", JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (NoSuchAlgorithmException ex) {
                        ex.printStackTrace();
                    }
                }else {
                    JOptionPane.showMessageDialog(AddInputPanel, "密码不能少于10位！", "添加用户", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        AddCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
