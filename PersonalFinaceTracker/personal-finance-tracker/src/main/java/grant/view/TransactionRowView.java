package grant.view;

import java.text.SimpleDateFormat;

import grant.UIHelpers;
import grant.model.Transaction;
import grant.model.TransactionType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    private Text descriptionText;
    private Text amountText;
    private Text dateText;

    public TransactionRowView(Transaction transaction) {
        this.transaction = transaction;
        this.dateFormat = new SimpleDateFormat("E MMM dd");

        setupUI();
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
}