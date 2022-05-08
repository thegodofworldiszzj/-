package Chat2;

import java.awt.Dimension;  //封装了一个构件的高度和宽度
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.*;

public class Login2{

    //在类中定义初始化界面的方法
    public void initUI() {
        //在initUI中实例化JFrame类的对象
        JFrame frame = new JFrame();
        //设置窗体对象的属性值
        frame.setTitle("❤登录界面❤️");//设置窗体标题
        frame.setSize(500, 500);//设置窗体大小，只对顶层容器生效
        frame.setDefaultCloseOperation(3);//设置窗体关闭操作，3表示关闭窗体退出程序
        frame.setLocationRelativeTo(null);//设置窗体相对于另一组间的居中位置，参数null表示窗体相对于屏幕的中央位置
        frame.setResizable(false);//禁止调整窗体大小
        frame.setFont(new Font("宋体",Font.PLAIN,14));//设置字体，显示格式正常，大小


        //实例化FlowLayout流式布局类的对象，指定对齐方式为居中对齐组件之间的间隔为10个像素
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER,5,5);
        //实例化流式布局类的对象
        frame.setLayout(fl);

        Dimension dim1 = new Dimension (500, 200);//图片大小
        Dimension dim2 = new Dimension (100, 50);//标签大小
        Dimension dim3 = new Dimension (300, 30);//输入框大小
        Dimension dim4 = new Dimension (100, 40);//按钮大小


        ImageIcon icon = new ImageIcon ("C:\\Users\\17898\\Desktop\\在线聊天\\src\\Chat\\tupian\\Login1(1).jpg");
        JLabel labimg = new JLabel (icon);
        labimg.setPreferredSize (dim1);
        frame.add (labimg);

        JLabel text_name = new JLabel ();
        text_name.setText ("账号：");
        text_name.setFont(new Font("宋体",Font.PLAIN,14));
        text_name.setPreferredSize (dim2);
        frame.add (text_name);
        JTextField textuser = new JTextField ();
        textuser.setPreferredSize (dim3);
        frame.add (textuser);

        JLabel text_password = new JLabel ();
        text_password.setText ("密码：");
        text_password.setFont(new Font("宋体",Font.PLAIN,14));
        text_password.setPreferredSize (dim2);
        frame.add (text_password);
        JPasswordField textPassword = new JPasswordField ();
        textPassword.setPreferredSize (dim3);
        frame.add (textPassword);

        JLabel text_ip = new JLabel ();
        text_ip.setText ("IP：");
        text_ip.setFont(new Font("宋体",Font.PLAIN,14));
        text_ip.setPreferredSize (dim2);
        frame.add (text_ip);
        JTextField textip = new JTextField ();
        textip.setPreferredSize (dim3);
        frame.add (textip);


        JButton button = new JButton ();
        button.setBorder (BorderFactory.createRaisedBevelBorder ());
        button.setText ("登录");
        button.setFont(new Font("宋体",Font.PLAIN,14));
        button.setPreferredSize (dim4);
        frame.add(button);
        frame.setVisible(true);//窗体可见，一定要放在所有组件加入窗体后

        LoginListener2 ll = new LoginListener2(frame,textuser,textPassword);
        button.addActionListener(ll);
    }
}
