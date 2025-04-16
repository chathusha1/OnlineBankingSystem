# 💰 Online Banking System

A **simple Java-based banking application** with **MySQL** database integration that allows users to:

- 🏦 Create new bank accounts  
- 💸 Check account balances  
- 👤 Manage customer data  

---

## 🛠️ Technologies Used

- **Java** (Core Java, JDBC)  
- **MySQL** (Database)  
- **MySQL Workbench** (Database Management)  
- **Maven** (Dependency Management)  

---

## 🚀 Features

- ✅ Account creation with initial deposit  
- ✅ Balance inquiry  
- ✅ Secure database storage  
- ✅ Simple console-based interface  

---

## ⚙️ Setup Instructions

### 📌 Prerequisites

- Java JDK 8 or later  
- MySQL Server 5.7+  
- MySQL Connector/J  

---

### 🧩 Installation

1. **Clone the repository:**

```bash
git clone https://github.com/chathusha1/OnlineBankingSystem.git

    Import the project into your favorite Java IDE (e.g., IntelliJ IDEA, VS Code)

    Set up the database using MySQL Workbench or CLI:

CREATE DATABASE online_banking;
USE online_banking;

CREATE TABLE accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    balance DECIMAL(15,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

    Configure DB connection in DBConnection.java:

String url = "jdbc:mysql://localhost:3306/online_banking";
String username = "your_username";
String password = "your_password";

    Run the application:

java -jar BankingApp.jar

📋 Usage

Run the application and select options from the menu:

=== Online Banking System ===
1. Create Account
2. Check Balance
3. Exit

👉 Follow the on-screen prompts to proceed.
📂 Project Structure

banking-app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── service/
│   │   │   │   ├── BankService.java
│   │   │   │   └── DBConnection.java
│   │   │   └── Main.java
│   │   └── resources/
├── lib/
│   └── mysql-connector-java-x.x.xx.jar
└── README.md

📜 License

This project is licensed under the MIT License – see the LICENSE file for details.
🤝 Contributing

Contributions are welcome!
Fork the repo, make your changes, and create a pull request. ❤️
📧 Contact

R.M. Chathusha Dinuranga
📩 Email: chathubrooit@gmail.com
🔗 Project Link: Online Banking System on GitHub
