package FinalProject.src;

public class CheckingAccount extends Account {
    
    public CheckingAccount(String userID, String name) {
        super(userID, name, AccountType.CHECKING);
    }
}