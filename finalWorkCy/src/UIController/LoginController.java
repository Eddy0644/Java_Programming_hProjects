package UIController;

import Entry.Main;
import Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;

public class LoginController extends MasterController {
    public static int status = 0;
    JTextField text_user_number;
    JTextField text_user_password;
    JButton button_login;
    JButton button_redirect_to_reg;

    public LoginController() {
        super(300, 200, "欢迎登录学校教务系统！");
        status++;
    }

    public void addToPane() {
        //need to be overwrited

        JPanel Line1 = new JPanel();
        Line1.setLayout(new FlowLayout());
        Line1.add(new JLabel("欢迎使用教务管理系统"));
        pane.add(Line1);

        JPanel Line2 = new JPanel();
        Line2.setLayout(new FlowLayout());
        Line2.add(new JLabel("学工号："));
        Line2.add(text_user_number = new JTextField(12));
        pane.add(Line2);


        JPanel Line3 = new JPanel();
        Line3.setLayout(new FlowLayout());
        Line3.add(new JLabel("密码："));
        Line3.add(text_user_password = new JPasswordField(12));
        pane.add(Line3);

        pane.add(button_login = new JButton("登  录"));
        button_login.addActionListener(this);

        pane.add(button_redirect_to_reg = new JButton("注  册"));
        button_redirect_to_reg.addActionListener(this);

        pane.setLayout(new GridLayout(5, 1));
    }

    public void onRedirectToRegButtonClick() {
        new RegController();
        this.dispose();
        status--;
    }

    public void onLoginButtonClick() {
        HashMap<String, String> queryCondition = new HashMap<>();
        queryCondition.put("user_number", text_user_number.getText());
        queryCondition.put("user_password", text_user_password.getText());

        User tempUser = (User) Main.ModelUser.selectSingleObjectFromList(queryCondition);
        if (tempUser != null) {
//			this.showDialog("登录成功");
            System.out.println("已成功登录为" + text_user_number.getText());
            Main.SessionHold.put("CurrentLoginUser", tempUser);

            if (tempUser.role_type == 1) {
                // 老师
                new TeacherMainController();
                this.dispose();
                status--;
            } else {
                // 学生
                new StudentMainController();
                this.dispose();
                status--;
            }
        } else {
            this.showDialog("用户名或者密码不正确，请重试！");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button_redirect_to_reg) {
            this.onRedirectToRegButtonClick();
        }

        if (e.getSource() == button_login) {
            this.onLoginButtonClick();
        }
    }

}
