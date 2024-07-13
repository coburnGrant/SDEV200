package grant.view;

import grant.UIHelpers;
import grant.model.Account;
import grant.model.AccountType;
import grant.model.SavingsAccount;
import grant.model.Transaction;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import javafx.stage.Stage;

public class AccountDetailView extends BorderPane {
    private final Account account;
    private final EventHandler<ActionEvent> backButtonHandler;
    private final EventHandler<ActionEvent> deleteAccountHandler;

    private VBox accountDetails;
    private HBox header;

    private CreateTransactionView createTransactionView;
    private Stage createTransactionStage;

    public AccountDetailView(Account account, EventHandler<ActionEvent> backButtonHandler, EventHandler<ActionEvent> deleteAccountHandler) {
        this.account = account;
        this.backButtonHandler = backButtonHandler;
        this.deleteAccountHandler = deleteAccountHandler;

        createAccountDetails();
        createHeader();

        setTop(header);
        setCenter(accountDetails);
    }

    /** Creates main account detail view */
    private void createAccountDetails() {
        accountDetails = new VBox(20);
        accountDetails.setPadding(new Insets(20));

        // Create balance text
        Text subtitle = UIHelpers.subtitleText("Balance:");

        Text balanceText = new Text(account.getFormattedBalance());
        balanceText.setFill(account.getBalance() < 0 ? Color.RED : Color.WHITE);
        balanceText.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        HBox balanceHBox = new HBox(5);
        balanceHBox.setAlignment(Pos.BOTTOM_LEFT);

        balanceHBox.getChildren().addAll(subtitle, balanceText);

        accountDetails.getChildren().add(balanceHBox);

        // Create list of transactions
        Text transactionSubtitleText = UIHelpers.subtitleText("Transactions");
        Button newTransactionButton = UIHelpers.createSimpleButton("Add new transaction +");
        newTransactionButton.setOnAction(e -> displayCreateNewTransaction());
        
        if(account.getAccountType() == AccountType.SAVINGS) {
            SavingsAccount savingsAccount = (SavingsAccount) account;

            HBox interestRateBox = new HBox(5);
            interestRateBox.setAlignment(Pos.BASELINE_LEFT);

            Text interestRateLabel = UIHelpers.subtitleText("Interest Rate:");

            Text interestRateText = UIHelpers.createText(savingsAccount.getInterestRateDescription(), FontWeight.BOLD, 20);

            interestRateBox.getChildren().addAll(interestRateLabel, interestRateText);

            accountDetails.getChildren().add(interestRateBox);
        }

        accountDetails.getChildren().addAll(transactionSubtitleText, newTransactionButton);

        for (Transaction transaction : account.getSortedTransactions()) {
            TransactionRowView row = new TransactionRowView(transaction, e -> deleteTransaction(transaction), e -> showEditTransaction(transaction));
            accountDetails.getChildren().add(row);
        }
    }

    /** Creates the account detail header with account name and back button. */
    private void createHeader() {
        header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(10));

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

        header.getChildren().addAll(backButton, accountTitle, spacer, deleteButton);
    }

    /** Refreshes and redraws account details */
    private void refreshDetails() {
        accountDetails.getChildren().clear();
        createAccountDetails();
        setCenter(accountDetails);
    }

    /** Displays a window to create a new transaction */
    private void displayCreateNewTransaction() {
        System.out.println("Creating new transaction!");
        createTransactionView = new CreateTransactionView(account, e -> addNewTransaction());

        Scene scene = new Scene(createTransactionView);

        createTransactionStage = new Stage();
        createTransactionStage.setTitle("Create New Transaction");
        createTransactionStage.setScene(scene);

        createTransactionStage.show();
    }

    /** Adds a new transaction to this account */
    private void addNewTransaction() {
        Transaction transaction = createTransactionView.getTransaction();

        System.out.println("Adding new transaction! \n" + transaction);

        boolean result = account.addTransaction(transaction);

        if (result) {
            closeCreateTransactionStage();
            refreshDetails();
        }
    }

    /** Deletes a transaction from this account */
    private void deleteTransaction(Transaction transaction) {
        boolean result = account.removeTransaction(transaction);

        if(result) {
            refreshDetails();
        }
    }

    /** Displays window to edit existing transaction */
    private void showEditTransaction(Transaction transaction) {
        createTransactionView = new CreateTransactionView(account, e -> saveEditTransaction(transaction));
        createTransactionView.editTransaction(transaction);
 
        Scene scene = new Scene(createTransactionView);
        
        createTransactionStage = new Stage();
        createTransactionStage.setTitle("Edit Transaction");
        createTransactionStage.setScene(scene);

        createTransactionStage.show();
    }

    private void closeCreateTransactionStage() {
        createTransactionStage.close();
    }

    private void saveEditTransaction(Transaction transaction) {
        System.out.println("Updating transaction...");
        Transaction newTransaction = createTransactionView.getTransaction();
        account.editTransaction(transaction, newTransaction);


        refreshDetails();
        closeCreateTransactionStage();
    }
}