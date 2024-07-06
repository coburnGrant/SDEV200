package FinalProject.Classes;

import java.util.ArrayList;
import java.util.UUID;

public class User {
    private final String userID;

    private String firstName;
    private String lastName;

    private final ArrayList<Account> accounts;

    public User(String firstName, String lastName) {
        this.userID = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.accounts = new ArrayList<>();
    }

    public String getUserID() {
        return userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public double getNetWorth() {
        double netWorth = 0.0;

        for(Account account : accounts) {
            netWorth += account.getBalance();
        }

        return netWorth;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User {");
        sb.append("User ID='").append(userID).append('\'');
        sb.append(", First Name='").append(firstName).append('\'');
        sb.append(", Last Name='").append(lastName).append('\'');
        sb.append(", Full Name='").append(getFullName()).append('\'');
        sb.append(", Net Worth=").append(getNetWorth());
        sb.append(", Accounts=[");

        for (Account account : accounts) {
            sb.append(account.toString()).append(", ");
        }

        if (!accounts.isEmpty()) {
            sb.setLength(sb.length() - 2); // Remove trailing comma and space
        }

        sb.append("]}");
        return sb.toString();
    }
}