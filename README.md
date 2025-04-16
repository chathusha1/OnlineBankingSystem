💻 Online Banking System – Java Console Application

A complete Java-based console application integrated with MySQL that simulates real-world banking operations with secure data persistence.
✨ Features
✅ Account Management

    Create new bank accounts

    View account details

    Check account balances

💰 Transaction Processing

    Deposit money

    Withdraw funds

    Transfer between accounts

📊 Transaction History

    View all transactions

    Filter by date or transaction type

🔒 Secure Database Storage

    MySQL integration using JDBC

    Reliable and persistent data storage

🛠 Technologies Used

    Core Java (OOP Concepts, JDBC)

    MySQL 8.0+

    MySQL Workbench

    Maven 3.8+ (Dependency Management)

🚀 Getting Started
✅ Prerequisites

    Java JDK 17+

    MySQL Server 8.0+

    Maven 3.8+

📥 Installation

git clone https://github.com/chathusha1/OnlineBankingSystem.git

    Open the project in your preferred Java IDE (IntelliJ IDEA, VS Code, etc.)

🗃️ Database Setup
1. Create the Database

CREATE DATABASE online_banking;
USE online_banking;

2. Create the Tables

       CREATE TABLE accounts (
          id INT AUTO_INCREMENT PRIMARY KEY,
          name VARCHAR(100) NOT NULL,
          balance DECIMAL(15,2) NOT NULL DEFAULT 0,
          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
       );

       CREATE TABLE transactions (
          id INT AUTO_INCREMENT PRIMARY KEY,
          account_id INT NOT NULL,
          type ENUM('DEPOSIT', 'WITHDRAWAL', 'TRANSFER') NOT NULL,
          amount DECIMAL(15,2) NOT NULL,
          related_account INT,
          transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
          FOREIGN KEY (account_id) REFERENCES accounts(id)
       );

▶️ Running the Application

Compile and run:

java -jar OnlineBankingSystem.jar

🧭 Main Menu Options

    1. Create Account  
    2. Check Balance  
    3. Deposit Money  
    4. Withdraw Money  
    5. Transfer Money  
    6. View Account Details  
    7. View Transaction History  
    8. Exit

📁 Project Structure

    online-banking-system/
     ├── src/
     │   └── main/
     │       └── java/
     │           ├── service/
     │           │   ├── BankService.java
     │           │   └── DBConnection.java
     │           └── Main.java
     ├── lib/
     │   └── mysql-connector-java-8.0.xx.jar
     ├── .gitignore
     └── README.md

🤝 Contributing

    Fork the repository

    Create your feature branch: git checkout -b feature/YourFeature

    Commit your changes: git commit -m 'Add YourFeature'

    Push to the branch: git push origin feature/YourFeature

    Open a Pull Request

📄 License

    Distributed under the MIT License. See LICENSE for more information.
📧 Contact

    Chathusha Dinuranga – chathubrooit@gmail.com
    Project Link – chathusha1
