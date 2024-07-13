package grant.view;

import grant.App;
import grant.model.Account;
import grant.model.Transaction;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class AccountDetailView extends BorderPane {
    private final Account account;
    private EventHandler<ActionEvent> backButtonHandler;

    public AccountDetailView(Account account) {
        this.account = account;

        VBox accountDetails = createAccountDetails();

        setTop(createTopPane());
        setCenter(accountDetails);
    }

    private VBox createAccountDetails() {
        VBox accountDetails = new VBox(20);
        accountDetails.setPadding(new Insets(20));

        Text subtitle = App.subtitleText("Balance:");

        Text balanceText = new Text(account.getFormattedBalance());
        balanceText.setFill(account.getBalance() < 0 ? Color.RED : Color.WHITE);
        balanceText.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        HBox balanceHBox = new HBox(10);
        balanceHBox.getChildren().addAll(subtitle, balanceText);
        balanceHBox.setAlignment(Pos.BOTTOM_LEFT);

        Text transactionSubtitleText = App.subtitleText("Transactions");

        accountDetails.getChildren().addAll(balanceHBox, transactionSubtitleText);

        for (Transaction transaction : account.getTransactions()) {
            TransactionRowView row = new TransactionRowView(transaction);
            accountDetails.getChildren().add(row);
        }

        return accountDetails;
    }

    private HBox createTopPane() {
        HBox topPane = new HBox(10);
        topPane.setAlignment(Pos.CENTER_LEFT);
        topPane.setPadding(new Insets(10));

        Text accountTitle = App.titleText(account.getName());

        Button backButton = App.createSimpleButton("←");
        backButton.setOnAction(e -> backButtonHandler.handle(e));

        topPane.getChildren().addAll(backButton, accountTitle);

        return topPane;
    }

    public void setOnBackButtonPressed(EventHandler<ActionEvent> handler) {
        this.backButtonHandler = handler;
    }
}