package grant;

import java.sql.SQLException;
import java.util.Date;

import grant.model.CheckingAccount;
import grant.model.SavingsAccount;
import grant.model.Transaction;
import grant.model.TransactionType;
import grant.model.User;
import grant.util.DatabaseUtil;
import grant.util.DatabaseUtil.LoginException;
import grant.view.AccountsListView;
import grant.view.DashboardView;
import grant.view.LoginView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {
    private Stage primaryStage;
    private static Scene primaryScene;
    private BorderPane rootLayout;

    private DatabaseUtil dbUtil;

    private User user;
    private User testUser;

    private HBox navBar;

    private Stage loginStage;

    @Override
    public void start(Stage primaryStage) {
        dbUtil = new DatabaseUtil();

        boolean isLoggedIn = false;

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Personal Finance Tracker");

        if (isLoggedIn) {
            showPrimaryStage();
        } else {
            showLoginView();
        }
    }

    private void showPrimaryStage() {
        initTestUser();
        initRootLayout();

        showDashboard();

        primaryScene = new Scene(rootLayout, 800, 600);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    /** Initializes the root layout */
    private void initRootLayout() {
        rootLayout = new BorderPane();

        // Set background color
        rootLayout.setBackground(UIHelpers.PRIMARY_BACKGROUND);

        // Create navbar
        createNavbar();

        rootLayout.setTop(navBar);
    }

    /** Initialized the navbar */
    private void createNavbar() {
        navBar = new HBox();
        navBar.setBackground(new Background(new BackgroundFill(UIHelpers.SECONDARY_COLOR, null, null)));
        navBar.setAlignment(Pos.CENTER);
        navBar.setSpacing(10);
        navBar.setPadding(new Insets(10));

        // Add buttons
        Button dashboardBtn = UIHelpers.createNavButton("Dashboard");
        Button accountsBtn = UIHelpers.createNavButton("Accounts");
        
        Button logoutBtn = UIHelpers.createNavButton("Logout");
        logoutBtn.setTextFill(Color.RED);
        Button deleteUserButton = UIHelpers.createNavButton("Delete User");
        deleteUserButton.setTextFill(Color.RED);

        // Add button actions
        dashboardBtn.setOnAction(e -> showDashboard());
        accountsBtn.setOnAction(e -> showAccounts());

        logoutBtn.setOnAction(e -> logoutUser());
        deleteUserButton.setOnAction(e -> deleteUser());

        navBar.getChildren().addAll(dashboardBtn, accountsBtn, logoutBtn, deleteUserButton);
    }

    /** Displays the dashboard */
    private void showDashboard() {
        DashboardView dashboard = new DashboardView(user);
        ScrollPane scrollPane = new ScrollPane();

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        scrollPane.setContent(dashboard);
        rootLayout.setCenter(scrollPane);
    }

    /** Displays account list view */
    private void showAccounts() {
        AccountsListView accountsView = new AccountsListView(user);
        ScrollPane scrollPane = new ScrollPane();

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        scrollPane.setContent(accountsView);
        rootLayout.setCenter(scrollPane);
    }

    /** Displays login/create account view */
    private void showLoginView() {
        LoginView loginView = new LoginView(
                (username, password) -> {
                    loginUser(username, password);
                },
                (newUser) -> {
                    createNewUser(newUser);
                });

        Scene loginScene = new Scene(loginView);

        loginStage = new Stage();
        loginStage.setTitle("Login to Personal Finance Tracker");
        loginStage.setScene(loginScene);
        loginStage.show();
    }

    private void loginUser(String username, String password) {
        System.out.println("logging in user " + username + " with password: " + password);

        try {
            User existingUser = dbUtil.loadUser(username, password);

            this.user = existingUser;
            loginStage.close();
            showPrimaryStage();

        } catch (LoginException e) {
            System.out.println("Error logging in user!! " + e.getMessage());
            displayLoginAlert(e);
        }
    }

    private void createNewUser(User newUser) {
        System.out.println("creating new user/n" + newUser);

        try {
            dbUtil.createUser(newUser);

            // Update the cacher
            newUser.setCacher(dbUtil);

            this.user = newUser;
            loginStage.close();
            showPrimaryStage();
        } catch (LoginException e) {
            System.out.println("Error creating new user!! " + e.getMessage());
            displayLoginAlert(e);
        }
    }

    private void logoutUser() {
        System.out.println("Logging out user!");
        this.user = null;
        primaryStage.close();
        showLoginView();
    }

    private void deleteUser() {
        System.out.println("Deleting user!");
        dbUtil.deleteUser(user.getUserID());
        logoutUser();
    }

    private void displayLoginAlert(LoginException exception) {
        Alert loginAlert = new Alert(AlertType.WARNING);
        loginAlert.setTitle("Login Error!");
        loginAlert.setHeaderText("Login Error!");
        loginAlert.setContentText(exception.getMessage());
        loginAlert.showAndWait();
    }

    /** Creates a test user for UI testing purposes */
    private void initTestUser() {
        testUser = new User("username", "password", "Grant", "Coburn", null);

        CheckingAccount checkingAccount = new CheckingAccount(testUser.getUserID(), "My Checking Account", 1000, null);
        SavingsAccount savingsAccount = new SavingsAccount(testUser.getUserID(), "My Savings Account", 4.5, 1000, null);

        // Adding transactions to Checking Account
        checkingAccount.addTransaction(new Transaction(checkingAccount.getAccountID(), "McDonalds", 10.99, new Date(), TransactionType.WITHDRAWAL), false);
        checkingAccount.addTransaction(new Transaction(checkingAccount.getAccountID(), "Gas Station", 30.50, new Date(), TransactionType.WITHDRAWAL), false);
        checkingAccount.addTransaction(new Transaction(checkingAccount.getAccountID(), "ATM Withdrawal", 100.00, new Date(), TransactionType.WITHDRAWAL), false);
        checkingAccount.addTransaction(new Transaction(checkingAccount.getAccountID(), "Transfer to Savings", 200.00, new Date(), TransactionType.WITHDRAWAL), false);
    
        // Adding transactions to Savings Account
        savingsAccount.addTransaction(new Transaction(savingsAccount.getAccountID(), "Paycheck", 500, new Date(), TransactionType.DEPOSIT), false);
        savingsAccount.addTransaction(new Transaction(savingsAccount.getAccountID(), "Interest", 20.25, new Date(), TransactionType.DEPOSIT), false);
        savingsAccount.addTransaction(new Transaction(savingsAccount.getAccountID(), "Online Purchase", 150.75, new Date(), TransactionType.WITHDRAWAL), false);
        savingsAccount.addTransaction(new Transaction(savingsAccount.getAccountID(), "Emergency Fund Deposit", 300, new Date(), TransactionType.DEPOSIT), false);
    
        // Adding accounts to the user
        testUser.addAccount(checkingAccount, false);
        testUser.addAccount(savingsAccount, false);
    }

    public static void main(String[] args) {
        launch();
    }
}