package grant;

import java.util.Date;

import grant.model.CheckingAccount;
import grant.model.SavingsAccount;
import grant.model.Transaction;
import grant.model.TransactionType;
import grant.model.User;
import grant.view.AccountsListView;
import grant.view.DashboardView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class App extends Application {
    private Stage primaryStage;
    private static Scene scene;
    private BorderPane rootLayout;

    private User testUser;

    private HBox navBar;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Personal Finance Tracker");

        initTestUser();
        initRootLayout();
        showDashboard();

        scene = new Scene(rootLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /** Initializes the root layout */
    private void initRootLayout() {
        rootLayout = new BorderPane();

        // Set background color
        rootLayout.setBackground(new Background(new BackgroundFill(UIHelpers.PRIMARY_COLOR, null, null)));

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

        // Add button actions
        dashboardBtn.setOnAction(e -> showDashboard());
        accountsBtn.setOnAction(e -> showAccounts());

        navBar.getChildren().addAll(dashboardBtn, accountsBtn);
    }

    /** Displays the dashboard */
    private void showDashboard() {
        DashboardView dashboard = new DashboardView(testUser);
        ScrollPane scrollPane = new ScrollPane();
    
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        
        scrollPane.setContent(dashboard);
        rootLayout.setCenter(scrollPane);
    }

    /** Displays account list view */
    private void showAccounts() {
        AccountsListView accountsView = new AccountsListView(testUser);
        ScrollPane scrollPane = new ScrollPane();
    
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        
        scrollPane.setContent(accountsView);
        rootLayout.setCenter(scrollPane);
    }

    /** Creates a test user for UI testing purposes */
    private void initTestUser() {
        testUser = new User("Grant", "Coburn");
    
        CheckingAccount checkingAccount = new CheckingAccount(testUser.getUserID(), "My Checking Account", 1000);
        SavingsAccount savingsAccount = new SavingsAccount(testUser.getUserID(), "My Savings Account", 4.5, 1000);
    
        // Adding transactions to Checking Account
        checkingAccount.addTransaction(new Transaction(checkingAccount.getAccountID(), "McDonalds", 10.99, new Date(), TransactionType.WITHDRAWAL));
        checkingAccount.addTransaction(new Transaction(checkingAccount.getAccountID(), "Gas Station", 30.50, new Date(), TransactionType.WITHDRAWAL));
        checkingAccount.addTransaction(new Transaction(checkingAccount.getAccountID(), "ATM Withdrawal", 100.00, new Date(), TransactionType.WITHDRAWAL));
        checkingAccount.addTransaction(new Transaction(checkingAccount.getAccountID(), "Transfer to Savings", 200.00, new Date(), TransactionType.WITHDRAWAL));
    
        // Adding transactions to Savings Account
        savingsAccount.addTransaction(new Transaction(savingsAccount.getAccountID(), "Paycheck", 500, new Date(), TransactionType.DEPOSIT));
        savingsAccount.addTransaction(new Transaction(savingsAccount.getAccountID(), "Interest", 20.25, new Date(), TransactionType.DEPOSIT));
        savingsAccount.addTransaction(new Transaction(savingsAccount.getAccountID(), "Online Purchase", 150.75, new Date(), TransactionType.WITHDRAWAL));
        savingsAccount.addTransaction(new Transaction(savingsAccount.getAccountID(), "Emergency Fund Deposit", 300, new Date(), TransactionType.DEPOSIT));
    
        // Adding accounts to the user
        testUser.addAccount(checkingAccount);
        testUser.addAccount(savingsAccount);
    }

    public static void main(String[] args) {
        launch();
    }
}