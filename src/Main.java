import java.util.Scanner;
import service.BankService;

public class Main {
    public static void main(String[] args) {
        BankService bank = new BankService();
        Scanner sc = new Scanner(System.in);

        System.out.println("==== Online Banking System ====");
        while (true) {
            System.out.println("What is Your Choice? ");
            System.out.println("1. Create Account\n2. Deposit\n3. Withdraw\n4. Check Balance\n5. Exit");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                System.out.print("Username: ");
                String user = sc.nextLine();
                System.out.print("Password: ");
                String pass = sc.nextLine();
                bank.createAccount(user, pass);
            } else if (choice == 2) {
                System.out.print("Username: ");
                String user = sc.nextLine();
                System.out.print("Amount to deposit: ");
                double amt = sc.nextDouble();
                bank.deposit(user, amt);
            } else if (choice == 3) {
                System.out.print("Username: ");
                String user = sc.nextLine();
                System.out.print("Amount to withdraw: ");
                double amt = sc.nextDouble();
                bank.withdraw(user, amt);
            } else if (choice == 4) {
                System.out.print("Username: ");
                String user = sc.nextLine();
                bank.checkBalance(user);
            } else if (choice == 5) {
                System.out.println("Exiting...");
                break;
            }
        }
        sc.close();
    }


}
