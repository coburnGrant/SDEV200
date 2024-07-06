package FinalProject.src;

/** Class to represent a checking account */
public class CheckingAccount extends Account {

    /** Constructor for a checking account */
    public CheckingAccount(String userID, String name) {
        super(userID, name, AccountType.CHECKING);
    }
}
