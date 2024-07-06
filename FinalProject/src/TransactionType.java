package FinalProject.src;

/** Enum for respresenting a type of transaction */
public enum TransactionType {
    /** A transaction that is a deposit into an account */
    DEPOSIT("Deposit"),

    /** A transaction that is a withdrawal from an account */
    WITHDRAWAL("Withdrawal");

    /** Textual representation of the type of transaction */    
    private final String description;

    TransactionType(String description) {
        this.description = description;
    }

    /** Getter for transaction type's description */
    public String getDescription() {
        return description;
    }

    /** @return the transaction type's description */
    @Override
    public String toString() {
        return description;
    }
}