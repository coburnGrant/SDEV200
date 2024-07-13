package grant.view;

import grant.UIHelpers;
import grant.model.Account;
import grant.model.Transaction;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class AccountDetailView extends BorderPane {
    private final Account account;
    private final EventHandler<ActionEvent> backButtonHandler;
    private final EventHandler<ActionEvent> deleteAccountHandler;


    public AccountDetailView(Account account, EventHandler<ActionEvent> backButtonHandler, EventHandler<ActionEvent> deleteAccountHandler) {
        this.account = account;
        this.backButtonHandler = backButtonHandler;
        this.deleteAccountHandler = deleteAccountHandler;

        VBox accountDetails = createAccountDetails();

        setTop(createHeader());
        setCenter(accountDetails);
    }

    /** Creates main account detail view */
    private VBox createAccountDetails() {
        VBox accountDetails = new VBox(20);
        accountDetails.setPadding(new Insets(20));

        // Create balance text
        Text subtitle = UIHelpers.subtitleText("Balance:");

        Text balanceText = new Text(account.getFormattedBalance());
        balanceText.setFill(account.getBalance() < 0 ? Color.RED : Color.WHITE);
        balanceText.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        HBox balanceHBox = new HBox(10);
        balanceHBox.setAlignment(Pos.BOTTOM_LEFT);

        balanceHBox.getChildren().addAll(subtitle, balanceText);

        // Create list of transactions
        Text transactionSubtitleText = UIHelpers.subtitleText("Transactions");

        accountDetails.getChildren().addAll(balanceHBox, transactionSubtitleText);

        for (Transaction transaction : account.getTransactions()) {
            TransactionRowView row = new TransactionRowView(transaction);
            accountDetails.getChildren().add(row);
        }

        return accountDetails;
    }

    /** Creates the account detail header with account name and back button. */
    private HBox createHeader() {
        HBox topPane = new HBox(10);
        topPane.setAlignment(Pos.CENTER_LEFT);
        topPane.setPadding(new Insets(10));

        // Create title
        Text accountTitle = UIHelpers.titleText(account.getName());

        // Create back button
        Button backButton = UIHelpers.createSimpleButton("←");
        backButton.setOnAction(e -> backButtonHandler.handle(e));

        // Create spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Create delete button
        Button deleteButton = UIHelpers.createSimpleButton("Delete");
        deleteButton.setTextFill(Color.RED);
        deleteButton.setOnAction(e -> deleteAccountHandler.handle(e));

        topPane.getChildren().addAll(backButton, accountTitle, spacer, deleteButton);

        return topPane;
    }
}