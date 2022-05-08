package Chat2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Service {
    boolean started = false;
//    boolean get = false;
    static ServerSocket ss = null;
//    static String Get = new String();
    List<Client> clients = new ArrayList<Client>();

//    public static void main(String[] args) {
//        try {
//            ss = new ServerSocket(8888);
//            new Service().start2();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        new Service().start();
//    }

    public void start() {
        try {
            ss = new ServerSocket(7777);

            started = true;
        } catch (BindException e) {
            System.out.println("已经有服务端链接....");
            System.out.println("请退出该链接");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }//new出一个ServerSocket，并将started状态改为true

        try {

            while(started) {
                Socket s = ss.accept();
                Client c = new Client(s);
                System.out.println("有新客户端链接!");
                new Thread(c).start();
                clients.add(c);
                //dis.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ss.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

//    public void start2() throws IOException{
//        try {
//
//            DataInputStream in  =  new DataInputStream(ss.accept().getInputStream());
//            String msg = in.readUTF();
//            User user = new User(msg);
//            LoginJdbc.login(user);
//            if (LoginJdbc.login(user)){
//                get = true;
//                Get = "ture";
//            }
//
//            for(int i=0; i<clients.size(); i++) {
//                Client c = clients.get(i);
//                c.send(Get);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }



    class Client implements Runnable {
        private Socket s;
        private DataInputStream dis = null;
        private DataOutputStream dos = null;
        private boolean bConnected = false;

        public Client(Socket s) {
            this.s = s;
            try {
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
                bConnected = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void send(String str) {
            try {
                dos.writeUTF(str);
            } catch (IOException e) {
                clients.remove(this);
                System.out.println(".......");
            }
        }

        public void run() {
            try {
                while(bConnected) {
                    String str = dis.readUTF();
                    System.out.println(str);
                    for(int i=0; i<clients.size(); i++) {
                        Client c = clients.get(i);
                        c.send(str);
                    }
                }
            } catch (EOFException e) {
                System.out.println("客户端断开链接!");
            } catch (SocketException e) {
                System.out.println("客户端断开链接!");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(dis != null) dis.close();
                    if(dos != null) dos.close();
                    if(s != null) s.close();
                    s.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

    }





}
