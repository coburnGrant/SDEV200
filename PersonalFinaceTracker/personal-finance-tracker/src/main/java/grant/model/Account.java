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

    /** The account's initial balance */
    private final double initialBalance;

    /** The account's current balance */
    private double balance;

    /** List of the account's transactions */
    private final ArrayList<Transaction> transactions;
    
    /** Type of account this is */
    private final AccountType accountType;

    private final AccountCacher cacher;

    /** Constructor for New Account */
    public Account(String userID, String name, AccountType accountType, double initialBalance, AccountCacher cacher) {
        this(UUID.randomUUID().toString(), userID, name, accountType, initialBalance, cacher);
    }

    /** Constructor for Existing Account */
    public Account(String accountID, String userID, String name, AccountType accountType, double initialBalance, AccountCacher cacher) {
        this.accountID = accountID;
        this.userID = userID;
        this.name = name;
        this.initialBalance = initialBalance;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
        this.accountType = accountType;
        this.cacher = cacher;
    }

    /** Getter for userID */
    public String getUserID() {
        return userID;
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

    /** Getter for initial account balance */
    public double getInitialBalance() {
        return initialBalance;
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
    public boolean addTransaction(Transaction transaction, boolean cache) {
        // Add to list of transactions
        boolean result = transactions.add(transaction);

        if(result) {
            // Update account balance
            balance += transaction.signedAmount();

            if(cache) {
                // Cache transaction
                boolean cached = cacher.createTransaction(transaction);

                if(cached) {
                    System.out.println("Successfully cached new transaction to database");
                } else {
                    System.out.println("Failed to cache new transaction to database");
                }
            }
        }

        return result;
    }

    /** 
     * Removes a transaction from the account
     * @return boolean indicating if the transaction was successfully removed
     */
    public boolean removeTransaction(Transaction transaction, boolean cache) {
        // Remove from list of transactions
        boolean result = transactions.remove(transaction);

        if(result) {
            // Update account balance
            balance -= transaction.signedAmount();
            
            if(cache) {
                // Cache removal of transaction
                boolean cached = cacher.deleteTransaction(transaction);

                if(cached) {
                    System.out.println("Successfully cached removed transaction to database");
                } else {
                    System.out.println("Failed to cache removed transaction to database");
                }
            }
        }
        
        return result;
    }

    /** Edits a transaction */
    public void editTransaction(Transaction oldTransaction, Transaction newTransaction) {
        removeTransaction(oldTransaction, true);
        addTransaction(newTransaction, true);
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

    public interface AccountCacher {
        boolean createTransaction(Transaction transaction);
        boolean deleteTransaction(Transaction transaction);
    }
}
