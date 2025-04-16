package service;

import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/online_banking";
    private static final String USER = "root";
    private static final String PASSWORD = "32213221";
    
    static {
        try {
            initializeDatabase();
        } catch (SQLException e) {
            System.err.println("Failed to initialize database: " + e.getMessage());
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    private static void initializeDatabase() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            
            stmt.execute("CREATE TABLE IF NOT EXISTS accounts (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "balance DECIMAL(15,2) NOT NULL DEFAULT 0," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
            
            stmt.execute("CREATE TABLE IF NOT EXISTS transactions (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "account_id INT NOT NULL," +
                "type ENUM('DEPOSIT', 'WITHDRAWAL', 'TRANSFER') NOT NULL," +
                "amount DECIMAL(15,2) NOT NULL," +
                "related_account INT," +
                "transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (account_id) REFERENCES accounts(id))");
        }
    }
}