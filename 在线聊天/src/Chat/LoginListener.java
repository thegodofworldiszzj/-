package Chat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import javax.swing.*;

public class LoginListener implements ActionListener{
    private JTextField text_name;
    private JPasswordField text_password;
    private javax.swing.JFrame login;


    public LoginListener(JFrame login, JTextField text_name, JPasswordField text_password)
    {//获取登录界面、账号密码输入框对象
        this.login=login;
        this.text_name=text_name;
        this.text_password=text_password;



    }

    public void actionPerformed(ActionEvent e)
    {
        Dimension dim3 = new Dimension(300,30);
        //生成新界面
        javax.swing.JFrame login2 = new javax.swing.JFrame();
        login2.setSize(400,200);
        login2.setDefaultCloseOperation(3);
        login2.setLocationRelativeTo(null);
        login2.setFont(new Font("宋体",Font.PLAIN,14));  //宋体，正常风格，14号字体
        //创建组件
        javax.swing.JPanel jp1 = new JPanel();

        if(text_name.getText().equals("zzjzjd") && text_password.getText().equals("4549"))
        {
            login.dispose();
            new Chat2().launchFrame();
        }
        else{
            JLabel message = new JLabel("登陆失败！");
            message.setFont(new Font("宋体", Font.PLAIN, 14));  //宋体，正常风格，14号字体
            message.setPreferredSize(dim3);
            jp1.add(message);
            login2.add(jp1, BorderLayout.CENTER);
            login2.setResizable(false);
            login2.setVisible(true);
            login.dispose();

        }

    }

}