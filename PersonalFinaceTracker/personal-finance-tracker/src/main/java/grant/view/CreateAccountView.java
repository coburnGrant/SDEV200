package grant.view;

import grant.UIHelpers;
import grant.model.Account;
import grant.model.AccountType;
import grant.model.CheckingAccount;
import grant.model.SavingsAccount;
import grant.model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CreateAccountView extends StackPane {
    private final User user;
    private final EventHandler<ActionEvent> newAccountHandler;
    private ComboBox<AccountType> accountTypePicker;
    private LabeledTextField nameField;
    private LabeledTextField initialBalanceField;
    private LabeledTextField interestRateField;

    VBox vBox;

    public CreateAccountView(User user, EventHandler<ActionEvent> newAccountHandler) {
        this.user = user;
        this.newAccountHandler = newAccountHandler;
        initializeUI();
    }

    private void initializeUI() {
        setBackground(UIHelpers.PRIMARY_BACKGROUND);

        vBox = new VBox(10);
        vBox.setPadding(new Insets(10));

        HBox accountTypeHBox = new HBox(5);
        accountTypeHBox.setAlignment(Pos.BASELINE_LEFT);

        Text accountTypeLabel = UIHelpers.primaryText("Account Type");

        accountTypePicker = new ComboBox<>();
        accountTypePicker.getItems().addAll(AccountType.values());
        accountTypePicker.setOnAction(e -> updateFields());

        accountTypeHBox.getChildren().addAll(accountTypeLabel, accountTypePicker);

        nameField = new LabeledTextField("Account Name");

        initialBalanceField = new LabeledTextField("Initial Balance");
        initialBalanceField.setNumbersOnly();

        interestRateField = new LabeledTextField("Interest Rate");
        interestRateField.setNumbersOnly();

        Button saveButton = UIHelpers.createSimpleButton("Save");
        saveButton.setOnAction(e -> newAccountHandler.handle(e));

        vBox.getChildren().addAll(accountTypeHBox, nameField, initialBalanceField, saveButton);

        getChildren().add(vBox);
    }

    private void updateFields() {
        AccountType selectedAccountType = accountTypePicker.getValue();

        if (selectedAccountType == AccountType.SAVINGS) {
            if (!vBox.getChildren().contains(interestRateField)) {
                vBox.getChildren().add(2, interestRateField);
            }
        } else {
            vBox.getChildren().remove(interestRateField);
        }
    }

    public Account getAccount() {
        AccountType selectedAccountType = accountTypePicker.getValue();

        String userID = user.getUserID();

        String accountName = nameField.getText();
        double balance = Double.parseDouble(initialBalanceField.getText());

        switch(selectedAccountType) {
            case CHECKING:

            return new CheckingAccount(userID, accountName, balance, user.getCacher());

            case SAVINGS:
            double interestRate = Double.parseDouble(interestRateField.getText());
            
            return new SavingsAccount(userID, accountName, interestRate, balance, user.getCacher());
        }

        return null;
    }
}