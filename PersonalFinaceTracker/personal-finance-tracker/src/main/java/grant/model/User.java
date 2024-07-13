package grant.model;

import java.util.ArrayList;
import java.util.UUID;

public class User {
    /** User's id */
    private final String userID;

    /** User's first name */
    private String firstName;

    /** User's last name */
    private String lastName;

    /** List of the user's accounts */
    private final ArrayList<Account> accounts;

    /** Constructor for a user */
    public User(String firstName, String lastName) {
        this.userID = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.accounts = new ArrayList<>();
    }

    /** Getter for userID */
    public String getUserID() {
        return userID;
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
     * Adds an account to the user's list of accounts
     * @return a boolean indicating if the account was successfully added 
     */
    public boolean addAccount(Account account) {
        return accounts.add(account);
    }

    /**
     * Removes an account from the user's list of accounts
     * @return a boolean indicating if the account was successfully removed
     */
    public boolean removeAccount(Account account) {
        return accounts.remove(account);
    }

    /** Gets the user's first and last name combined */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /** Gets the user's net worth by adding up all the balances in user's accounts */
    public double getNetWorth() {
        double netWorth = 0.0;

        for(Account account : accounts) {
            netWorth += account.getBalance();
        }

        return netWorth;
    }

    public String getFormattedNetWorth() {
        return Transaction.formatDoubleToMoney(getNetWorth());
    }

    /** Returns a string object representation of the user */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User {\n");
        sb.append("   User ID='").append(userID).append("'\n");
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
}