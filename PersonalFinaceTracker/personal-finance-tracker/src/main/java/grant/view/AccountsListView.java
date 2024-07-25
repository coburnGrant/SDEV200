package grant.view;

import grant.UIHelpers;
import grant.model.Account;
import grant.model.User;
import grant.model.UserObserver;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AccountsListView extends BorderPane implements UserObserver {
    private final User user;
    private final Text title;

    private final VBox accountList;
    private final VBox accountDetails;
    
    private CreateAccountView createAccountView;
    
    public AccountsListView(User user) {
        this.user = user;
        user.addObserver(this);

        setPadding(new Insets(20));
        setBackground(UIHelpers.PRIMARY_BACKGROUND);

        title = UIHelpers.titleText("Accounts");

        accountList = new VBox(20);
        accountList.setPadding(new Insets(20));
        accountList.setAlignment(Pos.TOP_LEFT);

        Button createNewAccountBtn = UIHelpers.createSimpleButton("Create new account +");
        createNewAccountBtn.setOnAction(e -> displayCreateNewAccountPanel());

        accountDetails = new VBox(20);
        accountDetails.setPadding(new Insets(20));
        accountDetails.setAlignment(Pos.TOP_LEFT);

        addAccounts();

        accountDetails.getChildren().addAll(createNewAccountBtn, accountList);

        setTop(title);
        setCenter(accountDetails);
    }

    /** Gets a the list of accounts in a VBox */
    public VBox getAccountList() {
        return accountList;
    }

    /** Adds list of accounts rows to list */
    private void addAccounts() {
        accountList.getChildren().clear();

        for (Account account : user.getAccounts()) {
            AccountRowView accountRow = new AccountRowView(account);
            
            // Set on row clicked
            accountRow.setOnRowClicked(e -> showAccountDetails(account));
            
            accountList.getChildren().add(accountRow);
        }
    }

    /** Displays the account detail view */
    private void showAccountDetails(Account account) {
        AccountDetailView detailView = new AccountDetailView(account, e -> closeAccountDetails(), e -> deleteAccount(account));
        
        setTop(null);
        setCenter(detailView);
    }

    /** Closes account detail view */
    private void closeAccountDetails() {
        System.out.println("closing account details...");

        // Refresh account list incase balance changed
        addAccounts();

        setTop(title);
        setCenter(accountDetails);
    }

    private void deleteAccount(Account account) {
        System.out.println("Deleting account! \n" + account);
        boolean result = user.removeAccount(account);
        System.out.println("Removed account: " + result);

        closeAccountDetails();
    }

    private void displayCreateNewAccountPanel() {
        System.out.println("new account button clicked!");
        
        createAccountView = new CreateAccountView(user, e -> createAccount());

        Scene scene = new Scene(createAccountView, 400, 200);
        Stage stage = new Stage();
        stage.setTitle("Create New Account");
        stage.setScene(scene);
        stage.show();
    }

    private void createAccount() {
        Account newAccount = createAccountView.getAccount();

        System.out.println("Creating new account: \n" + newAccount);

        user.addAccount(newAccount, true);
    }

    @Override
    public void update() {
        addAccounts();
    }
}