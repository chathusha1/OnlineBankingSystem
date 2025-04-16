import service.BankService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BankService bank = new BankService();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("==== Online Banking System ====");
            System.out.println("1. Create Account");
            System.out.println("2. Check Balance");
            System.out.println("3. Exit");
            System.out.print("What is Your Choice? ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    bank.createAccount();
                    break;
                case 2:
                    bank.checkBalance();
                    break;
                case 3:
                    System.out.println("Thank you! Goodbye.");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
