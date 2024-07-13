package grant.model;

public class SavingsAccount extends Account {
    /** Interest rate this savings account has */
    private double interestRate;

    /** Constructor for a savings account */
    public SavingsAccount(String userID, String name, double interestRate, double initialBalance) {
        super(userID, name, AccountType.SAVINGS, initialBalance);
        
        this.interestRate = interestRate;
    }

    /** Getter for interest rate */
    public double getInterestRate() {
        return interestRate;
    }

    /** Setter for interest rate */
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}
