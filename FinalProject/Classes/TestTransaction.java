package FinalProject.Classes;

import java.util.Date;

public class TestTransaction {
    public static final Transaction mcDonaldsTransaction = new Transaction("accountID", "McDonalds", 9.99, new Date(), TransactionType.WITHDRAWAL);
    public static final Transaction payDayTransaction = new Transaction("accountID", "Payday", 500, new Date(), TransactionType.DEPOSIT);
    public static final Transaction[] transactions = { mcDonaldsTransaction, payDayTransaction };
    
    public static void main(String[] args) {
        for(Transaction transaction : transactions) {
            System.out.println(transaction.transactionDescription());
            System.out.println(transaction.toString());
            System.out.println("Transaction type: " + transaction.getType().toString());
        }
    }
}
