package FinalProject.src;

import java.util.ArrayList;
import java.util.UUID;

public abstract class Account {
    private final String accountID;
    private final String userID;
    private String name;
    private double balance;
    private final ArrayList<Transaction> transactions;
    private final AccountType accountType;

    public Account(String userID, String name, AccountType accountType) {
        this.accountID = UUID.randomUUID().toString();
        this.userID = userID;
        this.name = name;
        this.balance = 0;
        this.transactions = new ArrayList<>();
        this.accountType = accountType;
    }

    public String getAccountID(){
        return accountID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public String getFormattedBalance() {
        return Transaction.formatDoubleToMoney(balance);
    }

    public ArrayList<Transaction> geTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        // Add to list of transactions
        transactions.add(transaction);

        // Update account balance
        balance += transaction.signedAmount();
    }

    public void removeTransaction(Transaction transaction) {
        // Remove from list of transactions
        transactions.remove(transaction);

        // Update account balance
        balance -= transaction.signedAmount();        
    }

    public AccountType getAccountType() {
        return accountType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Account {\n");
        sb.append("   Account ID='").append(accountID).append("'\n");
        sb.append("   User ID='").append(userID).append("'\n");
        sb.append("   Name='").append(name).append("'\n");
        sb.append("   Balance=").append(getFormattedBalance()).append("\n");
        sb.append("   Account Type=").append(accountType).append("\n");
        sb.append("   Transactions=[\n");
    
        for (Transaction transaction : transactions) {
            sb.append("      ").append(transaction.toString().replace("\n", "\n      ")).append("\n");
        }
        sb.append("   ]\n");
        sb.append("}");
    
        return sb.toString();
    }
}
