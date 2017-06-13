package LMS.Database;

import java.sql.*;

/**
 * Created by guohouxiao on 2017/6/9.
 * 数据库连接
 */
public class Database {

    private static final String DRIVER = "com.mysql.jdbc.Driver";//驱动程序名
    private static final String URL = "jdbc:mysql://localhost:3306/lms?characterEncoding=utf8&useSSL=true";//URL指向要访问的数据库名，同时MySQL在高版本需要指明是否进行SSL连接
    private static final String USER = "root";//MySQL的用户名
    private static final String PASSWORD = "251010";//MySQL的密码

    public Connection connection;
    public Statement statement;
    public ResultSet resultSet;

    public Database() throws SQLException {
        try {
            Class.forName(DRIVER);//加载驱动程序
            System.out.println("驱动加载成功");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);//建立数据库连接
            statement = connection.createStatement();//获取数据操作的对象
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
