package grant.view;

import grant.UIHelpers;
import grant.model.Transaction;
import grant.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class DashboardView extends BorderPane {
    private final User user;

    public DashboardView(User user) {
        this.user = user;
        initializeUI();
    }

    /** Initializes all UI elements in view */
    private void initializeUI() {
        setPadding(new Insets(20));
        setBackground(UIHelpers.PRIMARY_BACKGROUND);

        VBox dashboard = createDashboardContent();

        setCenter(dashboard);
    }

    /** Creates the dashboard UI */
    private VBox createDashboardContent() {
        VBox dashboard = new VBox(20);
        dashboard.setPadding(new Insets(20));
        dashboard.setAlignment(Pos.TOP_LEFT);

        // Create title
        Text title = UIHelpers.titleText("Dashboard");
        setTop(title);

        // Create greeting
        Text helloText = UIHelpers.createText(user.greetUser(), FontWeight.NORMAL, 20);
        dashboard.getChildren().add(helloText);

        // Create net worth text
        Text netWorthLabel = UIHelpers.primaryText("Net Worth:", 20);
        Text netWorthValue = UIHelpers.primaryText(user.getFormattedNetWorth(), 16);

        HBox netWorthBox = new HBox(10);
        netWorthBox.setAlignment(Pos.CENTER_LEFT);
        netWorthBox.getChildren().addAll(netWorthLabel, netWorthValue);
        dashboard.getChildren().add(netWorthBox);

        // Create recent transactions list
        VBox transactionBox = new VBox(10);
        transactionBox.setAlignment(Pos.CENTER_LEFT);

        Text transactionsTitle = UIHelpers.subtitleText("Recent Transactions");

        transactionBox.getChildren().add(transactionsTitle);

        for (Transaction transaction : user.getRecentTransactions()) {
            TransactionRowView row = new TransactionRowView(transaction);

            transactionBox.getChildren().add(row);
        }

        dashboard.getChildren().add(transactionBox);
        
        // Create accounts list 
        VBox accountsBox = new VBox(10);

        Text accountsTitle = UIHelpers.subtitleText("Accounts");

        VBox accountList = new AccountsListView(user).getAccountList();
        accountList.setPadding(new Insets(0));

        accountsBox.getChildren().addAll(accountsTitle, accountList);

        dashboard.getChildren().add(accountsBox);

        return dashboard;
    }
}