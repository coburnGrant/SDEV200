package grant.view;

import grant.App;
import grant.model.Account;
import grant.model.Transaction;
import grant.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DashboardView extends BorderPane {
    private final User user;

    public DashboardView(User user) {
        this.user = user;
        initializeUI();
    }

    private void initializeUI() {
        setPadding(new Insets(20));
        setBackground(new Background(new BackgroundFill(App.PRIMARY_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));

        VBox dashboard = createDashboardContent();
        setCenter(dashboard);
    }

    private VBox createDashboardContent() {
        VBox dashboard = new VBox(20);
        dashboard.setPadding(new Insets(20));
        dashboard.setAlignment(Pos.TOP_CENTER);

        Text title = App.titleText("Dashboard");
        setTop(title);

        // Display net worth
        Text netWorthLabel = App.primaryText("Net Worth:", 20);

        Text netWorthValue = App.primaryText(user.getFormattedNetWorth(), 16);

        HBox netWorthBox = new HBox(10);
        netWorthBox.setAlignment(Pos.CENTER_LEFT);
        netWorthBox.getChildren().addAll(netWorthLabel, netWorthValue);
        dashboard.getChildren().add(netWorthBox);

        // Display recent transactions
        VBox transactionBox = new VBox(10);
        transactionBox.setAlignment(Pos.CENTER_LEFT);

        Text transactionsTitle = App.subtitleText("Recent Transactions");

        transactionBox.getChildren().add(transactionsTitle);

        // Populate with transactions
        for (Account account : user.getAccounts()) {
            for (Transaction transaction : account.getTransactions()) {
                TransactionRowView row = new TransactionRowView(transaction);

                row.setRowClicked(e -> showTransaction(transaction));

                transactionBox.getChildren().add(row);
            }
        }

        dashboard.getChildren().add(transactionBox);
        
        VBox accountsBox = new VBox(10);
        accountsBox.setAlignment(Pos.CENTER_LEFT);

        Text accountsTitle = App.subtitleText("Accounts");
        
        VBox accountList = new AccountsListView(user.getAccounts()).getAccountList();
        accountList.setPadding(new Insets(0));

        accountsBox.getChildren().addAll(accountsTitle, accountList);

        dashboard.getChildren().add(accountsBox);

        return dashboard;
    }

    private void showTransaction(Transaction transaction) {
        System.out.println("Showing transaction " + transaction.getDescription());
    }
}