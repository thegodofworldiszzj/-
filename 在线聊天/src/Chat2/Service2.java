package Chat2;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Service2 {
//    boolean started = false;
    boolean get = false;
    static ServerSocket ss = null;
      static String Get = new String();

    List<Client2> clients2 = new ArrayList<Client2>();

    public static void main(String[] args) {
       new Service2().start2();
    }

    public void start2(){
        try {
            ss = new ServerSocket(8888);
            get = true;
        } catch (BindException e) {
            System.out.println("已经有服务端链接....");
            System.out.println("请退出该链接");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            while(get) {

                String msg = new DataInputStream(ss.accept().getInputStream()).readUTF();
                User user = new User(msg);
                boolean a = LoginJdbc.login(user);
//                try {
//                    ss.accept().close();
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
                if (a)
                {
                    Get = "ture";
                }
                Client2 d = new Client2(ss.accept());
                System.out.println("有新客户端链接!");
                new Thread(d).start();
                clients2.add(d);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Client2 implements Runnable {
        private Socket s;
        private boolean bConnected = true;

        public Client2(Socket s) {
            this.s = s;
        }

        public void send2() {
            try {
                new DataOutputStream(ss.accept().getOutputStream()).writeUTF(Get);
            } catch (IOException e) {
                clients2.remove(this);
                System.out.println(".......");
            }
        }

        public void run() {
            while (bConnected) {
                for(int i=0; i<clients2.size(); i++) {
                       Client2 c = clients2.get(i);
                       c.send2();
                 new Service().start();
                }
            }
        }
    }

}