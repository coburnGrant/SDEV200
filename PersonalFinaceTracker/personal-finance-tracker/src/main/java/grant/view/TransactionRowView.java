package grant.view;

import java.text.SimpleDateFormat;

import grant.App;
import grant.model.Transaction;
import grant.model.TransactionType;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
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
    private EventHandler<MouseEvent> rowPressEventHandler;

    private Text descriptionText;
    private Text amountText;
    private Text dateText;

    public TransactionRowView(Transaction transaction) {
        this.transaction = transaction;
        this.dateFormat = new SimpleDateFormat("E MMM dd");

        setupUI();
    }

    private void setupUI() {
        setBackground(new Background(new BackgroundFill(App.SECONDARY_COLOR, new CornerRadii(10), Insets.EMPTY)));
        setPadding(new Insets(15));
        setSpacing(10);
        setAlignment(Pos.CENTER_LEFT);

        descriptionText = App.createText(transaction.getDescription(), FontWeight.BOLD, 16);

        amountText = App.createText(transaction.getPlusMinusAmountDescription(), FontWeight.NORMAL, 14);
        amountText.setFill(transaction.getType() == TransactionType.WITHDRAWAL ? Color.RED : Color.GREEN);

        dateText = App.createText(dateFormat.format(transaction.getDate()), FontWeight.NORMAL, 14);
        dateText.setFill(Color.GRAY);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        getChildren().addAll(descriptionText, dateText, spacer, amountText);

        setOnMouseClicked(e -> rowPressEventHandler.handle(e));
    }

    public void setRowClicked(EventHandler<MouseEvent> handler) {
        this.rowPressEventHandler = handler;
    }
}