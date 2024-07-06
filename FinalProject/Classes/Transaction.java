package FinalProject.Classes;

import java.util.Date;
import java.util.UUID;

public class Transaction {
    private final String transactionID;
    private final String accountID;

    private final String description;
    private final double amount;
    private final Date date;
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
        return "Transaction {\n" +
                "    Transaction ID='" + transactionID + "'\n" +
                "    Account ID='" + accountID + "'\n" +
                "    Description='" + description + "'\n" +
                "    Amount=" + getAmountDescription() + "\n" +
                "    Date=" + date + "\n" +
                "    Type=" + type + "\n" +
                "}";
    }

    /** Returns the double value formatted into standard money formatting. Ex. $4.50 */
    public static String formatDoubleToMoney(double amount) {
        return "$" + String.format("%.2f", amount);
    }
}
