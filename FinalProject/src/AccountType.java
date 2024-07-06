package FinalProject.src;

/** Enum for representing a type of account */
public enum AccountType {
    /** An account that is a checking account */
    CHECKING("Checking Account"),

    /** An account that is a savings account */
    SAVINGS("Savings Account"),

    /** An account that is used for investing */
    INVESTMENT("Investment Account");

    /** Textual representation of the type of account */
    private final String description;

    AccountType(String description) {
        this.description = description;
    }

    /** Getter for account type description */
    public String getDescription() {
        return description;
    }

    /** @return The description for the account type */
    @Override
    public String toString() {
        return description;
    }
}