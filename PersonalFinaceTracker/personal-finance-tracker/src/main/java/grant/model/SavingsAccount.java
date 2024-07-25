package grant.model;

public class SavingsAccount extends Account {
    /** Interest rate this savings account has */
    private double interestRate;

    /** Constructor for a new savings account */
    public SavingsAccount(String userID, String name, double interestRate, double initialBalance, AccountCacher cacher) {
        super(userID, name, AccountType.SAVINGS, initialBalance, cacher);
        
        this.interestRate = interestRate;
    }

    /** Constructor for an existing savings account */
    public SavingsAccount(String accountID, String userID, String name, double interestRate, double initialBalance, AccountCacher cacher) {
        super(accountID, userID, name,  AccountType.SAVINGS, initialBalance, cacher);
    }

    /** Getter for interest rate */
    public double getInterestRate() {
        return interestRate;
    }

    /** Setter for interest rate */
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /** Gets the interest rate in a formatted % format. */
    public String getInterestRateDescription() {
        return interestRate + "%";
    }
}
