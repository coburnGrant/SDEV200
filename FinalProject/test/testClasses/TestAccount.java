package FinalProject.test.testClasses;

import FinalProject.src.Account;
import FinalProject.src.CheckingAccount;
import FinalProject.src.SavingsAccount;

public class TestAccount {
    public static final SavingsAccount mySavingsAccount = new SavingsAccount("someUserID", "GS Bank Savings", 4.5);
    public static final CheckingAccount myCheckingAccount = new CheckingAccount("someUserID", "Chase Bank checking account");

    public static final Account[] accounts = { mySavingsAccount, myCheckingAccount };
    public static void main(String[] args) {
        printAccounts();

        System.out.println("adding transactions...");
        mySavingsAccount.addTransaction(TestTransaction.payDayTransaction);
        myCheckingAccount.addTransaction(TestTransaction.mcDonaldsTransaction);
        
        printAccounts();

        System.out.println("removing transactions...");
        mySavingsAccount.removeTransaction(TestTransaction.payDayTransaction);
        myCheckingAccount.removeTransaction(TestTransaction.mcDonaldsTransaction);

        printAccounts();
    }

    public static void printAccounts() {
        System.out.println("Accounts...");
        for(Account account : accounts) {
            System.out.println(account);
        }
    }
}
