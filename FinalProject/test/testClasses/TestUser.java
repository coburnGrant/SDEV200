package FinalProject.test.testClasses;

import java.util.Date;

import FinalProject.src.CheckingAccount;
import FinalProject.src.SavingsAccount;
import FinalProject.src.Transaction;
import FinalProject.src.TransactionType;
import FinalProject.src.User;

public class TestUser {
    public static final User myUser = new User("Grant", "Coburn");

    public static void main(String[] args) {
        printUser();

        System.out.println("Hello! " + myUser.getFullName());

        System.out.println("adding accounts...");

        CheckingAccount checkingAccount = new CheckingAccount(myUser.getUserID(), "Chase Bank");
        SavingsAccount savingsAccount = new SavingsAccount(myUser.getUserID(), "Capital One", 4.5);

        Transaction checkingTransaction = new Transaction(checkingAccount.getAccountID(), "Payday!", 1000, new Date(), TransactionType.DEPOSIT);
        Transaction savingsTransaction = new Transaction(savingsAccount.getAccountID(), "Payday!", 1000, new Date(), TransactionType.DEPOSIT);

        checkingAccount.addTransaction(checkingTransaction);
        savingsAccount.addTransaction(savingsTransaction);

        myUser.addAccount(checkingAccount);
        myUser.addAccount(savingsAccount);

        printUser();

        System.out.println("removing account...");

        myUser.removeAccount(savingsAccount);

        printUser();
    }

    public static void printUser() {
        System.out.println(myUser);
    }
}
