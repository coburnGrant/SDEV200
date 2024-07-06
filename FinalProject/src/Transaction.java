package FinalProject.src;

import java.util.Date;
import java.util.UUID;

public class Transaction {
    /** Transaction's id */
    private final String transactionID;

    /** Associated account id to this transaction */
    private final String accountID;

    /** Main description of this transaction */
    private final String description;
    
    /** Dollar amount of this transaction */
    private final double amount;

    /** Date of this transaction */
    private final Date date;
    
    /** Type of transaction */
    private final TransactionType type;

    /** Constructor for Transaction */
    public Transaction(String accountID, String description, double amount, Date date, TransactionType type) {
        this.transactionID = UUID.randomUUID().toString();
        this.accountID = accountID;

        this.description = description;
        this.amount = amount;
        this.date = date;
        this.type = type;
    }

    /** Getter for transactionID */
    public String getTransactionID() {
        return transactionID;
    }

    /** Getter for transaction date */
    public Date getDate() {
        return date;
    }

    /** Getter for transaction type */
    public TransactionType getType() {
        return type;
    }

    /** Getter for transaction amount */
    public double getAmount() {
        return amount;
    }

    /** Getter for transaction description */
    public String getDescription() {
        return description;
    }

    /** Returns the signed amount for the transaction. If withdrawal, negative amount is negative */
    public double signedAmount() {
        if(type == TransactionType.DEPOSIT) {
            return amount;
        } else {
            return amount * -1.0;
        }
    }

    /** Returns a string representing the signed transaction amount with standard money formatting */
    public String getAmountDescription() {
        String sign = type == TransactionType.WITHDRAWAL ? "-" : "";
        return sign + Transaction.formatDoubleToMoney(amount);
    }

    /** Returns a simple transaction description. "(description) - $(amount). At (date)"" */
    public String transactionDescription() {
        return description + "- " + getAmountDescription() + ". At " + date.toString();
    }

    /** Overridden toString method to represent the transaction object and its properties. */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Transaction { \n");
        sb.append("    Transaction ID='").append(transactionID).append("'\n");
        sb.append("    Account ID='").append(accountID).append("'\n");
        sb.append("    Description='").append(description).append("'\n");
        sb.append("    Amount='").append(getAmountDescription()).append("'\n");
        sb.append("    Date='").append(date).append("'\n");
        sb.append("    Type='").append(type).append("'\n");
        sb.append("}");

        return sb.toString();
    }

    /** Returns the double value formatted into standard money formatting. Ex. $4.50 */
    public static String formatDoubleToMoney(double amount) {
        return "$" + String.format("%.2f", amount);
    }
}
