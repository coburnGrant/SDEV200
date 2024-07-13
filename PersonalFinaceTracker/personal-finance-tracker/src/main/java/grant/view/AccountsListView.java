package grant.view;

import java.util.ArrayList;

import grant.App;
import grant.model.Account;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AccountsListView extends BorderPane {
    private final Text title;
    private final VBox accountList;

    public AccountsListView(ArrayList<Account> accounts) {
        // Setup the main layout
        setPadding(new Insets(20));
        setBackground(new Background(new BackgroundFill(App.PRIMARY_COLOR, null, null)));

        // Title for the accounts view
        title = App.titleText("Accounts");

        accountList = new VBox(20);
        accountList.setPadding(new Insets(20));
        accountList.setAlignment(Pos.TOP_LEFT);

        // Display each account
        for (Account account : accounts) {
            HBox accountDetails = new AccountRowView(account);
            accountDetails.setOnMouseClicked(e -> showAccountDetails(account));
            
            accountList.getChildren().add(accountDetails);
        }

        setTop(title);
        setCenter(accountList);
    }

    public VBox getAccountList() {
        return accountList;
    }

    private void showAccountDetails(Account account) {
        AccountDetailView detailView = new AccountDetailView(account);
        detailView.setOnBackButtonPressed(e -> closeAccountDetails());
        
        setTop(null);
        setCenter(detailView);
    }

    private void closeAccountDetails() {
        System.out.println("closing account details...");
        setTop(title);
        setCenter(accountList);
    }
}