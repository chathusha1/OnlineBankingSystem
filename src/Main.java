import service.BankService;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BankService bankService = new BankService();
        Scanner input = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n=== Online Banking System ===");
            System.out.println("1. Create Account");
            System.out.println("2. Check Balance");
            System.out.println("3. Deposit Money");
            System.out.println("4. Withdraw Money");
            System.out.println("5. Transfer Money");
            System.out.println("6. View Account Details");
            System.out.println("7. View Transaction History");
            System.out.println("8. Exit");
            System.out.print("What is Your Choice? ");
            
            try {
                int choice = input.nextInt();
                input.nextLine(); // Clear buffer
                
                switch (choice) {
                    case 1 -> bankService.createAccount();
                    case 2 -> bankService.checkBalance();
                    case 3 -> bankService.depositMoney();
                    case 4 -> bankService.withdrawMoney();
                    case 5 -> bankService.transferMoney();
                    case 6 -> bankService.viewAccountDetails();
                    case 7 -> bankService.viewTransactionHistory();
                    case 8 -> {
                        System.out.println("Thank you for using our banking system!");
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid choice! Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number between 1-8");
                input.nextLine(); // Clear invalid input
            }
        }
    }
}