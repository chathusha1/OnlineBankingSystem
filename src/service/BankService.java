package service;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankService {
    private Scanner input = new Scanner(System.in);

    // Helper methods
    private int getValidAccountId(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int id = input.nextInt();
                input.nextLine();
                return id;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter numbers only.");
                input.nextLine();
            }
        }
    }

    private double getValidAmount(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double amount = input.nextDouble();
                input.nextLine();
                if (amount <= 0) {
                    System.out.println("Amount must be positive!");
                    continue;
                }
                return amount;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter numbers only.");
                input.nextLine();
            }
        }
    }

    private void recordTransaction(Connection conn, int accountId, String type, 
                             double amount, Integer relatedAccount) throws SQLException {
    String sql = "INSERT INTO transactions (account_id, type, amount, related_account) " +
                 "VALUES (?, ?, ?, ?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, accountId);
        stmt.setString(2, type);
        stmt.setDouble(3, amount);
        if (relatedAccount != null) {
            stmt.setInt(4, relatedAccount);
        } else {
            stmt.setNull(4, Types.INTEGER);
        }
        stmt.executeUpdate();
    }
}

    // Main methods
    public void createAccount() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter account holder name: ");
            String name = input.nextLine();
            
            double balance = getValidAmount("Enter initial deposit: ");
            
            String sql = "INSERT INTO accounts (name, balance) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, name);
                stmt.setDouble(2, balance);
                stmt.executeUpdate();
                
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int accountId = rs.getInt(1);
                        recordTransaction(conn, accountId, "DEPOSIT", balance, null);
                        System.out.printf("✅ Account created successfully! Account ID: %d%n", accountId);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error creating account: " + e.getMessage());
        }
    }

    public void checkBalance() {
        int accountId = getValidAccountId("Enter Account ID: ");
        
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT name, balance FROM accounts WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, accountId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        System.out.printf("Account Holder: %s%nBalance: Rs.%.2f%n", 
                            rs.getString("name"), rs.getDouble("balance"));
                    } else {
                        System.out.println("❌ Account not found!");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching balance: " + e.getMessage());
        }
    }

    public void depositMoney() {
        int accountId = getValidAccountId("Enter account ID: ");
        double amount = getValidAmount("Enter deposit amount: ");
        
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            
            try {
                // Update balance
                String updateSql = "UPDATE accounts SET balance = balance + ? WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
                    stmt.setDouble(1, amount);
                    stmt.setInt(2, accountId);
                    int rows = stmt.executeUpdate();
                    
                    if (rows == 0) {
                        System.out.println("❌ Account not found!");
                        return;
                    }
                }
                
                // Record transaction
                recordTransaction(conn, accountId, "DEPOSIT", amount, null);
                
                conn.commit();
                System.out.println("✅ Deposit successful!");
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("❌ Deposit failed: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
        }
    }

    public void withdrawMoney() {
        int accountId = getValidAccountId("Enter account ID: ");
        double amount = getValidAmount("Enter withdrawal amount: ");
        
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            
            try {
                // Check balance
                String checkSql = "SELECT balance FROM accounts WHERE id = ? FOR UPDATE";
                try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                    checkStmt.setInt(1, accountId);
                    try (ResultSet rs = checkStmt.executeQuery()) {
                        if (!rs.next()) {
                            System.out.println("❌ Account not found!");
                            return;
                        }
                        double balance = rs.getDouble("balance");
                        if (balance < amount) {
                            System.out.println("❌ Insufficient balance!");
                            return;
                        }
                    }
                }
                
                // Update balance
                String updateSql = "UPDATE accounts SET balance = balance - ? WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
                    stmt.setDouble(1, amount);
                    stmt.setInt(2, accountId);
                    stmt.executeUpdate();
                }
                
                // Record transaction
                recordTransaction(conn, accountId, "WITHDRAWAL", amount, null);
                
                conn.commit();
                System.out.println("✅ Withdrawal successful!");
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("❌ Withdrawal failed: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
        }
    }

    public void transferMoney() {
        int fromAccount = getValidAccountId("Enter your account ID: ");
        int toAccount = getValidAccountId("Enter recipient account ID: ");
        double amount = getValidAmount("Enter amount to transfer: ");
        
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            
            try {
                // Check sender's balance
                String checkSql = "SELECT balance FROM accounts WHERE id = ? FOR UPDATE";
                try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                    checkStmt.setInt(1, fromAccount);
                    try (ResultSet rs = checkStmt.executeQuery()) {
                        if (!rs.next()) {
                            System.out.println("❌ Sender account not found!");
                            return;
                        }
                        double balance = rs.getDouble("balance");
                        if (balance < amount) {
                            System.out.println("❌ Insufficient balance!");
                            return;
                        }
                    }
                }
                
                // Deduct from sender
                String deductSql = "UPDATE accounts SET balance = balance - ? WHERE id = ?";
                try (PreparedStatement deductStmt = conn.prepareStatement(deductSql)) {
                    deductStmt.setDouble(1, amount);
                    deductStmt.setInt(2, fromAccount);
                    deductStmt.executeUpdate();
                }
                
                // Add to recipient
                String addSql = "UPDATE accounts SET balance = balance + ? WHERE id = ?";
                try (PreparedStatement addStmt = conn.prepareStatement(addSql)) {
                    addStmt.setDouble(1, amount);
                    addStmt.setInt(2, toAccount);
                    addStmt.executeUpdate();
                }
                
                // Record transactions
                recordTransaction(conn, fromAccount, "TRANSFER", amount, toAccount);
                recordTransaction(conn, toAccount, "TRANSFER", amount, fromAccount);
                
                conn.commit();
                System.out.println("✅ Transfer successful!");
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("❌ Transfer failed: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
        }
    }

    public void viewAccountDetails() {
        int accountId = getValidAccountId("Enter account ID: ");
        
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM accounts WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, accountId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("\n=== Account Details ===");
                        System.out.println("Account ID: " + rs.getInt("id"));
                        System.out.println("Account Holder: " + rs.getString("name"));
                        System.out.printf("Current Balance: Rs.%.2f%n", rs.getDouble("balance"));
                        System.out.println("Account Created: " + rs.getTimestamp("created_at"));
                    } else {
                        System.out.println("❌ Account not found!");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving account details: " + e.getMessage());
        }
    }

    public void viewTransactionHistory() {
        int accountId = getValidAccountId("Enter account ID: ");
        
        try (Connection conn = DBConnection.getConnection()) {
            // Check if account exists
            String checkSql = "SELECT name FROM accounts WHERE id = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, accountId);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("❌ Account not found!");
                        return;
                    }
                    System.out.println("\nTransaction History for: " + rs.getString("name"));
                }
            }
            
            // Get transactions
            String sql = "SELECT * FROM transactions WHERE account_id = ? ORDER BY transaction_date DESC";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, accountId);
                try (ResultSet rs = stmt.executeQuery()) {
                    System.out.println("\nDate\t\tType\t\tAmount\t\tRelated Account");
                    System.out.println("--------------------------------------------------");
                    
                    boolean hasTransactions = false;
                    while (rs.next()) {
                        hasTransactions = true;
                        System.out.printf("%s\t%s\tRs.%.2f\t%s%n",
                            rs.getTimestamp("transaction_date"),
                            rs.getString("type"),
                            rs.getDouble("amount"),
                            rs.getObject("related_account") != null ? rs.getInt("related_account") : "N/A");
                    }
                    
                    if (!hasTransactions) {
                        System.out.println("No transactions found for this account");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving transaction history: " + e.getMessage());
        }
    }
}