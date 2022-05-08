package Chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Calendar;


public class Chat2 extends JFrame {

    Socket s = null;
    DataOutputStream dos = null;
    DataInputStream dis = null;
    private boolean bConnected = false;

    JTextField tfTxt = new JTextField();
    JTextArea taContent = new JTextArea();

    Thread tRecv = new Thread(new RecvThread());

    public static void main(String[] args) {
        new Chat2().launchFrame();
    }

    public Chat2(){
        super("志杰：");
    }//窗体名字

    public void launchFrame() {
        setLocation(400, 300);
        this.setSize(400,500);

        add(tfTxt, BorderLayout.SOUTH);
        add(taContent/*,BorderLayout.NORTH8*/);

        JScrollPane scrollpane = new JScrollPane();//创建滚动条面板
        scrollpane.setViewportView(taContent);//把组件放到滚动面板里
        add(scrollpane);//将滚动条面板加到窗体


        taContent.setBackground(Color.CYAN);
        tfTxt.setBackground(Color.pink);
        taContent.setForeground(Color.black);
        tfTxt.setForeground(Color.blue);

        tfTxt.setFont(new Font("宋体",Font.PLAIN,30));
        taContent.setFont(new Font("宋体",Font.PLAIN,20));

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

    public void connect() {

        try {
            s = new Socket("192.168.43.164",8888);
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
                dos.writeUTF(Chat2.super.getTitle()+str);
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
                    taContent.setText(taContent.getText() + str + '\n'+ Calendar.getInstance().getTime()+"\r\n");
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
