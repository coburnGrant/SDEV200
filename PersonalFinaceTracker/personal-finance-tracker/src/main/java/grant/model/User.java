package grant.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import grant.model.Account.AccountCacher;

public class User {
    /** User's id */
    private final String userID;

    /** User's username */
    private final String username;

    /** User's password */
    private final String password;

    /** User's first name */
    private String firstName;

    /** User's last name */
    private String lastName;

    /** List of the user's accounts */
    private final ArrayList<Account> accounts;

    /** List of observers for this user */
    private ArrayList<UserObserver> observers;

    /** Cacher that handles caching the user info to the database */
    private UserCacher cacher;

    /** Constructor for a new user */
    public User(String username, String password, String firstName, String lastName, UserCacher cacher) {
        this(UUID.randomUUID().toString(), username, password, firstName, lastName, cacher);
    }

    /** Constructor for an existing user */
    public User(String userID, String username, String password, String firstName, String lastName, UserCacher cacher) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accounts = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.cacher = cacher;
    }

    /** Getter for userID */
    public String getUserID() {
        return userID;
    }

    /** Getter for username */
    public String getUsername() {
        return username;
    }

    /** Getter for password */
    public String getPassword() {
        return password;
    }

    /** Getter for user firstName */
    public String getFirstName() {
        return firstName;
    }

    /** Setter for user first name */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /** Getter for user last name */
    public String getLastName() {
        return lastName;
    }

    /** Setter for user last name */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /** Getter for user accounts */
    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    /**
     * Adds an account to the user's list of accounts.
     * If the account is successfully added and caching is enabled, it caches the
     * new account in the database.
     * 
     * @param account the account to be added to the list
     * @param cache   indicates if this new account should be cached in the database
     * @return a boolean indicating if the account was successfully added to the
     *         list
     */
    public boolean addAccount(Account account, boolean cache) {
        boolean result = accounts.add(account);

        if (result && cache) {
            boolean cached = cacher.createAccount(account);

            if (cached) {
                System.out.println("Successfully cached new account to database");
            } else {
                System.out.println("Failed to cache new account to database");
            }
        }

        notifyObservers();
        return result;
    }

    /**
     * Removes an account from the user's list of account
     * If the account is successfully removed, it caches the removal of the account
     * in the database.
     * 
     * @return a boolean indicating if the account was successfully removed
     */
    public boolean removeAccount(Account account) {
        boolean result = accounts.remove(account);

        if (result) {
            cacher.deleteAccount(account.getAccountID());
        }

        notifyObservers();
        return result;
    }

    /** Gets the user's first and last name combined */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /** Greets the user. "Hello, `firstName lastName`!" */
    public String greetUser() {
        return "Hello, " + getFullName() + "!";
    }

    /**
     * Gets the user's net worth by adding up all the balances in user's accounts
     */
    public double getNetWorth() {
        double netWorth = 0.0;

        for (Account account : accounts) {
            netWorth += account.getBalance();
        }

        return netWorth;
    }

    /** Gets a list of the user's transactions within the last week. */
    public ArrayList<Transaction> getRecentTransactions() {
        ArrayList<Transaction> recent = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7); // a week ago
        Date dateThreshold = calendar.getTime();

        for (Account account : accounts) {
            for (Transaction transaction : account.getTransactions()) {
                if (transaction.getDate().after(dateThreshold)) {
                    recent.add(transaction);
                }
            }
        }

        return recent;
    }

    /** Returns the user's net worth in standard money format */
    public String getFormattedNetWorth() {
        return Transaction.formatDoubleToMoney(getNetWorth());
    }

    /** Adds observer to user */
    public void addObserver(UserObserver observer) {
        observers.add(observer);
    }

    /** Removes observer from user */
    public void removeObserver(UserObserver observer) {
        observers.remove(observer);
    }

    /** Notifies observes of updates */
    private void notifyObservers() {
        for (UserObserver observer : observers) {
            observer.update();
        }
    }

    /** Returns a string object representation of the user */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User {\n");
        sb.append("   User ID='").append(userID).append("'\n");
        sb.append("   Username='").append(username).append("'\n");
        sb.append("   Password='").append(password).append("'\n");
        sb.append("   First Name='").append(firstName).append("'\n");
        sb.append("   Last Name='").append(lastName).append("'\n");
        sb.append("   Full Name='").append(getFullName()).append("'\n");
        sb.append("   Net Worth=").append(getNetWorth()).append("\n");
        sb.append("   Accounts=[");

        if (!accounts.isEmpty()) {
            sb.append("\n");

            for (Account account : accounts) {
                sb.append("      ").append(account.toString().replace("\n", "\n      ")).append(",\n");
            }

            sb.setLength(sb.length() - 2); // Remove trailing comma and newline

            sb.append("\n   ]\n");
        } else {
            sb.append("]\n");
        }

        sb.append("}");
        return sb.toString();
    }

    /** Getter for the cacher */
    public UserCacher getCacher() {
        return cacher;
    }

    /** Setter for the cacher */
    public void setCacher(UserCacher cacher) {
        this.cacher = cacher;
    }

    /**
     * The UserCacher interface extends the AccountCacher interface and provides
     * additional methods
     * for caching user accounts and user information, such as in a database.
     */
    public interface UserCacher extends AccountCacher {

        /**
         * Caches a new account.
         * 
         * @param account the account to be cached
         * @return a boolean indicating if the account was cached
         */
        boolean createAccount(Account account);

        /**
         * Deletes a cached account.
         * 
         * @param accountID the ID of the account to be deleted
         * @return a boolean indicating if the account was deleted
         */
        boolean deleteAccount(String accountID);

        /**
         * Deletes a cached user and their associated data.
         * 
         * @param userID the ID of the user to be deleted
         * @return a boolean indicating if the user was deleted
         */
        boolean deleteUser(String userID);
    }
}