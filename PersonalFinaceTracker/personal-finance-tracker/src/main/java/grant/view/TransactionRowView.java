package grant.view;

import java.text.SimpleDateFormat;

import grant.UIHelpers;
import grant.model.Transaction;
import grant.model.TransactionType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class TransactionRowView extends HBox {
    private final Transaction transaction;
    private final SimpleDateFormat dateFormat;
    private final EventHandler<ActionEvent> handleTransactionDeletion;
    private final EventHandler<ActionEvent> handleTransactionEdit;

    private Text descriptionText;
    private Text amountText;
    private Text dateText;

    public TransactionRowView(Transaction transaction, EventHandler<ActionEvent> handleTransactionDeletion, EventHandler<ActionEvent> handleTransactionEdit) {
        this.transaction = transaction;
        this.handleTransactionDeletion = handleTransactionDeletion;
        this.handleTransactionEdit = handleTransactionEdit;

        this.dateFormat = new SimpleDateFormat("E MMM dd y");

        setupUI();

        if (handleTransactionDeletion != null && handleTransactionEdit != null) {
            setupContextMenu();
        }
    }

    /** Initializes UI for row */
    private void setupUI() {
        setBackground(new Background(new BackgroundFill(UIHelpers.SECONDARY_COLOR, new CornerRadii(10), Insets.EMPTY)));
        setPadding(new Insets(15));
        setSpacing(10);
        setAlignment(Pos.CENTER_LEFT);

        // Create description text
        descriptionText = UIHelpers.createText(transaction.getDescription(), FontWeight.BOLD, 16);

        // Create date text
        dateText = UIHelpers.createText(dateFormat.format(transaction.getDate()), FontWeight.NORMAL, 14);
        dateText.setFill(Color.GRAY);

        // Create amount text
        amountText = UIHelpers.createText(transaction.getPlusMinusAmountDescription(), FontWeight.NORMAL, 14);
        amountText.setFill(transaction.getType() == TransactionType.WITHDRAWAL ? Color.RED : Color.GREEN);

        // Add spacer to make amount text align at the end
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        getChildren().addAll(descriptionText, dateText, spacer, amountText);
    }

    /** Sets up the context menu for deleting the transaction */
    private void setupContextMenu() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(e -> {
            System.out.println("Deleting transaction: " + transaction);
            handleTransactionDeletion.handle(e);
        });

        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(e -> {
            System.out.println("Editing transaction: " + transaction);
            handleTransactionEdit.handle(e);
        });

        contextMenu.getItems().addAll(editItem, deleteItem);

        // Show context menu on right-click
        this.setOnContextMenuRequested(e -> {
            contextMenu.show(this, e.getScreenX(), e.getScreenY());
            e.consume(); // Consume event to prevent it from being handled further
        });
    }
}