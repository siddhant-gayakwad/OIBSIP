import java.util.*;

class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        bank.initialize();  // Initialize bank with some users

        ATM atm = new ATM(bank);
        atm.start();
    }
}

class ATM {
    private Bank bank;
    private User currentUser;

    public ATM(Bank bank) {
        this.bank = bank;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the ATM");

        while (true) {
            System.out.print("Enter user ID: ");
            String userId = scanner.nextLine();

            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine();

            currentUser = bank.authenticateUser(userId, pin);
            if (currentUser != null) {
                showMenu();
            } else {
                System.out.println("Invalid user ID or PIN.");
            }
        }
    }

    private void showMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    currentUser.printTransactionHistory();
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    currentUser.withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    currentUser.deposit(depositAmount);
                    break;
                case 4:
                    System.out.print("Enter recipient user ID: ");
                    String recipientId = scanner.next();
                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = scanner.nextDouble();
                    currentUser.transfer(recipientId, transferAmount, bank);
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

class User {
    private String userId;
    private String pin;
    private double balance;
    private List<Transaction> transactionHistory;

    public User(String userId, String pin, double initialBalance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public boolean authenticate(String pin) {
        return this.pin.equals(pin);
    }

    public void printTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance.");
        } else {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdraw", amount));
            System.out.println("Withdraw successful. New balance: " + balance);
        }
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount));
        System.out.println("Deposit successful. New balance: " + balance);
    }

    public void transfer(String recipientId, double amount, Bank bank) {
        if (amount > balance) {
            System.out.println("Insufficient balance.");
        } else {
            User recipient = bank.getUser(recipientId);
            if (recipient != null) {
                balance -= amount;
                recipient.deposit(amount);
                transactionHistory.add(new Transaction("Transfer to " + recipientId, amount));
                System.out.println("Transfer successful. New balance: " + balance);
            } else {
                System.out.println("Recipient not found.");
            }
        }
    }
}

class Transaction {
    private String type;
    private double amount;
    private Date date;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.date = new Date();
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "type='" + type + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}

class Bank {
    private Map<String, User> users;

    public Bank() {
        users = new HashMap<>();
    }

    public void initialize() {
        users.put("user1", new User("user1", "1234", 1000));
        users.put("user2", new User("user2", "5678", 2000));
    }

    public User authenticateUser(String userId, String pin) {
        User user = users.get(userId);
        if (user != null && user.authenticate(pin)) {
            return user;
        }
        return null;
    }

    public User getUser(String userId) {
        return users.get(userId);
    }
}
