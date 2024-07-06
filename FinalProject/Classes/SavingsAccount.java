package FinalProject.Classes;

public class SavingsAccount extends Account {
    double interestRate;

    public SavingsAccount(String userID, String name, double interestRate) {
        super(userID, name, AccountType.SAVINGS);
        
        this.interestRate = interestRate;
    }
}
