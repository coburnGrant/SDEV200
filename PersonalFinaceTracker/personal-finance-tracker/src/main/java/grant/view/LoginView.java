package grant.view;

import grant.UIHelpers;
import grant.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class LoginView extends BorderPane {
    // Fields for the login view
    private LabeledTextField loginUsernameField;
    private PasswordField loginPasswordField;

    private Button loginButton;
    private Button noAccountButton;

    // Fields for the create new account view
    private LabeledTextField createUsernameField;
    private PasswordField createPasswordField;
    private LabeledTextField firstNameField;
    private LabeledTextField lastNameField;

    private Button createAccountButton;
    private Button haveAnAccountButton;

    private VBox loginVBox;
    private VBox createNewAccountVBox;

    private LoginCallback loginCallback;
    private CreateAccountCallback createAccountCallback;

    public LoginView(LoginCallback onLogin, CreateAccountCallback onCreateAccount) { 
        this.loginCallback = onLogin;
        this.createAccountCallback = onCreateAccount;

        initializeUI();
        displayLogin();
    }

    private void initializeUI() {
        setBackground(UIHelpers.PRIMARY_BACKGROUND);
        setPadding(new Insets(20));

        // Initialize login view
        loginVBox = new VBox(10);
        loginVBox.setAlignment(Pos.CENTER);

        loginUsernameField = new LabeledTextField("Username");
        loginUsernameField.setAlignment(Pos.CENTER);

        // Password text box
        HBox passwordHBox = new HBox(5);
        Text passwordFieldLabel = UIHelpers.primaryText("Password");
        loginPasswordField = new PasswordField();
        passwordHBox.getChildren().addAll(passwordFieldLabel, loginPasswordField);
        passwordHBox.setAlignment(Pos.CENTER);

        // Login button
        loginButton = UIHelpers.createSimpleButton("Login");
        loginButton.setMinWidth(250);
        loginButton.setOnAction(e -> loginRequested());

        HBox loginButtonHBox = new HBox();
        loginButtonHBox.getChildren().add(loginButton);
        loginButtonHBox.setAlignment(Pos.CENTER);

        // "No account? Create account" Button
        HBox noAccountHBox = new HBox(5);
        noAccountHBox.setAlignment(Pos.CENTER);

        Text noAccountText = UIHelpers.primaryText("Don't have an account?");
        noAccountButton = UIHelpers.createSimpleButton("Create new account");
        noAccountButton.setOnAction(e -> displayCreateNewAccount());

        noAccountHBox.getChildren().addAll(noAccountText, noAccountButton);

        // Set background color
        VBox backgroundVBox = new VBox(10);
        backgroundVBox.setPadding(new Insets(30));
        backgroundVBox.setBackground(UIHelpers.SECONDARY_BACKGROUND);
        backgroundVBox.getChildren().addAll(loginUsernameField, passwordHBox, loginButtonHBox, noAccountHBox);

        // Welcome text
        Text welcomeText = UIHelpers.titleText("Login");
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        loginVBox.getChildren().addAll(welcomeText, backgroundVBox);

        // Initialize create new account view
        createNewAccountVBox = new VBox(10);
        createNewAccountVBox.setAlignment(Pos.CENTER);

        // First name field
        firstNameField = new LabeledTextField("First name");
        firstNameField.setAlignment(Pos.CENTER);

        // Last name field
        lastNameField = new LabeledTextField("Last name");
        lastNameField.setAlignment(Pos.CENTER);

        // Username field
        createUsernameField = new LabeledTextField("Username");
        createUsernameField.setAlignment(Pos.CENTER);
        
        // Password field
        createPasswordField = new PasswordField();
        Text passwordText = UIHelpers.primaryText("Password");

        HBox createPasswordHBox = new HBox(10);
        createPasswordHBox.setAlignment(Pos.CENTER);
        createPasswordHBox.getChildren().addAll(passwordText, createPasswordField);

        // "Have an account? Login" button
        HBox haveAnAccountHBox = new HBox(10);
        haveAnAccountHBox.setAlignment(Pos.CENTER);

        Text haveAnAccountText = UIHelpers.primaryText("Have an account?");

        haveAnAccountButton = UIHelpers.createSimpleButton("Login");
        haveAnAccountButton.setOnAction(e -> displayLogin());

        // Create account button
        createAccountButton = UIHelpers.createSimpleButton("Create new Account");
        createAccountButton.setOnAction(e -> createAccountRequested());
        createAccountButton.setMinWidth(250);
        createAccountButton.setAlignment(Pos.CENTER);

        HBox createAccountButtonHBox = new HBox();
        createAccountButtonHBox.getChildren().add(createAccountButton);
        createAccountButtonHBox.setAlignment(Pos.CENTER);

        haveAnAccountHBox.getChildren().addAll(haveAnAccountText, haveAnAccountButton);

        // Title
        Text createAccountText = UIHelpers.titleText("Create Account");
        createAccountText.setTextAlignment(TextAlignment.CENTER);

        // Background
        VBox createBackgroundVBox = new VBox(10);
        createBackgroundVBox.setBackground(UIHelpers.SECONDARY_BACKGROUND);
        createBackgroundVBox.setPadding(new Insets(30));
        createBackgroundVBox.getChildren().addAll(firstNameField, lastNameField, createUsernameField, createPasswordHBox, createAccountButtonHBox, haveAnAccountHBox);

        createNewAccountVBox.getChildren().addAll(createAccountText, createBackgroundVBox);
    }

    private void displayLogin() {
        setCenter(loginVBox);
    }

    private void displayCreateNewAccount() {
        setCenter(createNewAccountVBox);
    }

    private void loginRequested() {
        System.out.println("Login requested!");

        String uname = loginUsernameField.getText();
        String pw = loginPasswordField.getText();   
        
        loginCallback.onLogin(uname, pw);
    }

    private void createAccountRequested() {
        System.out.println("Create new account requested!");

        User newUser = getNewUser();

        createAccountCallback.onCreateNewAccount(newUser);
    }

    private User getNewUser() {
        String fname = firstNameField.getText();
        String lname = lastNameField.getText();
        String uname = createUsernameField.getText();
        String pw = createPasswordField.getText();

        // Cacher will be null until the user is cached
        return new User(uname, pw, fname, lname, null);
    }
    public interface LoginCallback {
        void onLogin(String username, String password);
    }

    public interface CreateAccountCallback {
        void onCreateNewAccount(User user);
    }
}