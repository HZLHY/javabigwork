import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;

public class ModifyPwdFrame extends JFrame {// 修改已登录用户密码窗口
    private MySqlDataBase DBHelper;
    private Container ModifyPwdContainer;
    private String Id;
    private String Uname;
    private JPanel PwdPanel = new JPanel();
    private JPanel ButtonPanel = new JPanel();
    private final JLabel OldPwdLabel = new JLabel("旧密码",JLabel.CENTER);
    private final JLabel NewPwdLabel = new JLabel("新密码",JLabel.CENTER);
    private final JLabel ConfirmPwdLabel = new JLabel("确认新密码",JLabel.CENTER);
    private final JPasswordField OldPwdArea = new JPasswordField();
    private final JPasswordField NewPwdArea = new JPasswordField();
    private final JPasswordField ConfirmPwdArea = new JPasswordField();
    private final JButton ModifyButton = new JButton("更改");
    private final JButton ModifyCancel = new JButton("取消");
    public ModifyPwdFrame(String uname,String id){
        this.Uname = uname;
        this.Id = id;
        ModifyPwdContainer = getContentPane();
        ModifyPwdContainer.setLayout(new BorderLayout());
        setTitle("修改密码");
        setSize(300,200);
        setLocationRelativeTo(null);
        InitUIEvent();
        setVisible(true);
    }
    public void InitPwdPanel(){
        PwdPanel.setLayout(null);
        OldPwdLabel.setBounds(50, 20, 50, 20);
        NewPwdLabel.setBounds(50, 50, 50, 20);
        ConfirmPwdLabel.setBounds(40, 80, 60, 20);

        OldPwdArea.setBounds(110, 20, 120, 20);
        NewPwdArea.setBounds(110, 50, 120, 20);
        ConfirmPwdArea.setBounds(110, 80, 120, 20);

        PwdPanel.add(OldPwdLabel);
        PwdPanel.add(NewPwdLabel);
        PwdPanel.add(ConfirmPwdLabel);
        PwdPanel.add(OldPwdArea);
        PwdPanel.add(NewPwdArea);
        PwdPanel.add(ConfirmPwdArea);
        ModifyPwdContainer.add(PwdPanel,"Center");
    }
    public void InitButtonPanel(){
        ButtonPanel.setLayout(new GridLayout(1,2));
        ButtonPanel.add(ModifyButton);
        ButtonPanel.add(ModifyCancel);
        ModifyPwdContainer.add(ButtonPanel,"South");
    }
    public void InitUIEvent(){
        InitPwdPanel();
        InitButtonPanel();
        MD5Util myMd5 = new MD5Util();
        try {
            DBHelper = new MySqlDataBase();
        }catch (Exception e){
            e.printStackTrace();
        }
        // 更改密码
        ModifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String OldPwd = String.valueOf(OldPwdArea.getPassword());
                String NewPwd = String.valueOf(NewPwdArea.getPassword());
                String ConfirmPwd = String.valueOf(ConfirmPwdArea.getPassword());
                if(OldPwd.equals("")|NewPwd.equals("")|ConfirmPwd.equals("")){
                    JOptionPane.showMessageDialog(PwdPanel, "请确保输入完整信息！", "更改密码", JOptionPane.WARNING_MESSAGE);
                }else {
                    if(DBHelper.VerifyUser(Uname,OldPwd)==1){// 验证旧密码
                        if(NewPwd.length()>=10&&NewPwd.equals(ConfirmPwd)){
                            try {
                                if(DBHelper.updateUser("upwd",Integer.parseInt(Id),myMd5.getMD5(NewPwd))){
                                 JOptionPane.showMessageDialog(PwdPanel,"更改成功","更改密码",JOptionPane.INFORMATION_MESSAGE);
                                 dispose();
                                }
                            } catch (NoSuchAlgorithmException ex) {
                                ex.printStackTrace();
                            }
                        }else if(!NewPwd.equals(ConfirmPwd)){
                            JOptionPane.showMessageDialog(PwdPanel,"请输入相同的密码","更改密码",JOptionPane.WARNING_MESSAGE);
                        }else if(NewPwd.length()<10){
                            JOptionPane.showMessageDialog(PwdPanel,"密码不能少于10位！","更改密码",JOptionPane.WARNING_MESSAGE);
                        }
                    }else if(DBHelper.VerifyUser(Uname,OldPwd)==-2){
                        // 旧密码不正确
                        JOptionPane.showMessageDialog(PwdPanel,"密码不正确！","更改密码",JOptionPane.WARNING_MESSAGE);
                    }else if(DBHelper.VerifyUser(Uname,OldPwd)==-1){
                        // 用户不存在
                        JOptionPane.showMessageDialog(PwdPanel,"用户不存在！","更改密码",JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
        // 取消更改
        ModifyCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
