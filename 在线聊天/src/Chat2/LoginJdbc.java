package Chat2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginJdbc {

    public static boolean login(User userZhangsan) {

        Connection conn=null;
        Statement st=null;
        ResultSet rs=null;
        try {
            //1、装载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            //2、链接数据库，使用com.mysql.jdbc.Connection包会出错
            List<User> list=new ArrayList<User>();
            conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/sw_database?user=root&password=root");
            //3、创建连接语句
            st=conn.createStatement();
            //4、执行ＳＱＬ语句获得结果集
            rs=st.executeQuery("select * from sw_user where username='"+userZhangsan.getUsername()+"' and password='"+userZhangsan.getPassword()+"'");
            //5、循环获得数据库字段生成对象
            //这种方法登录要把数据库数据都拿过来和login中数据比较，超级浪费资源
            if(rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            //关闭结果集
            try {
                if(rs!=null) {
                    rs.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //关闭连接语句
            try {
                if(st!=null) {
                    st.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //关闭数据库连接
            try {
                if(conn!=null) {
                    conn.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return false;
    }


}