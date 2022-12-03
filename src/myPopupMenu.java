import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class myPopupMenu extends JPopupMenu { // 表格鼠标右键菜单
    public myPopupMenu(String id,String uname,String major){
        JMenuItem DeleteItem = new JMenuItem(); // 删除按钮
        JMenuItem ModifyItem = new JMenuItem(); // 修改按钮
        DeleteItem.setText("删除");
        ModifyItem.setText("修改信息");
        add(DeleteItem);
        add(ModifyItem);
        DeleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteFrame(id,uname,major);
            }
        });
        ModifyItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ModifyFrame(id,uname,major);
            }
        });
    }
}
