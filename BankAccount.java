public class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Deposit must be positive.");
        balance += amount;
        System.out.printf("Deposited: %.2f | Balance: %.2f%n", amount, balance);
    }

    public void withdraw(double amount) {
        if (amount > balance)
            throw new IllegalStateException("Insufficient funds.");
        balance -= amount;
        System.out.printf("Withdrawn: %.2f | Balance: %.2f%n", amount, balance);
    }

    public double getBalance() {
        return balance;
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000);
        account.deposit(500);
        account.withdraw(200);

        try {
            account.withdraw(2000);
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}