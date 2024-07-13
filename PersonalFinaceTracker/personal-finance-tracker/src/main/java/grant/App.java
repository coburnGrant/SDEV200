package grant;

import java.util.Date;

import grant.model.CheckingAccount;
import grant.model.SavingsAccount;
import grant.model.Transaction;
import grant.model.TransactionType;
import grant.model.User;
import grant.view.AccountsListView;
import grant.view.DashboardView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {
    private Stage primaryStage;
    private static Scene scene;
    private BorderPane rootLayout;

    public static final Color PRIMARY_COLOR = Color.rgb(0, 0, 0);
    public static final Color SECONDARY_COLOR = Color.rgb(22, 22, 24);
    public static final Color TERTIARY_COLOR = Color.rgb(33, 33, 36);
    public static final Color QUATERNARY_COLOR = Color.rgb(129, 129, 129);
    public static final String FONT_FAMILY = "Arial";

    User testUser;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Personal Finance Tracker");

        initTestUser();
        initRootLayout();
        showDashboard();

        scene = new Scene(rootLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void initTestUser() {
        testUser = new User("Grant", "Coburn");

        CheckingAccount checkingAccount = new CheckingAccount(testUser.getUserID(), "My Checking Account");
        SavingsAccount savingsAccount = new SavingsAccount(testUser.getUserID(), "My Savings Account", 4.5);

        checkingAccount.addTransaction(new Transaction(checkingAccount.getAccountID(), "McDonalds", 10.99, new Date(),
                TransactionType.WITHDRAWAL));
        savingsAccount.addTransaction(
                new Transaction(savingsAccount.getAccountID(), "Paycheck", 500, new Date(), TransactionType.DEPOSIT));

        testUser.addAccount(checkingAccount);
        testUser.addAccount(savingsAccount);
    }

    public void initRootLayout() {
        rootLayout = new BorderPane();

        rootLayout.setBackground(new Background(new BackgroundFill(PRIMARY_COLOR, null, null)));

        HBox navBar = new HBox();
        navBar.setBackground(new Background(new BackgroundFill(SECONDARY_COLOR, null, null)));
        navBar.setAlignment(Pos.CENTER);
        navBar.setSpacing(10);
        navBar.setPadding(new Insets(10));

        Button dashboardBtn = createNavButton("Dashboard");
        Button accountsBtn = createNavButton("Accounts");

        dashboardBtn.setOnAction(e -> showDashboard());
        accountsBtn.setOnAction(e -> showAccounts());

        navBar.getChildren().addAll(dashboardBtn, accountsBtn);

        rootLayout.setTop(navBar);
    }

    public static Button createStyledButton(String text, double width, FontWeight fontWeight, double fontSize) {
        Button button = new Button(text);

        if (width != 0) {
            button.setPrefWidth(width);
        }

        // Define colors
        Color textColor = Color.WHITE;

        // Create background fills
        Background normalBackground = new Background(
                new BackgroundFill(TERTIARY_COLOR, new CornerRadii(5), Insets.EMPTY));
        Background hoverBackground = new Background(
                new BackgroundFill(QUATERNARY_COLOR, new CornerRadii(5), Insets.EMPTY));
        Background pressedBackground = new Background(
                new BackgroundFill(QUATERNARY_COLOR.darker(), new CornerRadii(5), Insets.EMPTY));

        // Apply styles
        button.setBackground(normalBackground);
        button.setTextFill(textColor);

        button.setFont(Font.font(FONT_FAMILY, fontWeight, fontSize));
        button.setPadding(new Insets(10));
        button.setBorder(new Border(new BorderStroke(Color.rgb(80, 80, 80), BorderStrokeStyle.SOLID, new CornerRadii(5),
                new BorderWidths(1))));

        // Handle mouse events
        button.setOnMouseEntered(e -> button.setBackground(hoverBackground));
        button.setOnMouseExited(e -> button.setBackground(normalBackground));
        button.setOnMousePressed(e -> button.setBackground(pressedBackground));
        button.setOnMouseReleased(e -> button.setBackground(hoverBackground));

        return button;
    }

    public static Button createNavButton(String text) {
        return createStyledButton(text, 150, FontWeight.BOLD, 16);
    }

    public static Button createSimpleButton(String text) {
        return createStyledButton(text, 0, FontWeight.NORMAL, 14);
    }

    public void showDashboard() {
        DashboardView dashboard = new DashboardView(testUser);
        rootLayout.setCenter(dashboard);
    }

    public void showAccounts() {
        AccountsListView accountsView = new AccountsListView(testUser.getAccounts());

        rootLayout.setCenter(accountsView);
    }

    public static Text primaryText(String text, double fontSize) {
        Text txt = new Text(text);
        txt.setFont(Font.font(fontSize));
        txt.setFill(Color.WHITE);
        return txt;
    }

    public static Text primaryText(String text) {
        return primaryText(text, 12);
    }

    public static Text titleText(String text) {
        Text title = new Text(text);
        title.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 24));
        title.setFill(Color.WHITE);

        return title;
    }

    public static Text subtitleText(String text) {
        Text title = new Text(text);
        title.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 20));
        title.setFill(Color.GRAY);

        return title;
    }

    public static Text headingText(String text) {
        Text txt = new Text(text);
        txt.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 18));
        txt.setFill(Color.WHITE);
        return txt;
    }

    public static Text createText(String text, FontWeight weight, double size) {
        Text newText = new Text(text);
        newText.setFont(Font.font(FONT_FAMILY, weight, size));
        newText.setFill(Color.WHITE);
        return newText;
    }

    public static void main(String[] args) {
        launch();
    }
}