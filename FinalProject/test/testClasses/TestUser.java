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

        boolean addCheckingAccountResult = myUser.addAccount(checkingAccount);
        String checkingResultText = addCheckingAccountResult ? "Successfully added" : "Failed to add";
        System.out.println(checkingResultText + " " + checkingAccount.getName());

        boolean addSavingsAccountResult = myUser.addAccount(savingsAccount);
        String savingsResultText = addSavingsAccountResult ? "Successfully added" : "Failed to add";
        System.out.println(savingsResultText + " " + savingsAccount.getName());

        printUser();

        System.out.println("removing account...");

        boolean removeAccountResult = myUser.removeAccount(savingsAccount);
        String removeResultText = removeAccountResult ? "Successfully removed" : "Failed to remove";
        System.out.println(removeResultText + " " + savingsAccount.getName());

        printUser();
    }

    public static void printUser() {
        System.out.println(myUser);
    }
}
