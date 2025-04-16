package service;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/online_banking", "root", "32213221");
        } catch (Exception e) {
            System.out.println("? Database connection failed!");
            e.printStackTrace();
            return null;
        }
    }
}
