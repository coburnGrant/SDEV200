package grant.model;

/** Class to represent a checking account */
public class CheckingAccount extends Account {

    /** Constructor for a checking account */
    public CheckingAccount(String userID, String name, double initialBalance) {
        super(userID, name, AccountType.CHECKING, initialBalance);
    }
}
