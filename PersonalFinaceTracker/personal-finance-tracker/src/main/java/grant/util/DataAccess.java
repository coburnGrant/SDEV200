package grant.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import grant.exception.LoginException;
import grant.model.Account;
import grant.model.AccountType;
import grant.model.CheckingAccount;
import grant.model.SavingsAccount;
import grant.model.Transaction;
import grant.model.TransactionType;
import grant.model.User;
import grant.model.User.UserCacher;

public class DataAccess implements UserCacher {
    private Connection connection;

    public DataAccess() {
        try {
            this.connection = getConnection();
        } catch (SQLException e) {
            System.out.println("Error establishing connection to DB");
            e.printStackTrace();
        }
    }

    /** Establishes connection to the database. */
    private Connection getConnection() throws SQLException {
        String url = getSqlUrl(DBProperties.HOST, DBProperties.PORT, DBProperties.DB_NAME);

        return DriverManager.getConnection(url, DBProperties.USER, DBProperties.PASSWORD);
    }

    // MARK: Querying

    /**
     * Attempts to find a user with the given user name and password. If found,
     * attempts to find all accounts associated with the user and all transactions
     * associated with those accounts to add them to the user
     */
    public User loadUser(String username, String password) throws LoginException {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                String userID = result.getString("userID");
                String fname = result.getString("firstName");
                String lname = result.getString("lastName");

                User user = new User(userID, username, password, fname, lname, this);

                // Get and add user's accounts
                for (Account account : accountsForUser(userID)) {
                    user.addAccount(account, false);
                }

                return user;
            } else {
                throw new LoginException("User not found or incorrect password");
            }
        } catch (SQLException e) {
            System.out.println("Error with loading user with username and password:");
            e.printStackTrace();

            throw new LoginException(e);
        }
    }

    /**
     * Attempts to get all the accounts associated with a userID and all of the
     * transaction
     * associated with those accounts.
     */
    public ArrayList<Account> accountsForUser(String userID) throws SQLException {
        String query = "SELECT * FROM accounts WHERE userID = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, userID);

        ResultSet result = preparedStatement.executeQuery();

        ArrayList<Account> accounts = new ArrayList<>();

        String accountID;
        String name;
        double balance;
        String accountType;
        double interestRate;

        while (result.next()) {

            accountID = result.getString("accountID");
            name = result.getString("name");
            balance = result.getDouble("balance");
            accountType = result.getString("accountType");
            interestRate = result.getDouble("interestRate");

            Account account;
            if (accountType.equals(AccountType.SAVINGS.getDescription())) {
                account = new SavingsAccount(accountID, userID, name, interestRate, balance, this);
            } else {
                account = new CheckingAccount(accountID, userID, name, balance, this);
            }

            // Load in and transactions for account
            for (Transaction transaction : transactionsForAccount(accountID)) {
                account.addTransaction(transaction, false);
            }

            accounts.add(account);
        }

        return accounts;
    }

    /** Attempts to get all of the transactions associated with an account. */
    public ArrayList<Transaction> transactionsForAccount(String accountID) throws SQLException {
        String query = "SELECT * FROM transactions WHERE accountID = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, accountID);

        ResultSet result = preparedStatement.executeQuery();

        ArrayList<Transaction> transactions = new ArrayList<>();

        String transactionID;
        String description;
        Double amount;
        Date date;
        String type;

        while (result.next()) {
            transactionID = result.getString("transactionID");
            description = result.getString("description");
            amount = result.getDouble("amount");
            date = result.getDate("Date");
            type = result.getString("type");

            TransactionType transactionType = type.equals(TransactionType.WITHDRAWAL.getDescription()) ? TransactionType.WITHDRAWAL : TransactionType.DEPOSIT;

            Transaction transaction = new Transaction(transactionID, accountID, description, amount, date, transactionType);

            transactions.add(transaction);
        }

        return transactions;
    }

    // MARK: Adding

    /** Attempts to insert a new user into the database */
    public void createUser(User user) throws LoginException {
        String query = "INSERT INTO users (userID, username, password, firstName, lastName) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getUserID());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getLastName());

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted < 1) {
                throw new LoginException("No user was added to the database");
            }
        } catch (SQLException e) {
            System.out.println("Error creating account:");
            e.printStackTrace();

            throw new LoginException(e);
        }
    }

    /** Attempts to insert a new account into the database */
    @Override
    public boolean createAccount(Account account) {
        String query = "INSERT INTO accounts (accountID, userID, name, balance, accountType, interestRate) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, account.getAccountID());
            preparedStatement.setString(2, account.getUserID());
            preparedStatement.setString(3, account.getName());
            preparedStatement.setDouble(4, account.getInitialBalance());
            preparedStatement.setString(5, account.getAccountType().getDescription());

            if (account instanceof SavingsAccount) {
                preparedStatement.setDouble(6, ((SavingsAccount) account).getInterestRate());
            } else {
                preparedStatement.setDouble(6, 0);
            }

            int rowsInserted = preparedStatement.executeUpdate();

            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("Error creating account:");
            e.printStackTrace();
        }

        return false;
    }

    /** Attempts to insert a transaction into the database */
    public boolean createTransaction(Transaction transaction) {
        String query = "INSERT INTO transactions (transactionID, accountID, description, amount, date, type) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, transaction.getTransactionID());
            preparedStatement.setString(2, transaction.getAccountID());
            preparedStatement.setString(3, transaction.getDescription());
            preparedStatement.setDouble(4, transaction.getAmount());
            preparedStatement.setDate(5, new java.sql.Date(transaction.getDate().getTime()));
            preparedStatement.setString(6, transaction.getType().getDescription());

            int rowsInserted = preparedStatement.executeUpdate();

            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("Error creating account:");
            e.printStackTrace();
        }

        return false;
    }

    // MARK: Deleting

    /**
     * r
     * Attempts to delete a user and all of the user's associated accounts along
     * with those accounts' associated transactions.
     */
    @Override
    public boolean deleteUser(String userID) {
        try {
            String deleteTransactionsSQL = "DELETE FROM transactions WHERE accountID IN (SELECT accountID FROM accounts WHERE userID = ?)";
            String deleteAccountsSQL = "DELETE FROM accounts WHERE userID = ?";
            String deleteUserSQL = "DELETE FROM users WHERE userID = ?";

            PreparedStatement deleteTransactionsStmt = connection.prepareStatement(deleteTransactionsSQL);
            PreparedStatement deleteAccountsStmt = connection.prepareStatement(deleteAccountsSQL);
            PreparedStatement deleteUserStmt = connection.prepareStatement(deleteUserSQL);

            // Delete transactions
            deleteTransactionsStmt.setString(1, userID);
            int transactionsDeleted = deleteTransactionsStmt.executeUpdate();

            // Delete accounts
            deleteAccountsStmt.setString(1, userID);
            int accountsDeleted = deleteAccountsStmt.executeUpdate();

            // Delete user
            deleteUserStmt.setString(1, userID);
            int usersDeleted = deleteUserStmt.executeUpdate();

            System.out.println("Transactions deleted: " + transactionsDeleted + "\nAccounts Deleted: " + accountsDeleted
                    + "\nUsers deleted: " + usersDeleted);

            return transactionsDeleted > 0 || accountsDeleted > 0 || usersDeleted > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting user");
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Attempts to delete an account, along with all of the accounts associated
     * transactions from the database.
     */
    @Override
    public boolean deleteAccount(String accountID) {
        try {

            String deleteTransactionsSQL = "DELETE FROM transactions WHERE accountID = ?";
            String deleteAccountSQL = "DELETE FROM accounts WHERE accountID = ?";

            PreparedStatement deleteTransactionsStmt = connection.prepareStatement(deleteTransactionsSQL);
            PreparedStatement deleteAccountStmt = connection.prepareStatement(deleteAccountSQL);

            // Delete transactions
            deleteTransactionsStmt.setString(1, accountID);
            int transactionsDeleted = deleteTransactionsStmt.executeUpdate();

            // Delete account
            deleteAccountStmt.setString(1, accountID);
            int accountsDeleted = deleteAccountStmt.executeUpdate();

            System.out.println("Transactions deleted: " + transactionsDeleted + "\nAccounts Deleted: " + accountsDeleted);

            return transactionsDeleted > 0 || accountsDeleted > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting account");
            e.printStackTrace();
        }

        return false;
    }

    /** Attempts to delete a transaction from the database . */
    @Override
    public boolean deleteTransaction(String transactionID) {
        try {

            String deleteTransactionSQL = "DELETE FROM transactions WHERE transactionID = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(deleteTransactionSQL);

            preparedStatement.setString(1, transactionID);

            int transactionsDeleted = preparedStatement.executeUpdate();

            System.out.println("Transactions deleted: " + transactionsDeleted);

            return transactionsDeleted > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting transaction");
            e.printStackTrace();
        }

        return false;
    }

    /** Helper function to format a SQL URL string */
    public static String getSqlUrl(String host, int port, String dbName) {
        return String.format("jdbc:mysql://%s:%d/%s", host, port, dbName);
    }
}
