package service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class JDBCUtil {
    public static Connection getConnection() {
        Connection c = null;

        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            // Lấy thông tin kết nối từ biến môi trường
            String host = System.getenv("DATABASE_HOST"); // Ví dụ: "db"
            String port = System.getenv("DATABASE_PORT"); // Ví dụ: "3306"
            String database = System.getenv("DATABASE_NAME"); // Ví dụ: "phuonglinh_db"
            String user = System.getenv("DATABASE_USER"); // Ví dụ: "root"
            String password = System.getenv("DATABASE_PASSWORD"); // Ví dụ: "nook"

            // Tạo URL kết nối
            String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
            System.out.println(url);
            if("jdbc:mysql://null:null/null".equals(url))
                url = "jdbc:mySQL://localhost:3306/phuonglinh_db";
//
//            System.out.println("Connecting to: " + url);

            // Kết nối tới cơ sở dữ liệu
            c = DriverManager.getConnection(url, user, password);
//            System.out.println("connect successful");

        } catch (SQLException e){
            e.printStackTrace();
        }
        return c;
//        FileInputStream inputStream = null;
//        try {
//            Properties properties = new Properties();
//
//            inputStream = new FileInputStream(".\\src\\config.properties");
//            properties.load(inputStream);
//            Connection c = null;
//
//            try {
////            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
//                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
//
////                String url = "jdbc:mySQL://"+properties.getProperty("db.url");
//                String url = "jdbc:mySQL://localhost:3306/phuonglinh_db";
//                System.out.println("url: " + url);
//                String user = properties.getProperty("db.username");
//                String pwd = properties.getProperty("db.password");
//
//                c = DriverManager.getConnection(url,user,pwd);
//
//            } catch (SQLException e){
//                e.printStackTrace();
//            }
//            return c;
//        } catch (IOException e) {
//            System.out.println("url error:" + e.getMessage());
//            throw new RuntimeException(e);
//        }



    }

    public static void closeConnection(Connection c){
        try {
            if(c!=null){
                c.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void printInfo(Connection c){
        try {

            if (c != null) {
                DatabaseMetaData mtdt = c.getMetaData();
                System.out.println(mtdt.getDatabaseProductName());
                System.out.println(mtdt.getDatabaseProductVersion());
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
