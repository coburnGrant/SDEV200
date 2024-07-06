package FinalProject.src;

public enum AccountType {
    CHECKING("Checking Account"),
    SAVINGS("Savings Account"),
    INVESTMENT("Investment Account"),
    DEFAULT("Default Account");

    private final String description;

    AccountType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}