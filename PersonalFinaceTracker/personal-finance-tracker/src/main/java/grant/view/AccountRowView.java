package grant.view;

import grant.UIHelpers;
import grant.model.Account;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class AccountRowView extends HBox {
    private final Account account;

    private EventHandler<MouseEvent> rowPressEventHandler;

    public AccountRowView(Account account) {
        this.account = account;
        setupUI();
    }

    /** Creates row view */
    private void setupUI() {
        setPrefHeight(50);
        setPadding(new Insets(15));
        setBackground(new Background(new BackgroundFill(UIHelpers.SECONDARY_COLOR, new CornerRadii(10), null)));
        setAlignment(Pos.CENTER_LEFT);
        setCursor(Cursor.HAND);

        VBox vBox = new VBox(10);

        Text accountName = UIHelpers.headingText(account.getName());

        Text subtitle = UIHelpers.primaryText("Balance:", 16);
        subtitle.setFill(Color.GRAY);

        Text balanceText = new Text(account.getFormattedBalance());
        balanceText.setFill(account.getBalance() < 0 ? Color.RED : Color.WHITE);
        balanceText.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        HBox balanceHBox = new HBox(5);
        balanceHBox.getChildren().addAll(subtitle, balanceText);
        balanceHBox.setAlignment(Pos.BOTTOM_LEFT);

        vBox.getChildren().addAll(accountName, balanceHBox);

        getChildren().add(vBox);

        setOnMouseClicked(e -> rowPressEventHandler.handle(e));
    }

    /** Sets event handler for when the account row is clicked. */
    public void setOnRowClicked(EventHandler<MouseEvent> handler) {
        this.rowPressEventHandler = handler;
    }
}