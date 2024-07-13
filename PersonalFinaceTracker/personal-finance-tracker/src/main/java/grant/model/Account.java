package grant.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

public abstract class Account {
    /** Account's id */
    private final String accountID;

    /** User id associated with this account */
    private final String userID;

    /** Name of the account */
    private String name;

    /** The account's current balance */
    private double balance;

    /** List of the account's transactions */
    private final ArrayList<Transaction> transactions;
    
    /** Type of account this is */
    private final AccountType accountType;

    /** Constructor for Account */
    public Account(String userID, String name, AccountType accountType, double initialBalance) {
        this.accountID = UUID.randomUUID().toString();
        this.userID = userID;
        this.name = name;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
        this.accountType = accountType;
    }

    /** Getter for accountID */
    public String getAccountID(){
        return accountID;
    }

    /** Getter for account name */
    public String getName() {
        return name;
    }

    /** Setter for account name */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for account balance */
    public double getBalance() {
        return balance;
    }
    
    /** Returns the account balance in a standard money format */
    public String getFormattedBalance() {
        return Transaction.formatDoubleToMoney(balance);
    }

    /** Getter for account's list of transactions */
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    /** Gets transactions sorted by date newest to oldest */
    public ArrayList<Transaction> getSortedTransactions() {
        // Create a copy of the original list
        ArrayList<Transaction> sortedTransactions = new ArrayList<>(transactions);
        
        Collections.sort(sortedTransactions, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction t1, Transaction t2) {
                // Sort by date in descending order (newest to oldest)
                return t2.getDate().compareTo(t1.getDate());
            }
        });

        return sortedTransactions;
    }

    /**
     * Adds an transaction to the account 
     * @return boolean indicating if the transaction was successfully added
     */
    public boolean addTransaction(Transaction transaction) {
        // Add to list of transactions
        boolean result = transactions.add(transaction);

        if(result) {
            // Update account balance
            balance += transaction.signedAmount();
        }

        return result;
    }

    /** 
     * Removes a transaction from the account
     * @return boolean indicating if the transaction was successfully removed
     */
    public boolean removeTransaction(Transaction transaction) {
        // Remove from list of transactions
        boolean result = transactions.remove(transaction);

        if(result) {
            // Update account balance
            balance -= transaction.signedAmount();
        }
        
        return result;
    }

    /** Edits a transaction */
    public void editTransaction(Transaction oldTransaction, Transaction newTransaction) {
        removeTransaction(oldTransaction);
        addTransaction(newTransaction);
    }

    /** Getter for account type */
    public AccountType getAccountType() {
        return accountType;
    }

    /** toString method for account. Returns an object representation of the account */
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
