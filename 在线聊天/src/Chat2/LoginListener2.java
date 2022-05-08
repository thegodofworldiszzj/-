package Chat2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;


public class LoginListener2 implements ActionListener {
        Socket s = null;
        private JTextField text_name;
        private JPasswordField text_password;
        private JFrame login2;

        public LoginListener2(JFrame login, JTextField text_name, JPasswordField text_password)
        {//获取登录界面、账号密码输入框对象
            this.login2 = login;
            this.text_name = text_name;
            this.text_password = text_password;

        }
        public void actionPerformed(ActionEvent e1) {
            login2.dispose();
            String name = text_name.getText();
            String password = text_password.getText();
            String namePwd = name + "," + password;

            try {
                s = new Socket("192.168.43.164", 8888);
                new DataOutputStream(s.getOutputStream()).writeUTF(namePwd);
                String str = new DataInputStream(s.getInputStream()).readUTF();
                if(str=="ture"){
                    new Chat2().launchFrame();
                }
                System.out.println("连接成功!");

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("服务器未开启！");
            }



        }
    }

