package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class BankService {
    Scanner input = new Scanner(System.in);

    public void createAccount() {
        try {
            Connection conn = DBConnection.getConnection();
            if (conn != null) {
                System.out.print("Enter account holder name: ");
                String name = input.nextLine();
                System.out.print("Enter initial deposit: ");
                double balance = input.nextDouble();
                input.nextLine();

                String sql = "INSERT INTO accounts (name, balance) VALUES (?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setDouble(2, balance);
                stmt.executeUpdate();
                System.out.println("✅ Account created successfully!");
            }
        } catch (Exception e) {
            System.out.println("❌ Error creating account.");
            e.printStackTrace();
        }
    }

    public void checkBalance() {
        try {
            Connection conn = DBConnection.getConnection();
            System.out.print("Enter Account ID: ");
            int id = input.nextInt();
            input.nextLine();

            String sql = "SELECT * FROM accounts WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Account: " + rs.getString("name"));
                System.out.println("Balance: Rs." + rs.getDouble("balance"));
            } else {
                System.out.println("❌ Account not found!");
            }
        } catch (Exception e) {
            System.out.println("❌ Error fetching balance.");
            e.printStackTrace();
        }
    }
}
