package grant.view;

import java.util.ArrayList;

import grant.UIHelpers;
import grant.model.Account;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AccountsListView extends BorderPane {
    private final Text title;
    private final ArrayList<Account> accounts;
    private final VBox accountList;

    public AccountsListView(ArrayList<Account> accounts) {
        this.accounts = accounts;
        setPadding(new Insets(20));
        setBackground(UIHelpers.PRIMARY_BACKGROUND);

        title = UIHelpers.titleText("Accounts");

        accountList = new VBox(20);
        accountList.setPadding(new Insets(20));
        accountList.setAlignment(Pos.TOP_LEFT);

        addAccounts();

        setTop(title);
        setCenter(accountList);
    }

    /** Gets a the list of accounts in a VBox */
    public VBox getAccountList() {
        return accountList;
    }

    /** Adds list of accounts rows to list */
    private void addAccounts() {
        for (Account account : accounts) {
            AccountRowView accountDetails = new AccountRowView(account);
            
            // Set on row clicked
            accountDetails.setOnRowClicked(e -> showAccountDetails(account));
            
            accountList.getChildren().add(accountDetails);
        }
    }

    /** Displays the account detail view */
    private void showAccountDetails(Account account) {
        AccountDetailView detailView = new AccountDetailView(account);
        detailView.setOnBackButtonPressed(e -> closeAccountDetails());
        
        setTop(null);
        setCenter(detailView);
    }

    /** Closes account detail view */
    private void closeAccountDetails() {
        System.out.println("closing account details...");
        setTop(title);
        setCenter(accountList);
    }
}