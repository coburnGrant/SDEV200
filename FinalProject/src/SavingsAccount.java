package FinalProject.src;

public class SavingsAccount extends Account {
    /** Interest rate this savings account has */
    double interestRate;

    /** Constructor for a savings account */
    public SavingsAccount(String userID, String name, double interestRate) {
        super(userID, name, AccountType.SAVINGS);
        
        this.interestRate = interestRate;
    }
}
