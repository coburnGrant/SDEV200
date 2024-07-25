package grant.model;

/** Class to represent a checking account */
public class CheckingAccount extends Account {

    /** Constructor for a checking account */
    public CheckingAccount(String userID, String name, double initialBalance, AccountCacher cacher) {
        super(userID, name, AccountType.CHECKING, initialBalance, cacher);
    }

    public CheckingAccount(String accountID, String userID, String name, double initialBalance, AccountCacher cacher) {
        super(accountID, userID, name, AccountType.CHECKING, initialBalance, cacher);
    }
}
