package service;

import model.User;
import java.util.*;

public class BankService {
    private List<User> users = new ArrayList<>();

    public void createAccount(String username, String password) {
        User user = new User(username, password, 0.0);
        users.add(user);
        System.out.println("Account created for " + username);
    }

    public void deposit(String username, double amount) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                user.setBalance(user.getBalance() + amount);
                System.out.println("Deposited " + amount + " to " + username);
                return;
            }
        }
        System.out.println("User not found.");
    }

    public void withdraw(String username, double amount) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (user.getBalance() >= amount) {
                    user.setBalance(user.getBalance() - amount);
                    System.out.println("Withdrew " + amount + " from " + username);
                } else {
                    System.out.println("Insufficient balance.");
                }
                return;
            }
        }
        System.out.println("User not found.");
    }

    public void checkBalance(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("Balance for " + username + ": " + user.getBalance());
                return;
            }
        }
        System.out.println("User not found.");
    }
}
