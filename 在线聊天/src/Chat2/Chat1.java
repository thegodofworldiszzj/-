package Chat2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;


public class Chat1 extends JFrame {

    Socket s = null;
    DataOutputStream dos = null;
    DataInputStream dis = null;
    private boolean bConnected = false;



    JTextField tfTxt = new JTextField();
    JTextArea taContent = new JTextArea();

    Thread tRecv = new Thread(new RecvThread());

    public static void main(String[] args) {
        new Chat1().launchFrame();
    }

    public Chat1(){
        super("志杰：");
    }//窗体名字

    public void launchFrame() {
        setLocation(400, 300);
        this.setSize(300,500);

        add(tfTxt, BorderLayout.SOUTH);
        add(taContent/*,BorderLayout.NORTH8*/);

        JScrollPane scrollpane = new JScrollPane();//创建滚动条面板
        scrollpane.setViewportView(taContent);//把组件放到滚动面板里
        add(scrollpane);//将滚动条面板加到窗体



        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent arg0) {
                disconnect();
                System.exit(0);
            }

        });
        tfTxt.addActionListener(new TFListener());
        setVisible(true);
        connect();

        tRecv.start();
    }//建立一个自己想要的窗体方法

//    public void initUI() {
//            //在initUI中实例化JFrame类的对象
//            JFrame frame = new JFrame();
//            //设置窗体对象的属性值
//            frame.setTitle("QQ聊天登录页面：");//设置窗体标题
//            frame.setSize(400, 250);//设置窗体大小，只对顶层容器生效
//            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//设置窗体关闭操作，3表示关闭窗体退出程序
//            frame.setLocationRelativeTo(null);//设置窗体相对于另一组间的居中位置，参数null表示窗体相对于屏幕的中央位置
//            frame.setResizable(false);//禁止调整窗体大小
//            frame.setFont(new Font("宋体",Font.PLAIN,14));//设置字体，显示格式正常，大小
//
//            //实例化FlowLayout流式布局类的对象，指定对齐方式为居中对齐组件之间的间隔为10个像素
//            FlowLayout fl = new FlowLayout(FlowLayout.CENTER,10,10);
//            //实例化流式布局类的对象
//            frame.setLayout(fl);
//
//            //实例化JLabel标签对象，该对象显示“账号”
//            JLabel labname = new JLabel("账号：");
//            labname.setFont(new Font("宋体",Font.PLAIN,14));
//            //将labname标签添加到窗体上
//            frame.add(labname);
//
//            //实例化JTextField标签对象化
//            JTextField text_name = new JTextField();
//            Dimension dim1 = new Dimension(300,30);
//            text_name.setPreferredSize(dim1);//设置除顶级容器组件以外其他组件的大小
//            //将textName标签添加到窗体上
//            frame.add(text_name);
//
//            //实例化JLabel标签对象，该对象显示“密码”
//            JLabel labpass = new JLabel("密码：");
//            labpass.setFont(new Font("宋体",Font.PLAIN,14));
//            //将labpass添加到窗体上
//            frame.add(labpass);
//
//            //实例化JPasswordField
//            JPasswordField text_password = new JPasswordField();
//            //设置大小
//            text_password.setPreferredSize(dim1);
//            //添加到窗体
//            frame.add(text_password);
//
//            //实例化JButton组件
//            JButton button1 = new JButton();
//            //设置按键的显示内容
//            Dimension dim2 = new Dimension(100,30);
//            button1.setText("登录");
//            button1.setFont(new Font("宋体",Font.PLAIN,14));
//            //设置按键大小
//            button1.setSize(dim2);
//            frame.add(button1);
//
//            frame.setVisible(true);//窗体可见，一定要放在所有组件加入窗体后
//
//
//            LoginListener ll = new LoginListener(frame,text_name,text_password);
//        send();
//            button1.addActionListener(ll);
//
//        }
//    public static class LoginListener implements ActionListener {
//        static JTextField text_name;
//        static JPasswordField text_password;
//        private JFrame login;
//
//
//        public LoginListener(JFrame login, JTextField text_name, JPasswordField text_password)
//        {//获取登录界面、账号密码输入框对象
//            this.login=login;
//            this.text_name=text_name;
//            this.text_password=text_password;
//
//        }
//
//        public void actionPerformed(ActionEvent e)
//        {
//            Dimension dim3 = new Dimension(300,30);
//            //生成新界面
//            JFrame login2 = new JFrame();
//            login2.setSize(400,200);
//            login2.setDefaultCloseOperation(3);
//            login2.setLocationRelativeTo(null);
//            login2.setFont(new Font("宋体",Font.PLAIN,14));  //宋体，正常风格，14号字体
//            //创建组件
//            JPanel jp1 = new JPanel();
//
//            if(text_name.getText().equals("zzjzjd") && text_password.getText().equals("4549"))
//            {
//                login.dispose();
//                new Chat1().launchFrame();
//            }
//            else{
//                JLabel message = new JLabel("登陆失败！");
//                message.setFont(new Font("宋体", Font.PLAIN, 14));  //宋体，正常风格，14号字体
//                message.setPreferredSize(dim3);
//                jp1.add(message);
//                login2.add(jp1, BorderLayout.CENTER);
//
//                login2.setResizable(false);
//                login2.setVisible(true);
//                login.dispose();
//                //通过我们获取的登录界面对象，用dispose方法关闭它
//            }
//        }
//
//
//
//    }
//
//    public void send(){
//        connect();
//        DataOutputStream date = null;
//        try {
//            String s1 = String.valueOf(LoginListener.text_name.getText());
//            String s2 = String.valueOf(LoginListener.text_password.getText());
//            date = new DataOutputStream(s.getOutputStream());
//            date.writeUTF(s1);
//            date.writeUTF(s2);
//            date.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    public void connect() {

        try {
            s = new Socket("192.168.43.164", 8008);
            dos = new DataOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());
            System.out.println("连接成功!");
            bConnected = true;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("服务器未开启！");
        }



    }//建立链接

    public void disconnect() {
        try {
            dos.close();
            dis.close();
            s.close();
        } catch (IOException e) {
            System.out.println("未连接到服务器！");//io异常是i代表input, o 代表 output, 所以io 异常就是输入输出异常。
        }
        catch (NullPointerException e){
            System.out.println("未连接到服务器！！");//空指针异常，原因是当“dos可能为Null”，抛出这个异常可以避免服务器没开启后不能关闭聊天框的问题
        }
    }//断开链接

    private class TFListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String str = tfTxt.getText().trim();

            tfTxt.setText("");

            try {
                dos.writeUTF(Chat1.super.getTitle()+str);
                dos.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }catch (NullPointerException e1){
                System.out.println("尚未连接到服务器！");
            }

        }

    }//创建一个监听者

    private class RecvThread implements Runnable {

        public void run() {
            try {
                while(bConnected) {
                    String str = dis.readUTF();
                    //System.out.println(str);
                    taContent.setText(taContent.getText() + str + '\n');
                }
            } catch (SocketException e) {
                System.out.println("退出网络连接!");
            } catch (EOFException e) {
                System.out.println("剑东已经下线!");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }//使双方数据能显示出来





}
