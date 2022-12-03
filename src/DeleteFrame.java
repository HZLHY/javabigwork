import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteFrame extends JFrame {// 删除用户信息窗口
    private MySqlDataBase DBHelper;
    private Container DeleteContainer;
    private String id;
    private String uname;
    private String major;

    private final JLabel UserIdLabel =new JLabel("ID:",JLabel.CENTER);
    private final JLabel UserNameLabel =new JLabel("用户名:",JLabel.CENTER);
    private final JLabel UserMajorLabel =new JLabel("专业:",JLabel.CENTER);
    private final JLabel UserIdLabelR =new JLabel();
    private final JLabel UserNameLabelR =new JLabel();
    private final JLabel UserMajorLabelR =new JLabel();

    private final JButton DeleteButton =new JButton("删除");
    private final JButton DeleteCancel =new JButton("取消");
    private final JPanel UserInfoPanel = new JPanel();
    private final JPanel DeleteButtonPanel = new JPanel();

    public DeleteFrame(String id,String uname,String major){
        this.id = id;
        this.uname = uname;
        this.major = major;
        DeleteContainer = getContentPane();
        DeleteContainer.setLayout(new BorderLayout());
        setTitle("删除数据");
        setSize(300,200);
        setLocationRelativeTo(null);
        InitUIEvent();
        setVisible(true);
    }
    public void InitDeletePanel(){
        UserInfoPanel.setLayout(null);
        UserIdLabel.setBounds(50, 20, 50, 20);
        UserNameLabel.setBounds(50, 50, 50, 20);
        UserMajorLabel.setBounds(50, 80, 50, 20);

        UserIdLabelR.setBounds(110, 20, 120, 20);
        UserNameLabelR.setBounds(110, 50, 120, 20);
        UserMajorLabelR.setBounds(110, 80, 120, 20);
        UserIdLabelR.setText(id);
        UserNameLabelR.setText(uname);
        UserMajorLabelR.setText(major);

        UserInfoPanel.add(UserIdLabel);
        UserInfoPanel.add(UserNameLabel);
        UserInfoPanel.add(UserMajorLabel);
        UserInfoPanel.add(UserIdLabelR);
        UserInfoPanel.add(UserNameLabelR);
        UserInfoPanel.add(UserMajorLabelR);
        DeleteContainer.add(UserInfoPanel,"Center");
    }
    public void InitButtonPanel(){
        DeleteButtonPanel.setLayout(new GridLayout(1,2));
        DeleteButtonPanel.add(DeleteButton);
        DeleteButtonPanel.add(DeleteCancel);
        DeleteContainer.add(DeleteButtonPanel,"South");
    }

    public void InitUIEvent(){
        InitDeletePanel();
        InitButtonPanel();
        try {
            DBHelper = new MySqlDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        DeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 添加删除前确认对话框
                int n = JOptionPane.showConfirmDialog(UserInfoPanel,"是否确认删除?","提示",JOptionPane.YES_NO_OPTION);
                if(n==0){
                    if(DBHelper.deleteUser(uname)){
                        JOptionPane.showMessageDialog(UserInfoPanel, "删除成功！", "删除反馈", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    }else {
                        JOptionPane.showMessageDialog(UserInfoPanel, "删除失败！", "删除反馈", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
        DeleteCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
