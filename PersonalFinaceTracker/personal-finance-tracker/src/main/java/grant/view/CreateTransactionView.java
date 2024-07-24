package grant.view;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import grant.UIHelpers;
import grant.model.Account;
import grant.model.Transaction;
import grant.model.TransactionType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
public class CreateTransactionView extends StackPane {
    private final Account account;
    private final EventHandler<ActionEvent> newTransactionHandler;

    private ComboBox<TransactionType> transactionTypePicker;
    private LabeledTextField descriptionField;
    private LabeledTextField amountTextField;
    private DatePicker datePicker;

    public CreateTransactionView(Account account, EventHandler<ActionEvent> newTransactionHandler){
        this.account = account;
        this.newTransactionHandler = newTransactionHandler;

        initializeUI();
    }

    private void initializeUI() {
        setBackground(UIHelpers.PRIMARY_BACKGROUND);

        VBox vBox = new VBox(10);

        HBox transactionTypeHBox = new HBox(5);
        transactionTypeHBox.setAlignment(Pos.BASELINE_LEFT);

        Text transactionTypeLabel = UIHelpers.primaryText("Transaction Type");

        transactionTypePicker = new ComboBox<>();
        transactionTypePicker.getItems().addAll(TransactionType.values());

        transactionTypeHBox.getChildren().addAll(transactionTypeLabel, transactionTypePicker);

        descriptionField = new LabeledTextField("Description");

        amountTextField = new LabeledTextField("Amount");
        amountTextField.setNumbersOnly();

        HBox datePickerHBox = new HBox(5);
        datePickerHBox.setAlignment(Pos.BASELINE_LEFT);

        Text datePickerLabel = UIHelpers.primaryText("Date");

        datePicker = new DatePicker();

        datePickerHBox.getChildren().addAll(datePickerLabel, datePicker);

        Button saveButton = UIHelpers.createSimpleButton("Save");
        saveButton.setOnAction(e -> newTransactionHandler.handle(e));

        vBox.getChildren().addAll(transactionTypeHBox, descriptionField, amountTextField, datePickerHBox, saveButton);

        setPadding(new Insets(20));
        getChildren().add(vBox);
    }

    public void editTransaction(Transaction transaction) {
        transactionTypePicker.setValue(transaction.getType());
        descriptionField.setText(transaction.getDescription());
        amountTextField.setText(Double.toString(transaction.getAmount()));
        LocalDate localDate = transaction.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        datePicker.setValue(localDate);
    }

    public Transaction getTransaction() {
        TransactionType type = transactionTypePicker.getValue();
        String description = descriptionField.getText();
        double amount = amountTextField.getDoubleValue();

        LocalDate localDate = datePicker.getValue();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return new Transaction(account.getAccountID(), description, amount, date, type);
    }
}
