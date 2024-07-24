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

    /** Cacher that handle's caching the account info to the database */
    private final AccountCacher cacher;

    /** Constructor for New Account */
    public Account(String userID, String name, AccountType accountType, double initialBalance, AccountCacher cacher) {
        this(UUID.randomUUID().toString(), userID, name, accountType, initialBalance, cacher);
    }

    /** Constructor for Existing Account */
    public Account(String accountID, String userID, String name, AccountType accountType, double initialBalance,
            AccountCacher cacher) {
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
    public String getAccountID() {
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
     * Adds a transaction to the account.
     * If the transaction is successfully added and caching is enabled, it caches
     * the new transaction in the database.
     * 
     * @param account the account to be added to the list
     * @param cache   indicates if this new transaction should be cached in the
     *                database
     * @return a boolean indicating if the transaction was successfully added to the
     *         list
     */
    public boolean addTransaction(Transaction transaction, boolean cache) {
        // Add to list of transactions
        boolean result = transactions.add(transaction);

        if (result) {
            // Update account balance
            balance += transaction.signedAmount();

            if (cache) {
                // Cache transaction
                boolean cached = cacher.createTransaction(transaction);

                if (cached) {
                    System.out.println("Successfully cached new transaction to database");
                } else {
                    System.out.println("Failed to cache new transaction to database");
                }
            }
        }

        return result;
    }

    /**
     * Removes a transaction to the account.
     * If the transaction is removed added and caching is enabled, it caches the
     * removed transaction in the database.
     * 
     * @param account the account to be added to the list
     * @param cache   indicates if this new transaction should be cached in the
     *                database
     * @return a boolean indicating if the transaction was successfully removed from
     *         the list
     */
    public boolean removeTransaction(Transaction transaction, boolean cache) {
        // Remove from list of transactions
        boolean result = transactions.remove(transaction);

        if (result) {
            // Update account balance
            balance -= transaction.signedAmount();

            if (cache) {
                // Cache removal of transaction
                boolean cached = cacher.deleteTransaction(transaction.getTransactionID());

                if (cached) {
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

    /**
     * toString method for account. Returns an object representation of the account
     */
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

    /**
     * The AccountCacher interface provides methods for caching account transactions
     * somewhere like a database.
     */
    public interface AccountCacher {

        /**
         * Caches a new transaction.
         * 
         * @param transaction the transaction to be cached
         * @return a boolean indicating if the transaction was cached
         */
        boolean createTransaction(Transaction transaction);

        /**
         * Deletes a cached transaction.
         * 
         * @param transactionID the ID of the transaction to be deleted
         * @return a boolean indicating if the transaction was deleted.
         */
        boolean deleteTransaction(String transactionID);
    }
}
